package ru.klavogonki.statistics.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerVocabularyDataDto {

    private Integer racesCount; // пробег игрока по словарю

    private Double averageSpeed; // средняя скорость игрока по словарю, знаков в минуту

    private Integer bestSpeed; // рекорд игрока по словарю, знаков в минуту

    private Double averageError; // процент ошибок игрока по словарю, в процентах

    private String haul; // время, проведённое игроком в словаре, в секундах. Отформатированное для показа в UI

    private Integer haulInteger; // true haul, for order number

    private Integer qual; // на сколько пройдена квалификация по словарю. Рекорды в словаре засчитываются в пределах 1.2 * qual.

    // don't care about dirty
    // string since Java8 Date/Time formatting does not work in Freemarker
    // see https://stackoverflow.com/questions/32063276/java-time-java-8-support-in-freemarker
    private String updated; // Время апдейта результата игроков по словарю. Московское время.

    // exact value of PlayerVocabularyStatsEntity#updated, for Excel
    private LocalDateTime updatedDateTime;
}
