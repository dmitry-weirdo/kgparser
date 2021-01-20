package ru.klavogonki.statistics.export.vocabulary.standard;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.klavogonki.kgparser.StandardDictionary;

@Log4j2
@Component
public class MarathonTopExporter extends StandardVocabularyTopExporterDefaultImpl {

    @Override
    public StandardDictionary vocabulary() {
        return StandardDictionary.marathon;
    }

    @Override
    public int minRacesCount() {
        return 100;
    }

    @Override
    public Logger logger() {
        return logger;
    }
}
