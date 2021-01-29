package ru.klavogonki.statistics.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.klavogonki.statistics.dto.PlayerMultiVocabularyDto;
import ru.klavogonki.statistics.entity.PlayerEntity;
import ru.klavogonki.statistics.entity.PlayerVocabularyStatsEntity;
import ru.klavogonki.statistics.freemarker.HaulUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlayerMultiVocabularyDtoMapper {

    @Data
    @AllArgsConstructor
    class InputWrapper {
        private PlayerEntity player;
        private Map<String, PlayerVocabularyStatsEntity> vocabularies;
    }

    @Mapping(source = "player.playerId", target = "playerId")
    @Mapping(source = "player.login", target = "login")
    @Mapping(source = "player.rankLevel", target = "rankLevel")
    @Mapping(source = "player.rank", target = "rank")
    @Mapping(source = "player.blocked", target = "blocked")
    @Mapping(source = "player.registered", target = "registered")
    @Mapping(source = "player.ratingLevel", target = "ratingLevel")
    @Mapping(source = "player.profileLink", target = "profileLink")
    @Mapping(source = "vocabularies", target = "vocabulariesData")
    PlayerMultiVocabularyDto toDto(InputWrapper input);

    List<PlayerMultiVocabularyDto> toDtoList(List<InputWrapper> inputList);

    @AfterMapping
    default void fillSummaryFields(InputWrapper input, @MappingTarget PlayerMultiVocabularyDto target) {
        Collection<PlayerVocabularyStatsEntity> vocabularies = input.vocabularies.values();

        int totalVocabularies = vocabularies.size();

        int totalRacesCount = vocabularies
            .stream()
            .mapToInt(PlayerVocabularyStatsEntity::getRacesCount)
            .sum();

        int recordsSum = vocabularies
            .stream()
            .mapToInt(PlayerVocabularyStatsEntity::getBestSpeed)
            .sum();

        double averageSpeed = vocabularies
            .stream()
            .mapToDouble(PlayerVocabularyStatsEntity::getAverageSpeed)
            .average()
            .orElseThrow(() -> new IllegalStateException(String.format("Cannot get averageSpeed for player %d", input.player.getPlayerId())));

        double averageError = vocabularies
            .stream()
            .mapToDouble(PlayerVocabularyStatsEntity::getAverageError)
            .average()
            .orElseThrow(() -> new IllegalStateException(String.format("Cannot get averageError for player %d", input.player.getPlayerId())));

        int totalHaul = vocabularies
            .stream()
            .mapToInt(PlayerVocabularyStatsEntity::getHaul)
            .sum();

        target.setTotalVocabularies(totalVocabularies);
        target.setTotalRacesCount(totalRacesCount);
        target.setBestSpeedsSum(recordsSum);
        target.setAverageSpeed(averageSpeed);
        target.setAverageError(averageError);
        target.setTotalHaul(HaulUtils.format(totalHaul));
        target.setTotalHaulInteger(totalHaul);
    }

    // todo: fill orderNumber with given function
}
