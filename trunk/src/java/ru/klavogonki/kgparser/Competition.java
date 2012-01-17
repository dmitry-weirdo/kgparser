/**
 * User: 1
 * Date: 17.01.2012
 * Time: 22:44:20
 */
package ru.klavogonki.kgparser;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * ������������. �������� � ���� ����� ������� � ������ ������������.
 * �� ������� ������������ ����������� ����������.
 */
public class Competition
{
	public Competition() {
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

	// todo: ����� ������ ������ � ������ ������������ ����� �������
	/**
	 * @return ������ ���� �������, ����������� ������� ���� �� � ����� ������ ������������.
	 */
	Set<Player> getPlayers() {
		Set<Player> players = new HashSet<Player>();

		for (Round round : rounds)
		{
			List<PlayerRoundResult> results = round.getResults();
			for (PlayerRoundResult result : results)
				players.add(result.getPlayer());
		}

		return players;
	}

	/**
	 * �������� ������������.
	 */
	private String name;

	/**
	 * ������ � ������ ������������.
	 */
	private List<Round> rounds;
}
