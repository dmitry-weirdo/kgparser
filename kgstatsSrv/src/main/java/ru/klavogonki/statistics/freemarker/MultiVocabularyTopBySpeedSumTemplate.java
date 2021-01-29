package ru.klavogonki.statistics.freemarker;

import lombok.extern.log4j.Log4j2;
import ru.klavogonki.statistics.dto.PlayerMultiVocabularyDto;
import ru.klavogonki.statistics.dto.VocabularyDto;

import java.util.List;

@Log4j2
public class MultiVocabularyTopBySpeedSumTemplate extends FreemarkerTemplate {

    private static final String VOCABULARIES_KEY = "vocabularies";
    private static final String PLAYERS_KEY = "players";

    private static final String PAGE_TITLE_KEY = "pageTitle";
    private static final String HEADER_KEY = "header";
    private static final String ADDITIONAL_HEADER_KEY = "additionalHeader";
    // todo: other parameters

    @Override
    public String getTemplatePath() {
        return "ftl/multi-vocabulary-top-by-speed-sum.ftl";
    }

    public MultiVocabularyTopBySpeedSumTemplate vocabularies(List<VocabularyDto> vocabularies) {
        templateData.put(VOCABULARIES_KEY, vocabularies);
        return this;
    }

    public List<VocabularyDto> getVocabularies() {
        return (List<VocabularyDto>) templateData.get(VOCABULARIES_KEY);
    }

    public MultiVocabularyTopBySpeedSumTemplate players(List<PlayerMultiVocabularyDto> players) {
        templateData.put(PLAYERS_KEY, players);
        return this;
    }

    public List<PlayerMultiVocabularyDto> getPlayers() {
        return (List<PlayerMultiVocabularyDto>) templateData.get(PLAYERS_KEY);
    }

    public MultiVocabularyTopBySpeedSumTemplate pageTitle(String pageTitle) {
        templateData.put(PAGE_TITLE_KEY, pageTitle);
        return this;
    }

    public MultiVocabularyTopBySpeedSumTemplate header(String header) {
        templateData.put(HEADER_KEY, header);
        return this;
    }

    public MultiVocabularyTopBySpeedSumTemplate additionalHeader(String additionalHeader) {
        templateData.put(ADDITIONAL_HEADER_KEY, additionalHeader);
        return this;
    }

    @Override
    public void export(final String filePath) {
        // todo: validate keys presence?
        super.export(filePath);

        logger.debug(
            "Multi-vocabulary top by speed sum: {} players in {} vocabularies exported to file {}",
            getPlayers().size(),
            getVocabularies().size(),
            filePath
        );
    }
}
