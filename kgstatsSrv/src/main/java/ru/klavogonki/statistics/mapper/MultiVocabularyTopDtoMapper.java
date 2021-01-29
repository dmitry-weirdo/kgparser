package ru.klavogonki.statistics.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;
import ru.klavogonki.kgparser.Vocabulary;
import ru.klavogonki.statistics.dto.MultiVocabularyTopDto;

import java.util.List;

@Mapper(uses = {VocabularyDtoMapper.class, PlayerMultiVocabularyDtoMapper.class})
public interface MultiVocabularyTopDtoMapper {

    @Data
    @AllArgsConstructor
    class InputWrapper {
        private List<Vocabulary> vocabularies;
        private List<PlayerMultiVocabularyDtoMapper.InputWrapper> players;
    }

    MultiVocabularyTopDto toDto(InputWrapper input);
}
