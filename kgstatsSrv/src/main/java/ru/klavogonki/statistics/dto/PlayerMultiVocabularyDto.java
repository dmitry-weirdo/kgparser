package ru.klavogonki.statistics.dto;

import lombok.Data;
import ru.klavogonki.kgparser.Rank;

import java.util.List;

@Data
public class PlayerMultiVocabularyDto {

    /**
     * Порядковый номер в таблице результатов.
     */
    private String orderNumber;

    // данные игрока, PlayerEntity

    private Integer playerId; // KG user id

    private String login;

    private Rank rank;

    private Integer blocked;

    // string since Java8 Date/Time formatting does not work in Freemarker
    // see https://stackoverflow.com/questions/32063276/java-time-java-8-support-in-freemarker
    private String registered;

    private Integer ratingLevel;

    private String profileLink;

    // todo: any other standard if required

    private List<PlayerVocabularyDataDto> vocabulariesData;
}
