package ru.klavogonki.statistics.dto;

import lombok.Data;

@Data
public class PlayerVocabularyDataDto {

    private Integer racesCount; // пробег игрока по словарю

    private Double averageSpeed; // средняя скорость игрока по словарю, знаков в минуту

    private Integer bestSpeed; // рекорд игрока по словарю, знаков в минуту

    private Double averageError; // процент ошибок игрока по словарю, в процентах

    private String haul; // время, проведённое игроком в словаре, в секундах. Отформатированное для показа в UI

    private Integer haulInteger; // true haul, for order number

    private Integer qual; // на сколько пройдена квалификация по словарю. Рекорды в словаре засчитываются в пределах 1.2 * qual.
}
