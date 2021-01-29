package ru.klavogonki.statistics.mapper;

import org.mapstruct.Mapper;
import ru.klavogonki.kgparser.Vocabulary;
import ru.klavogonki.statistics.dto.VocabularyDto;

import java.util.List;

@Mapper
public interface VocabularyDtoMapper {

    VocabularyDto vocabularyToDto(Vocabulary vocabulary);

    List<VocabularyDto> vocabularyListToDtoList(List<Vocabulary> vocabulary);
}
