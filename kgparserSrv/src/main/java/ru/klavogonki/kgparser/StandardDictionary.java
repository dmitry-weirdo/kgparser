package ru.klavogonki.kgparser;

/**
 * Copyright 2014 <a href="mailto:dmitry.weirdo@gmail.com">Dmitriy Popov</a>.
 * $HeadURL$
 * $Author$
 * $Revision$
 * $Date::                      $
 * <br>
 * <br>
 * Энум с названиями стандартных словарей (режимов), как они используются в ajax-api.
 */
public enum StandardDictionary implements Vocabulary
{
	/**
	 * Обычный.
	 */
	normal,

	/**
	 * Безошибочный.
	 */
	noerror,

	/**
	 * Буквы.
	 */
	chars,

	/**
	 * Марафон.
	 */
	marathon,

	/**
	 * Абракадабра.
	 */
	abra,

	/**
	 * Яндекс.Рефераты.
	 */
	referats,

	/**
	 * Цифры.
	 */
	digits,

	/**
	 * Спринт.
	 */
	sprint,
	;

	/**
	 * @param dictionary стандартный словарь
	 * @return цвет стандартного словаря в формате &laquo;#123456&raquo;
	 */
	public static String getColor(StandardDictionary dictionary) {
		switch (dictionary)
		{
			case normal: return "#333333";
			case noerror: return "#4692AA";
			case chars: return "#B55900";
			case marathon: return "#D43E68";
			case abra: return "#3D4856";
			case referats: return "#698725";
			case digits: return "#777777";
			case sprint: return "#833F3A";

			default:
				throw new IllegalArgumentException("Unknown standard dictionary: " + dictionary); // todo: use concat
		}
	}

	/**
	 * @param dictionary стандартный словарь
	 * @return русское название словаря для отображения
	 */
	public static String getDisplayName(StandardDictionary dictionary) {
		switch (dictionary)
		{
			case normal: return "Обычный";
			case noerror: return "Безошибочный";
			case chars: return "Буквы";
			case marathon: return "Марафон";
			case abra: return "Абракадабра";
			case referats: return "Яндекс.Рефераты";
			case digits: return "Цифры";
			case sprint: return "Спринт";

			default:
				throw new IllegalArgumentException("Unknown standard dictionary: " + dictionary); // todo: use concat
		}
	}

	/**
	 * @param dictionary стандартный словарь
	 * @return русское название словаря для отображения, в предложном падеже
	 */
	public static String getDisplayNameInPrepositionalCase(StandardDictionary dictionary) {
		switch (dictionary)
		{
			case normal: return "Обычном";
			case noerror: return "Безошибочном";
			case chars: return "Буквах";
			case marathon: return "Марафоне";
			case abra: return "Абракадабре";
			case referats: return "Яндекс.Рефератах";
			case digits: return "Цифрах";
			case sprint: return "Спринте";

			default:
				throw new IllegalArgumentException("Unknown standard dictionary: " + dictionary); // todo: use concat
		}
	}

	/**
	 * @param dictionary стандартный словарь
	 * @return страница стандартного словаря в <a href="http://klavogonki.ru/wiki/">википедии клавогонок</a>.
	 */
	public static String getDictionaryPageUrl(StandardDictionary dictionary) {
		switch (dictionary)
		{
			case normal: return "http://klavogonki.ru/wiki/%D0%9E%D0%B1%D1%8B%D1%87%D0%BD%D1%8B%D0%B9";
			case noerror: return "http://klavogonki.ru/wiki/%D0%91%D0%B5%D0%B7%D0%BE%D1%88%D0%B8%D0%B1%D0%BE%D1%87%D0%BD%D1%8B%D0%B9";
			case chars: return "http://klavogonki.ru/wiki/%D0%91%D1%83%D0%BA%D0%B2%D1%8B";
			case marathon: return "http://klavogonki.ru/wiki/%D0%9C%D0%B0%D1%80%D0%B0%D1%84%D0%BE%D0%BD";
			case abra: return "http://klavogonki.ru/wiki/%D0%90%D0%B1%D1%80%D0%B0%D0%BA%D0%B0%D0%B4%D0%B0%D0%B1%D1%80%D0%B0";
			case referats: return "http://klavogonki.ru/wiki/%D0%AF%D0%BD%D0%B4%D0%B5%D0%BA%D1%81.%D0%A0%D0%B5%D1%84%D0%B5%D1%80%D0%B0%D1%82%D1%8B";
			case digits: return "http://klavogonki.ru/wiki/%D0%A6%D0%B8%D1%84%D1%80%D1%8B";
			case sprint: return "http://klavogonki.ru/wiki/%D0%A1%D0%BF%D1%80%D0%B8%D0%BD%D1%82";

			default:
				throw new IllegalArgumentException("Unknown standard dictionary: " + dictionary); // todo: use concat
		}
	}

	public static int getTextType(StandardDictionary dictionary) { // for non-standards it, will be dictionaryId
		switch (dictionary) {
			case normal:
			case noerror:
			case sprint:
			case marathon:
				return 0;

			case abra:
				return -1;

			case digits:
				return -2;

			case referats:
				return -3;

			case chars:
				return -4;

			default:
				throw new IllegalArgumentException("Unknown standard dictionary: " + dictionary); // todo: use concat
		}
	}

	public static boolean isValidStandardDictionaryCode(String code) {
		try {
			StandardDictionary.valueOf(code);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}


	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getName() {
		return getDisplayName(this);
	}

	@Override
	public String getNamePrepositional() {
		return getDisplayNameInPrepositionalCase(this);
	}
}
