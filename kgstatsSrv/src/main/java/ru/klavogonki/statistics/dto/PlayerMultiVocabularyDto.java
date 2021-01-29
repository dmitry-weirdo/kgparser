package ru.klavogonki.statistics.dto;

import lombok.Data;
import ru.klavogonki.kgparser.Rank;
import ru.klavogonki.statistics.freemarker.OrderUtils;

import java.util.Map;

@Data
public class PlayerMultiVocabularyDto implements OrderUtils.OrderNumberDto {

    /**
     * Порядковый номер в таблице результатов.
     */
    private String orderNumber;

    // данные игрока, PlayerEntity

    private Integer playerId; // KG user id

    private String login;

    private Integer rankLevel;

    private Rank rank;

    private Integer blocked;

    // string since Java8 Date/Time formatting does not work in Freemarker
    // see https://stackoverflow.com/questions/32063276/java-time-java-8-support-in-freemarker
    private String registered;

    private Integer ratingLevel;

    private String profileLink;

    // todo: any other standard fields if required
    // todo: stats link?

    // агрегация данных по всем словарям
    private int totalVocabularies;

    private int totalRacesCount;

    private int bestSpeedsSum;

    private Double averageSpeed;

    private Double averageError;

    private String totalHaul;

    private Integer totalHaulInteger;

    private Map<String, PlayerVocabularyDataDto> vocabulariesMap; // vocabulary code to vocabulary data
}
