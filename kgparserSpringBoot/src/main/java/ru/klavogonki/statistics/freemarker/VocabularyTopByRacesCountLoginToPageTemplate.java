package ru.klavogonki.statistics.freemarker;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class VocabularyTopByRacesCountLoginToPageTemplate extends VocabularyTopLoginToPageTemplate {

    @Override
    public String getTemplatePath() {
        return "ftl/vocabulary-top-by-races-count-login-to-page.ftl";
    }

    @Override
    public void export(final String filePath) {
        // todo: validate keys presence?
        super.export(filePath);

        logger.debug(
            "Top by races count: saved login -> page mapping to file {}. Total logins: {}",
            filePath,
            getLoginToPage().size()
        );
    }
}
