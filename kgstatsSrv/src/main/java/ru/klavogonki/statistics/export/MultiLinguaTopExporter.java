package ru.klavogonki.statistics.export;


import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.klavogonki.kgparser.NonStandardDictionary;
import ru.klavogonki.kgparser.StandardDictionary;
import ru.klavogonki.kgparser.Vocabulary;
import ru.klavogonki.statistics.dto.MultiVocabularyTopDto;
import ru.klavogonki.statistics.dto.PlayerMultiVocabularyDto;
import ru.klavogonki.statistics.entity.PlayerEntity;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;
import ru.klavogonki.statistics.freemarker.MultiVocabularyTopBySpeedSumTemplate;
import ru.klavogonki.statistics.freemarker.OrderUtils;
import ru.klavogonki.statistics.mapper.MultiVocabularyTopDtoMapper;
import ru.klavogonki.statistics.mapper.PlayerMultiVocabularyDtoMapper;
import ru.klavogonki.statistics.repository.PlayerVocabularyStatsRepository;
import ru.klavogonki.statistics.springboot.Profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
@Profile(Profiles.DATABASE)
public class MultiLinguaTopExporter implements DataExporter {

    private static final int MIN_NON_NORMAL_VOCABULARIES = 10;

    @Autowired
    protected PlayerVocabularyStatsRepository repository;

    // todo: autowire it, @see https://mapstruct.org/documentation/stable/reference/html/#using-dependency-injection
    private final MultiVocabularyTopDtoMapper mapper = Mappers.getMapper(MultiVocabularyTopDtoMapper.class);

    @Override
    public void export(final ExportContext context) {

        // export multi-lingua
        List<NonStandardDictionary> nonStandardVocabularies = NonStandardDictionary.getMultiLinguaNonStandardDictionaries();

        List<String> nonStandardVocabularyCodes = nonStandardVocabularies
            .stream()
            .map(Vocabulary::getCode)
            .collect(Collectors.toList());

//        List<String> multiLinguaVocabularyCodes = ListUtils.union(List.of(StandardDictionary.normal.name()), nonStandardVocabularyCodes);
        List<String> multiLinguaVocabularyCodes = nonStandardVocabularyCodes;

        List<PlayerVocabularyStatsEntity> players = repository.findByVocabularyCodeInAndPlayerBlockedEquals(multiLinguaVocabularyCodes, PlayerEntity.NOT_BLOCKED);

        logger.debug("Total PlayerVocabularyStats by multilingual vocabularies: {}", players.size());

        Map<Integer, List<PlayerVocabularyStatsEntity>> playerIdToVocabularyStats = players
            .stream()
            .collect(Collectors.groupingBy(
                playerVocabularyStats -> playerVocabularyStats.getPlayer().getPlayerId()
            ));

        logger.debug("Different players in multilingual top: {}", playerIdToVocabularyStats.size());

        Map<Integer, List<PlayerVocabularyStatsEntity>> playerIdToVocabularyStatsWithResultsInMinVocabularies = playerIdToVocabularyStats
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().size() >= MIN_NON_NORMAL_VOCABULARIES) // filter by different vocabularies count
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Integer> playerIds = playerIdToVocabularyStatsWithResultsInMinVocabularies
            .keySet()
            .stream()
            .sorted()
            .collect(Collectors.toList());

        logger.debug("Player ids with stats for minimum {} different vocabularies, total {} players: {}", MIN_NON_NORMAL_VOCABULARIES, playerIds.size(), playerIds);

        // to not make the initial selection super-slow, select values for "normal" by a separate query with playerIds!
        List<PlayerVocabularyStatsEntity> playersStatsInNormal = repository.findByVocabularyCodeInAndPlayer_PlayerIdInAndPlayerBlockedEquals(
            List.of(StandardDictionary.normal.getCode()),
            playerIds,
            PlayerEntity.NOT_BLOCKED
        );

        logger.debug("Got player results for {} players in {}. Results count: {}", playerIds.size(), StandardDictionary.normal, playersStatsInNormal.size());

        Map<Integer, PlayerVocabularyStatsEntity> playerIdToPlayerStatsInNormal = playersStatsInNormal
            .stream()
            .collect(Collectors.toMap(stats -> stats.getPlayer().getPlayerId(), stats -> stats));

        List<Vocabulary> vocabularies = new ArrayList<>();
        vocabularies.add(StandardDictionary.normal);
        vocabularies.addAll(nonStandardVocabularies);

        List<PlayerMultiVocabularyDtoMapper.InputWrapper> playersMapperInput = playerIdToVocabularyStatsWithResultsInMinVocabularies
            .values()
            .stream()
            .map(playerVocabularyStats -> {
                PlayerEntity player = playerVocabularyStats.get(0).getPlayer();

                Map<String, PlayerVocabularyStatsEntity> playerVocabulariesMap = playerVocabularyStats
                    .stream()
                    .collect(Collectors.toMap(PlayerVocabularyStatsEntity::getVocabularyCode, f -> f));

                Integer playerId = player.getPlayerId();
                PlayerVocabularyStatsEntity playerResultInNormal = playerIdToPlayerStatsInNormal.get(playerId);
                if (playerResultInNormal != null) {
                    playerVocabulariesMap.put(
                        StandardDictionary.normal.getCode(),
                        playerResultInNormal
                    );
                }
                else {
                    logger.warn("Player with id = {}, login = {} has no result in {}.", playerId, player.getLogin(), StandardDictionary.normal);
                }

                return new PlayerMultiVocabularyDtoMapper.InputWrapper(
                    player,
                    playerVocabulariesMap
                );
            })
            .collect(Collectors.toList());

        MultiVocabularyTopDtoMapper.InputWrapper mapperInput = new MultiVocabularyTopDtoMapper.InputWrapper(
            vocabularies, playersMapperInput
        );

        MultiVocabularyTopDto multiVocabularyTopDto = mapper.toDto(mapperInput);

        // todo: other sortings (by haul, by totalRaces, by totalVocabularies
        // sort by sum of best speeds
        multiVocabularyTopDto
            .getPlayers()
            .sort((p1, p2) -> Integer.compare(p2.getBestSpeedsSum(), p1.getBestSpeedsSum()));

        OrderUtils.fillOrderNumbers(multiVocabularyTopDto.getPlayers(), PlayerMultiVocabularyDto::getBestSpeedsSum);

        logger.debug("Converted player dto: \n {}", multiVocabularyTopDto);

        // todo: paging
        new MultiVocabularyTopBySpeedSumTemplate()
            .vocabularies(multiVocabularyTopDto.getVocabularies())
            .players(multiVocabularyTopDto.getPlayers())
            .pageTitle("Топ по сумме рекордов в «Мультилингве»")
            .header("Топ по сумме рекордов в «Мультилингве»")
            .additionalHeader(String.format("Учтены игроки с результатами минимум в %d словарях (помимо «Обычного»)", MIN_NON_NORMAL_VOCABULARIES))
            .export(context.webRootDir + "/_multilingua-top-by-speed-sum.html"); // todo: good name
    }
}