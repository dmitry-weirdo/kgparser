package ru.klavogonki.statistics.export;

import lombok.Data;

/**
 * <p>
 * Конфиг, настраивающий генерацию словарей.
 * </p>
 *
 * <p>
 * todo: по факту, в конфиг можно перенести настройки, которые сейчас реализуют классы,
 * реализующие VocabularyTopExporter.
 * </p>
 */
@Data
public class StatisticsGeneratorConfig {

    // aggregate tops
    private boolean exportIndexPage;

    private boolean exportTopBySpeed; // old exporter, not by normal vocabulary

    private boolean exportTop500Pages;

    private boolean exportPlayersByRank;

    // standard vocabularies tops
    private boolean exportNormalTop;
    private boolean exportAbraTop;
    private boolean exportReferatsTop;
    private boolean exportNoErrorTop;
    private boolean exportMarathonTop;
    private boolean exportCharsTop;
    private boolean exportDigitsTop;
    private boolean exportSprintTop;

    // non-standard vocabularies tops
    private boolean exportNormalInEnglishTop;
    private boolean exportMiniMarathonTop;
    private boolean exportShortTextsTop;
    private boolean exportFrequencyVocabularyTop;
    private boolean exportOneHundredRussianTop;
    private boolean exportDigitsOneHundredTop;
    private boolean exportTrainingIndexFingersTop;
    private boolean exportRingFingersTop;
    private boolean exportPinkiesPlusTop;

    // multi-vocabularies tops
    private boolean exportMultiLinguaTop;
}
