package ru.klavogonki.statistics.springboot;

import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.klavogonki.statistics.export.ExportContext;
import ru.klavogonki.statistics.export.IndexPageExporter;
import ru.klavogonki.statistics.export.PlayersByRankExporter;
import ru.klavogonki.statistics.export.Top500PagesExporter;
import ru.klavogonki.statistics.export.TopBySpeedExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.DigitsOneHundredTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.FrequencyVocabularyTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.MiniMarathonTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.NormalInEnglishTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.OneHundredRussianTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.PinkiesPlusTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.RingFingersTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.ShortTextsTopExporter;
import ru.klavogonki.statistics.export.vocabulary.non_standard.TrainingIndexFingersTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.AbraTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.CharsTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.DigitsTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.MarathonTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.NoErrorTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.NormalTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.ReferatsTopExporter;
import ru.klavogonki.statistics.export.vocabulary.standard.SprintTopExporter;
import ru.klavogonki.statistics.entity.PlayerEntity;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;
import ru.klavogonki.statistics.mapper.PlayerMapper;
import ru.klavogonki.statistics.mapper.PlayerVocabularyStatsMapper;
import ru.klavogonki.statistics.repository.PlayerRepository;
import ru.klavogonki.statistics.repository.PlayerVocabularyStatsRepository;
import ru.klavogonki.kgparser.statistics.Config;
import ru.klavogonki.kgparser.statistics.download.PlayerJsonData;
import ru.klavogonki.kgparser.statistics.download.PlayerJsonParser;
import ru.klavogonki.kgparser.util.DateUtils;
import ru.klavogonki.kgparser.util.JacksonUtils;
import ru.klavogonki.openapi.model.GetStatsOverviewGameType;
import ru.klavogonki.openapi.model.GetStatsOverviewResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@EntityScan(basePackages= {"ru.klavogonki.statistics.entity"})
@ComponentScan({"ru.klavogonki.kgparser"})
@EnableJpaRepositories("ru.klavogonki.kgparser")
@Log4j2
public class KgParserApplication implements CommandLineRunner {
	public static final int REQUIRED_ARGUMENTS_COUNT = 2;

	public enum Mode {
		IMPORT_JSON_TO_DATABASE,
		GENERATE_STATISTICS_FROM_DATABASE
	}

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerVocabularyStatsRepository playerVocabularyStatsRepository;

	// aggregate tops
	@Autowired
	private IndexPageExporter indexPageExporter;

	@Autowired
	private TopBySpeedExporter topBySpeedExporter;

	@Autowired
	private Top500PagesExporter top500PagesExporter;

	@Autowired
	private PlayersByRankExporter playersByRankExporter;

	// standard vocabularies
	@Autowired
	private NormalTopExporter normalTopExporter;

	@Autowired
	private AbraTopExporter abraTopExporter;

	@Autowired
	private ReferatsTopExporter referatsTopExporter;

	@Autowired
	private NoErrorTopExporter noErrorTopExporter;

	@Autowired
	private MarathonTopExporter marathonTopExporter;

	@Autowired
	private CharsTopExporter charsTopExporter;

	@Autowired
	private DigitsTopExporter digitsTopExporter;

	@Autowired
	private SprintTopExporter sprintTopExporter;

	// non-standard vocabularies
	@Autowired
	private NormalInEnglishTopExporter normalInEnglishTopExporter;

	@Autowired
	private MiniMarathonTopExporter miniMarathonTopExporter;

	@Autowired
	private ShortTextsTopExporter shortTextsTopExporter;

	@Autowired
	private FrequencyVocabularyTopExporter frequencyVocabularyTopExporter;

	@Autowired
	private OneHundredRussianTopExporter oneHundredRussianTopExporter;

	@Autowired
	private DigitsOneHundredTopExporter digitsOneHundredTopExporter;

	@Autowired
	private TrainingIndexFingersTopExporter trainingIndexFingersTopExporter;

	@Autowired
	private RingFingersTopExporter ringFingersTopExporter;

	@Autowired
	private PinkiesPlusTopExporter pinkiesPlusTopExporter;

	// todo: autowire it, @see https://mapstruct.org/documentation/stable/reference/html/#using-dependency-injection
	private final PlayerMapper mapper = Mappers.getMapper(PlayerMapper.class);

	// todo: autowire it, @see https://mapstruct.org/documentation/stable/reference/html/#using-dependency-injection
	private final PlayerVocabularyStatsMapper statsMapper = Mappers.getMapper(PlayerVocabularyStatsMapper.class);

	public static void main(String[] args) {
		SpringApplication.run(KgParserApplication.class, args);
	}

	private List<PlayerEntity> playersBatch = new ArrayList<>(); // todo: find a nicer solution

	@Override
	public void run(final String... args) {
		if (args.length != REQUIRED_ARGUMENTS_COUNT) {
			// todo: use logger instead of System.out??
			System.out.printf("Usage: %s <mode> <inputConfigFilePath> %n", KgParserApplication.class.getSimpleName());
			System.out.printf("Possible <mode> values: %s, %s %n", Mode.IMPORT_JSON_TO_DATABASE.name(), Mode.GENERATE_STATISTICS_FROM_DATABASE.name());
			return;
		}

		String modeString = args[0];
		String inputConfigFilePath = args[1];

		Mode mode = Mode.valueOf(modeString);

		Config config = JacksonUtils.parseConfig(inputConfigFilePath);

		switch (mode) {
			case IMPORT_JSON_TO_DATABASE:
				importJsonToDatabase(config);
				break;

			case GENERATE_STATISTICS_FROM_DATABASE:
				generateStatistics(config);
				break;

			default:
				throw new IllegalArgumentException(String.format("Unknown mode value: %s", mode));
		}
	}

	private void generateStatistics(final Config config) {
		ExportContext context = new ExportContext(config);
/*
		context.webRootDir = "C:/java/kgparser/kgstats/src/main/webapp/";

		// data load from 2020-12-28
		context.minPlayerId = 1;
		context.maxPlayerId = 628000;
		context.dataDownloadStartDate = DateUtils.parseLocalDateTimeWithUiDateFormat("2020-12-28 00:28:13");
		context.dataDownloadEndDate = DateUtils.parseLocalDateTimeWithUiDateFormat("2020-12-28 01:44:43");
*/

/*
		// data load from 2020-12-09
		context.minPlayerId = 1;
		context.maxPlayerId = 625000;
		context.dataDownloadStartDate = DateUtils.parseLocalDateTimeWithUiDateFormat("2020-12-08 02:39:07");
		context.dataDownloadEndDate = DateUtils.parseLocalDateTimeWithUiDateFormat("2020-12-09 16:28:01");
*/

		// todo: select mode (what to do) by arguments
		// todo: add an option to skip Excel import

		boolean exitAfterPageGeneration = false;
		boolean executeDatabaseJsonImport = false;

		// non-standard vocabularies exporters
		pinkiesPlusTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		ringFingersTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		trainingIndexFingersTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		digitsOneHundredTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		oneHundredRussianTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		shortTextsTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		frequencyVocabularyTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		miniMarathonTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		normalInEnglishTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		// standard vocabularies exporters
		sprintTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		digitsTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		marathonTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		referatsTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		abraTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		normalTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		noErrorTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		charsTopExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		// global tops exporters
		playersByRankExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		top500PagesExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		indexPageExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		topBySpeedExporter.export(context);
		if (exitAfterPageGeneration) {
			return;
		}

		if (!executeDatabaseJsonImport) {
			return;
		}
	}

	private void importJsonToDatabase(final Config config) {
		// todo: move all related logic to a separate Spring component

		// export from json files with API call results to the database
		PlayerJsonParser.handlePlayers(config, this::handlePlayer);

		if (!playersBatch.isEmpty()) { // save remainder from batch size
			handlePlayersBatch(true);
		}
	}

	private void handlePlayer(int playerId, Optional<PlayerJsonData> jsonDataOptional) {
		if (jsonDataOptional.isEmpty()) {
			String errorMessage = String.format("Something really serious happened for player %d. JsonData is not present.", playerId);
			logger.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}

/*
		if (true) { // just test the parsing + validation, no DB save. Todo: make it configurable, like "dry-run"
			return;
		}
*/

		PlayerJsonData jsonData = jsonDataOptional.get();

		PlayerEntity player = mapper.playerJsonDataToPlayerEntity(jsonData);

		// todo: this can be done in @AfterMapping in the mapper, passing playerId as @Context
		if (player.getPlayerId() == null) { // non-existing user -> no playerId in neither summary nor indexData
			logger.debug(
				"Player {}: setting playerId manually. Most probably this player does not exist.\n/get-summary error: {}\n/get-index/data error: {}",
				playerId,
				player.getGetSummaryError(),
				player.getGetIndexDataError()
			);

			player.setPlayerId(playerId);
		}

//		savePlayerToDatabase(player);

		GetStatsOverviewResponse statsOverview = jsonData.statsOverview;
		Map<String, GetStatsOverviewGameType> gameTypes = statsOverview.getGametypes();

		List<PlayerVocabularyStatsEntity> allPlayerStats = new ArrayList<>();
		for (Map.Entry<String, GetStatsOverviewGameType> entry : gameTypes.entrySet()) {
			String vocabularyCode = entry.getKey();
			GetStatsOverviewGameType gameType = entry.getValue();

			LocalDateTime importDateUtc = DateUtils.convertToUtcLocalDateTime(jsonData.importDate);

			PlayerVocabularyStatsEntity stats = statsMapper.statsGameTypeToEntity(gameType, importDateUtc, statsOverview, vocabularyCode, player);
			allPlayerStats.add(stats);
		}


		player.setStats(allPlayerStats);

		playersBatch.add(player);
		handlePlayersBatch(false);
//		savePlayerToDatabase(player); // cascade save player with its stats

//		saveStatsToDatabase(player, allPlayerStats);
	}

	private void handlePlayersBatch(boolean force) {
		int size = playersBatch.size();
		if ((size == 1000) || force) { // force for last execution
			logger.info("!!! Batch size is {}. Saving {} players to the database...", size, size);

			playerRepository.saveAll(playersBatch);

			// todo: time measure for saving 1000 players?
			logger.info("!!! {} players saved to the database.", size);

			playersBatch.clear();
			logger.info("Batch cleared from {} to {} players", size, playersBatch.size());

		}
		else {
			logger.info("Batch size is {}. Do not save it yet.", size);
		}
	}

	private void savePlayerToDatabase(PlayerEntity player) {
		final Integer playerId = player.getPlayerId();
		logger.debug("Saving player {} to database (NO EXISTING PLAYERS CHECK)...", playerId);

/*
		// todo: we must actually search by importedDate + playerId, if data is not sharded to different tables by importDate
		List<PlayerEntity> existingPlayersWithId = playerRepository.findByPlayerId(playerId);

		List<Long> playerDbIds = existingPlayersWithId
			.stream()
			.map(PlayerEntity::getDbId)
			.sorted()
			.collect(Collectors.toList());

		if (existingPlayersWithId.isEmpty()) {
			logger.debug("No players with playerId = {} found in the database. Nothing to delete.", playerId);
		}
		else {
			logger.debug("Found {} players with playerId = {}. Deleting them...", existingPlayersWithId.size(), playerId);
			playerRepository.deleteAll(existingPlayersWithId);
			logger.debug("Deleted {} players with db ids: {}.", existingPlayersWithId.size(), playerDbIds);
		}
*/

		playerRepository.save(player);
		logger.debug("Successfully saved player {} (login = \"{}\") to database. Player dbId: {}.", player.getPlayerId(), player.getLogin(), player.getDbId());
	}

	private void saveStatsToDatabase(PlayerEntity player, List<PlayerVocabularyStatsEntity> statsList) {
		logger.debug("Saving player {} stats (total {} vocabularies) to database (NO EXISTING STATS CHECK)...", player.getPlayerId(), statsList.size());

		/* todo: any db checks if required */

		playerVocabularyStatsRepository.saveAll(statsList);

		logger.debug("Successfully player {} stats (total {} vocabularies) to database.", player.getPlayerId(), statsList.size());
	}
}
