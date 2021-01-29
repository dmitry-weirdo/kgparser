package ru.klavogonki.statistics.export;


import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.klavogonki.kgparser.NonStandardDictionary;
import ru.klavogonki.kgparser.Vocabulary;
import ru.klavogonki.statistics.dto.MultiVocabularyTopDto;
import ru.klavogonki.statistics.entity.PlayerEntity;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;
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

        List<Integer> playerIds = playerIdToVocabularyStats
            .values()
            .stream()
            .filter(playerVocabularyStatsEntities -> playerVocabularyStatsEntities.size() >= MIN_NON_NORMAL_VOCABULARIES) // filter by different vocabularies count
            .map(playerVocabularyStatsEntities -> playerVocabularyStatsEntities.get(0).getPlayer().getPlayerId())
            .sorted()
            .collect(Collectors.toList());

        logger.debug("Player ids with stats for minimum {} different vocabularies, total {} players: {}", MIN_NON_NORMAL_VOCABULARIES, playerIds.size(), playerIds);
        // todo: to not make the selection super-slow, select values for "normal" by a separate query with playerIds!

        // todo: also add StandardDictionary.normal
        List<Vocabulary> vocabularies = new ArrayList<>(nonStandardVocabularies);

        List<PlayerMultiVocabularyDtoMapper.InputWrapper> playersMapperInput = playerIdToVocabularyStats
            .values()
            .stream()
            .map(playerVocabularyStats -> {
                PlayerEntity player = playerVocabularyStats.get(0).getPlayer();

                Map<String, PlayerVocabularyStatsEntity> playerVocabulariesMap = playerVocabularyStats
                    .stream()
                    .collect(Collectors.toMap(PlayerVocabularyStatsEntity::getVocabularyCode, f -> f));

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

        logger.debug("Converted player dto: \n {}", multiVocabularyTopDto);

        // todo: pass to ftl exporter
    }
}
