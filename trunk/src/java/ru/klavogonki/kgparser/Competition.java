/**
 * User: 1
 * Date: 17.01.2012
 * Time: 22:44:20
 */
package ru.klavogonki.kgparser;

import su.opencode.kefir.util.StringUtils;

import java.util.*;

/**
 * Соревнование. Содержит в себе набор заездов в рамках соревнования.
 * По заездам соревнования формируются результаты.
 */
public class Competition
{
	public Competition() {
		this.rounds = new ArrayList<>();
	}
	public Competition(List<Round> rounds) {
		this.rounds = rounds;
	}
	public Competition(String name, List<Round> rounds) {
		this.name = name;
		this.rounds = rounds;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Round> getRounds() {
		return rounds;
	}
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public void addRound(Round round) {
		// todo: add and check preventAdd flag
		this.rounds.add(round);
	}

	// todo: копия такого метода с учетом минимального числа заездов
	/**
	 * @return список всех игроков, принимавших участие хотя бы в одном заезде соревнования.
	 */
	public Set<Player> getPlayers() {
		Set<Player> players = new HashSet<>();

		for (Round round : rounds)
		{
			List<PlayerRoundResult> results = round.getResults();
			for (PlayerRoundResult result : results)
				players.add(result.getPlayer());
		}

		return players;
	}

	/**
	 * @return множество всех словарей, заезды по которым есть в соревновании.
	 */
	public Set<Dictionary> getDictionaries() {
		Set<Dictionary> dictionaries = new HashSet<>();

		for (Round round : rounds)
		{
			Dictionary dictionary = round.getDictionary();
			if (dictionary == null)
				throw new IllegalStateException( StringUtils.concat("Round ", round.getNumber(), " has no set dictionary") );

			dictionaries.add(dictionary);
		}

		return dictionaries;
	}

	/**
	 * @param dictionaryCode строковый  код словаря
	 * @return <code>true</code> &mdash; если соревнование содержит хотя бы один заезд по словарю с указанным кодом
	 * <br/>
	 * <code>false</code> &mdash; если соревнование не содержит ни одного заезда по словарю с указанным кодом
	 */
	public boolean containsDictionary(String dictionaryCode) {
		if ( StringUtils.empty(dictionaryCode) )
			throw new IllegalArgumentException("dictionaryCode must not be null or empty");

		// todo: think about iterating by rounds and use their dictionary instead of using this.getDictionaries
		for (Dictionary dictionary : this.getDictionaries())
			if ( dictionary.hasCode(dictionaryCode) )
				return true;

		return false;
	}

	/**
	 * @param dictionary словарь
	 * @return <code>true</code> &mdash; если соревнование содержит хотя бы один заезд по указанному словарю
	 * <br/>
	 * <code>false</code> &mdash; если соревнование не содержит ни одного заезда по указанному словарю
	 */
	public boolean containsDictionary(Dictionary dictionary) {
		if (dictionary == null)
			throw new IllegalArgumentException("dictionary cannot be null");

		return containsDictionary(dictionary.getCode());
	}


	/**
	 * @param dictionaryCode строковый код словаря
	 * @return количество заездов по словарю в соревновании. Больше либо равно 0.
	 */
	public int getRoundsCount(String dictionaryCode) {
		if ( StringUtils.empty(dictionaryCode) )
			throw new IllegalArgumentException("dictionaryCode must not be null or empty");

		int count = 0;

		for (Round round : rounds)
		{
			Dictionary dictionary = round.getDictionary();
			if (dictionary == null)
				throw new IllegalStateException( StringUtils.concat("Round ", round.getNumber(), " has no set dictionary") );

			if ( dictionary.hasCode(dictionaryCode) )
				count++;
		}

		return count;
	}

	/**
	 * @param dictionary словарь
	 * @return количество заездов по словарю в соревновании. Больше либо равно 0.
	 */
	public int getRoundsCount(Dictionary dictionary) {
		if (dictionary == null)
			throw new IllegalArgumentException("dictionary cannot be null");

		return getRoundsCount( dictionary.getCode() );
	}

	/**
	 * @return мэп сгруппированных по словарям заездов соревнования.
	 * Ключом является {@linkplain Dictionary#code строковый код} словаря.
	 */
	public Map<String, List<Round>> getRoundsByDictionariesMap() {
		Map<String, List<Round>> map = new HashMap<>();

		for (Round round : rounds)
		{
			Dictionary dictionary = round.getDictionary();
			if (dictionary == null)
				throw new IllegalStateException( StringUtils.concat("Round ", round.getNumber(), " has no set dictionary") );

			String dictionaryCode = dictionary.getCode();

			if ( map.containsKey(dictionaryCode) )
			{ // map already contains round's dictionary -> add round to the existing list
				List<Round> rounds = map.get(dictionaryCode);
				rounds.add(round);
			}
			else
			{ // map does not contain round's dictionary -> create new list, put round to it and put it to the map with the dictionary code key
				List<Round> rounds = new ArrayList<>();
				rounds.add(round);

				map.put(dictionaryCode, rounds);
			}
		}

		// todo: sort all lists by number

		return map;
	}

	/**
	 * Название соревнования.
	 */
	private String name;

	/**
	 * Заезды в рамках соревнования.
	 */
	private List<Round> rounds;
}