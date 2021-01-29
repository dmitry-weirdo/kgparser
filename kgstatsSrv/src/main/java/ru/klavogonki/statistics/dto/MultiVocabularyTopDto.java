package ru.klavogonki.statistics.dto;

import lombok.Data;

import java.util.List;

@Data
public class MultiVocabularyTopDto {

    // словари для показа в топе. Отсортированы в порядке показа
    private List<VocabularyDto> vocabularies;

    // игроки в топе
    private List<PlayerMultiVocabularyDto> players;
}
