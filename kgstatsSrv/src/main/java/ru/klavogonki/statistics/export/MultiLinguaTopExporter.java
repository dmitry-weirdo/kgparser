package ru.klavogonki.statistics.export;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.klavogonki.kgparser.NonStandardDictionary;
import ru.klavogonki.statistics.entity.PlayerEntity;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;
import ru.klavogonki.statistics.repository.PlayerVocabularyStatsRepository;
import ru.klavogonki.statistics.springboot.Profiles;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
@Profile(Profiles.DATABASE)
public class MultiLinguaTopExporter implements DataExporter {

    @Autowired
    protected PlayerVocabularyStatsRepository repository;

    @Override
    public void export(final ExportContext context) {

        // export multi-lingua
        List<NonStandardDictionary> nonStandardVocabularies = NonStandardDictionary.getMultiLinguaNonStandardDictionaries();

        List<String> nonStandardVocabularyCodes = nonStandardVocabularies
            .stream()
            .map(vocabulary -> vocabulary.code)
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

        int minDifferentVocabularies = 10; // todo: move to a constant

        List<Integer> playerIds = playerIdToVocabularyStats
            .values()
            .stream()
            .filter(playerVocabularyStatsEntities -> playerVocabularyStatsEntities.size() >= 10) // filter by different vocabularies count
            .map(playerVocabularyStatsEntities -> playerVocabularyStatsEntities.get(0).getPlayer().getPlayerId())
            .sorted()
            .collect(Collectors.toList());

        logger.debug("Player ids with stats for minimum {} different vocabularies, total {} players: {}", minDifferentVocabularies, playerIds.size(), playerIds);

        // todo: to not make the selection super-slow, select values for "normal" by a separate query with playerIds!
    }
}
