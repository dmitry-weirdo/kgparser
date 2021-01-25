package ru.klavogonki.statistics.excel.player;

import ru.klavogonki.statistics.dto.PlayerVocabularyDto;

import java.util.function.Function;

public class AverageSpeedColumn implements DoubleColumn<PlayerVocabularyDto> { // todo: generalize type to interface with #getAverageSpeed when required

    @Override
    public String getColumnName() {
        return "Средняя";
    }

    @Override
    public Function<PlayerVocabularyDto, Double> playerFieldGetter() {
        return PlayerVocabularyDto::getAverageSpeed;
    }
}
