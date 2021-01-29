package ru.klavogonki.statistics.repository;

import org.springframework.data.repository.CrudRepository;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;

import java.util.List;

public interface PlayerVocabularyStatsRepository extends CrudRepository<PlayerVocabularyStatsEntity, Long> {

    List<PlayerVocabularyStatsEntity> findByVocabularyCodeEqualsAndRacesCountGreaterThanEqualAndPlayerBlockedEqualsOrderByBestSpeedDesc(String vocabularyCode, int racesCount, int blocked);

    List<PlayerVocabularyStatsEntity> findByVocabularyCodeEqualsAndRacesCountGreaterThanEqualAndPlayerBlockedEqualsOrderByRacesCountDesc(String vocabularyCode, int racesCount, int blocked);

    List<PlayerVocabularyStatsEntity> findByVocabularyCodeEqualsAndRacesCountGreaterThanEqualAndPlayerBlockedEqualsOrderByHaulDesc(String vocabularyCode, int racesCount, int blocked);

    List<PlayerVocabularyStatsEntity> findByVocabularyCodeInAndPlayerBlockedEquals(List<String> vocabularyCodes, int blocked);

    List<PlayerVocabularyStatsEntity> findByVocabularyCodeInAndPlayer_PlayerIdInAndPlayerBlockedEquals(List<String> vocabularyCodes, List<Integer> playerId, int blocked);
}
