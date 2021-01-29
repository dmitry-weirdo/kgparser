package ru.klavogonki.kgparser;

import ru.klavogonki.kgparser.http.UrlConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Популярные нестандартные словари.
 */
public enum NonStandardDictionary implements Vocabulary {

    // most popular non-standard dictionaries
    NORMAL_IN_ENGLISH(5539, "Обычный in English", "Обычном in English"),
    ONE_HUNDRED_RUSSIAN(25856, "Соточка", "Соточке"),
    FREQUENCY_VOCABULARY(192, "Частотный словарь", "Частотном словаре"),
    SHORT_TEXTS(1789, "Короткие тексты", "Коротких текстах"),
    DIGITS_ONE_HUNDRED(62238, "Цифросоточка", "Цифросоточке"),
    FIVE_THOUSAND_MOST_POPULAR_WORDS(203, "5000 самых частых слов", "5000 самых частых словах"),
    ONE_HUNDRED(62586, "One Hundred", "One Hundred"),
    SHORT_TEXTS_IN_ENGLISH(14878, "Short Texts in English", "Short Texts in English"),
    MINI_MARATHON(6018, "Мини-марафон, 800 знаков", "Мини-марафоне, 800 знаков"),
    CYBERTEXT(6562, "Кибертекст", "Кибертексте"),
    CYBERTEXT_2(115606, "Кибертекст 2.0", "Кибертексте 2.0"),
    PINKIES_PLUS(3714, "Мизинцы+", "Мизинцах+"),
    TRAINING_INDEX_FINGERS(226, "Тренируем указательные", "Тренируем указательные"),
    RING_FINGERS(8223, "Безымянные", "Безымянных"),

    // Упражнения Хруста
    // номера словарей от 1 до 24 по порядку: 13571 13572 13573 13574 13583 13584 13585 13654 13656 13659
    // 13661 13663 13664 16346 16759 16762 17495 17497 17498 17499
    // 32013 32014 32015 32016
    HRUST_EXERCISE_1(13571, "Упражнение №1", "Упражнении №1"),
    HRUST_EXERCISE_2(13572, "Упражнение №2", "Упражнении №2"),
    HRUST_EXERCISE_3(13573, "Упражнение №3", "Упражнении №3"),
    HRUST_EXERCISE_4(13574, "Упражнение №4", "Упражнении №4"),
    HRUST_EXERCISE_5(13583, "Упражнение №5", "Упражнении №5"),
    HRUST_EXERCISE_6(13584, "Упражнение №6", "Упражнении №6"),
    HRUST_EXERCISE_7(13585, "Упражнение №7", "Упражнении №7"),
    HRUST_EXERCISE_8(13654, "Упражнение №8", "Упражнении №8"),
    HRUST_EXERCISE_9(13656, "Упражнение №9", "Упражнении №9"),
    HRUST_EXERCISE_10(13659, "Упражнение №10", "Упражнении №10"),
    HRUST_EXERCISE_11(13661, "Упражнение №11", "Упражнении №11"),
    HRUST_EXERCISE_12(13663, "Упражнение №12", "Упражнении №12"),
    HRUST_EXERCISE_13(13664, "Упражнение №13", "Упражнении №13"),
    HRUST_EXERCISE_14(16346, "Упражнение №14", "Упражнении №14"),
    HRUST_EXERCISE_15(16759, "Упражнение №15", "Упражнении №15"),
    HRUST_EXERCISE_16(16762, "Упражнение №16", "Упражнении №16"),
    HRUST_EXERCISE_17(17495, "Упражнение №17", "Упражнении №17"),
    HRUST_EXERCISE_18(17497, "Упражнение №18", "Упражнении №18"),
    HRUST_EXERCISE_19(17498, "Упражнение №19", "Упражнении №19"),
    HRUST_EXERCISE_20(17499, "Упражнение №20", "Упражнении №20"),
    HRUST_EXERCISE_21(32013, "Упражнение №21", "Упражнении №21"),
    HRUST_EXERCISE_22(32014, "Упражнение №22", "Упражнении №22"),
    HRUST_EXERCISE_23(32015, "Упражнение №23", "Упражнении №23"),
//    HRUST_EXERCISE_24(32016, "Упражнение №24", "Упражнении №24"),
    HRUST_EXERCISE_24(32016, "Упражнение заключительное", "Упражнении заключительном"),

    // Мультилингва
    // https://klavogonki.ru/forum/software/59/page6/#post108
    // http://klavogonki.ru/forum/events/18143/

    // номера словарей по возрастанию: normal 5539 8950 25130 29236 29464 29468 29510 29513 29521 29537 29616 29667 30395 30641 35239 40559 106181 106487 106554 106688 114083 114825 115209 115378 115424 115943 116073 116553 116554 116745 117862 122698 122702 122761 122888 122891 123163 130272 136302 136354 136403 136795 136908 137357 138500 141412 141415 141423 141609 141610 141960
//    NORMAL_IN_ENGLISH(5539, "Обычный in English", "Обычном in English"),
    NORMAL_IN_BULGARIAN(35239, "Обычный болгарский", "Обычном болгарском"),
    NORMAL_IN_DUTCH(29667, "Обычный нидерландский", "Обычном нидерландском"),
    NORMAL_IN_LATIN(117862, "Обычный латинский", "Обычном латинском"),
    NORMAL_IN_CZECH(29537, "Обычный чешский", "Обычном чешском"),
    NORMAL_IN_GEORGIAN(114083, "Обычный грузинский", "Обычном грузинском"),
    NORMAL_IN_GERMAN(8950, "Обычный немецкий", "Обычном немецком"),
    NORMAL_IN_SLOVENIAN(136302, "Обычный словенский", "Обычном словенском"),
    NORMAL_IN_FINNISH(29468, "Обычный финский", "Обычном финском"),
    NORMAL_IN_CROATIAN(29510, "Обычный хорватский", "Обычном хорватском"),
    NORMAL_IN_INDONESIAN(141610, "Обычный индонезийский", "Обычном индонезийском"),
    NORMAL_IN_AFRIKAANS(141415, "Обычный африкаанс", "Обычном африкаанс"),
    NORMAL_IN_SWAHILI(141423, "Обычный суахили", "Обычном суахили"),
    NORMAL_IN_SLOVAK(30641, "Обычный словацкий", "Обычном словацком"),
    NORMAL_IN_HEBREW(116745, "Обычный иврит", "Обычном иврите"),
    NORMAL_IN_ITALIAN(25130, "Обычный итальянский", "Обычном итальянском"),
    NORMAL_IN_SWEDISH(106688, "Обычный шведский", "Обычном шведском"),
    NORMAL_IN_ESTONIAN(115943, "Обычный эстонский", "Обычном эстонском"),
    NORMAL_IN_PORTUGUESE(29464, "Обычный португальский", "Обычном португальском"),
    NORMAL_IN_HUNGARIAN(30395, "Обычный венгерский", "Обычном венгерском"),
    NORMAL_IN_SPANISH(106487, "Обычный испанский", "Обычном испанском"),
    NORMAL_IN_AZERBAIJANI(122702, "Обычный азербайджанский", "Обычном азербайджанском"),
    NORMAL_IN_DANISH(122761, "Обычный датский", "Обычном датском"),
    NORMAL_IN_ALBANIAN(136354, "Обычный албанский", "Обычном албанском"),
    NORMAL_IN_MACEDONIAN(116554, "Обычный македонский", "Обычном македонском"),
    NORMAL_IN_BELARUSIAN(29616, "Обычный белорусский", "Обычном белорусском"),
    NORMAL_IN_TURKISH(40559, "Обычный Turkish", "Обычном Turkish"),
    TEXTS_IN_KAZAKH(115209, "Тексты на казахском", "Текстах на казахском"),
    NORMAL_IN_CATALAN(138500, "Обычный каталанский", "Обычном каталанском"),
    NORMAL_IN_POLISH(29513, "Обычный польский", "Обычном польском"),
    NORMAL_IN_FRENCH(29236, "Обычный Français", "Обычном Français"),
    NORMAL_IN_NORWEGIAN(106554, "Обычный норвежский", "Обычном норвежском"),
    NORMAL_IN_IRISH(141960, "Обычный ирландский", "Обычном ирландском"),
    NORMAL_IN_ICELANDIC(115378, "Обычный исландский", "Обычном исландском"),
    NORMAL_IN_ROMANIAN(29521, "Обычный румынский", "Обычном румынском"),
    NORMAL_IN_KOREAN(106181, "Корейский", "Корейском"),
    NORMAL_IN_GREEK(114825, "Обычный греческий", "Обычном греческом"),
    NORMAL_IN_VIETNAMESE(136908, "Обычный вьетнамский", "Обычном вьетнамском"),
    NORMAL_IN_LITHUANIAN(115424, "Обычный литовский", "Обычном литовском"),
    NORMAL_IN_UKRAINIAN(123163, "Обычный украинский", "Обычном украинском"),
    NORMAL_IN_ARMENIAN(116073, "Обычный армянский", "Обычном армянском"),
    NORMAL_IN_ESPERANTO(122888, "Обычный эсперанто", "Обычном эсперанто"),
    NORMAL_IN_HINDI(137357, "Обычный хинди", "Обычном хинди"),
    NORMAL_IN_MONGOLIAN(116553, "Обычный монгольский", "Обычном монгольском"),
    NORMAL_IN_BASHKIR(136795, "Обычный башкирский", "Обычном башкирском"),
    NORMAL_IN_LATVIAN(122698, "Обычный латышский", "Обычном латышском"),
    NORMAL_IN_ARABIC(130272, "Обычный арабский", "Обычном арабском"),
    NORMAL_IN_TATAR(141412, "Обычный татарский", "Обычном татарском"),
    NORMAL_IN_SERBIAN_CYRILLIC(122891, "Обычный сербский (кириллица)", "Обычном сербском (кириллице)"),
    NORMAL_IN_KYRGYZ(136403, "Обычный киргизский", "Обычном киргизском"),
    NORMAL_IN_BENGALI(141609, "Обычный бенгальский", "Обычном бенгальском"),
    ;


    NonStandardDictionary(final int code, final String name, final String namePrepositional) {
        this(Dictionary.NON_STANDARD_DICTIONARY_ID_PREFIX + code, name, namePrepositional);
    }

    NonStandardDictionary(final String code, final String name, final String namePrepositional) {
        this.code = code;
        this.name = name;
        this.namePrepositional = namePrepositional;
    }

    int getId() {
        return Dictionary.getDictionaryId(code);
    }

    public static List<NonStandardDictionary> getMultiLinguaNonStandardDictionaries() {
        List<NonStandardDictionary> dictionaries = List.of(
            NORMAL_IN_ENGLISH,
            NORMAL_IN_BULGARIAN,
            NORMAL_IN_DUTCH,
            NORMAL_IN_LATIN,
            NORMAL_IN_CZECH,
            NORMAL_IN_GEORGIAN,
            NORMAL_IN_GERMAN,
            NORMAL_IN_SLOVENIAN,
            NORMAL_IN_FINNISH,
            NORMAL_IN_CROATIAN,
            NORMAL_IN_INDONESIAN,
            NORMAL_IN_AFRIKAANS,
            NORMAL_IN_SWAHILI,
            NORMAL_IN_SLOVAK,
            NORMAL_IN_HEBREW,
            NORMAL_IN_ITALIAN,
            NORMAL_IN_SWEDISH,
            NORMAL_IN_ESTONIAN,
            NORMAL_IN_PORTUGUESE,
            NORMAL_IN_HUNGARIAN,
            NORMAL_IN_SPANISH,
            NORMAL_IN_AZERBAIJANI,
            NORMAL_IN_DANISH,
            NORMAL_IN_ALBANIAN,
            NORMAL_IN_MACEDONIAN,
            NORMAL_IN_BELARUSIAN,
            NORMAL_IN_TURKISH,
            TEXTS_IN_KAZAKH,
            NORMAL_IN_CATALAN,
            NORMAL_IN_POLISH,
            NORMAL_IN_FRENCH,
            NORMAL_IN_NORWEGIAN,
            NORMAL_IN_IRISH,
            NORMAL_IN_ICELANDIC,
            NORMAL_IN_ROMANIAN,
            NORMAL_IN_KOREAN,
            NORMAL_IN_GREEK,
            NORMAL_IN_VIETNAMESE,
            NORMAL_IN_LITHUANIAN,
            NORMAL_IN_UKRAINIAN,
            NORMAL_IN_ARMENIAN,
            NORMAL_IN_ESPERANTO,
            NORMAL_IN_HINDI,
            NORMAL_IN_MONGOLIAN,
            NORMAL_IN_BASHKIR,
            NORMAL_IN_LATVIAN,
            NORMAL_IN_ARABIC,
            NORMAL_IN_TATAR,
            NORMAL_IN_SERBIAN_CYRILLIC,
            NORMAL_IN_KYRGYZ,
            NORMAL_IN_BENGALI
        );

        // order by ids to have a stable order
        return dictionaries
            .stream()
            .sorted(Comparator.comparingInt(NonStandardDictionary::getId))
            .collect(Collectors.toList());
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNamePrepositional() {
        return namePrepositional;
    }

    @Override
    public String getLink() {
        return UrlConstructor.dictionaryPage(getId());
    }

    public final String code;
    public final String name;
    public final String namePrepositional;
}
