/**
 * User: 1
 * Date: 13.01.2012
 * Time: 23:33:06
 */
package ru.klavogonki.kgparser;

import su.opencode.kefir.srv.json.Json;
import su.opencode.kefir.srv.json.JsonObject;

/**
 * Игрок клавогонок.
 */
public class Player extends JsonObject implements Comparable<Player>
{
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	public Integer getNormalRecord() {
		return normalRecord;
	}
	public void setNormalRecord(Integer normalRecord) {
		if (normalRecord == null)
		{
			this.normalRecord = null;
			return;
		}

		if (normalRecord <= 0)
			throw new IllegalArgumentException("Record must be > 0");

		this.normalRecord = normalRecord;
	}

	@Json(exclude = true)
	public Rank getRankByNormalRecord() {
		return Rank.getRankByNormalRecord(normalRecord);
	}
	@Json(exclude = true)
	public String getColor() {
		return Rank.getColor( getRank() );
	}

	@Json(exclude = true)
	public boolean isGuest() {
		return profileId == null;
	}

	public int compareTo(Player o) {
		return this.profileId.compareTo(o.profileId);
	}
	public boolean equals(Object player) {
		if (player instanceof Player)
			return this.profileId.equals( ((Player) player).getProfileId() );

		return false;
	}
	public int hashCode() {
		return (profileId != null ? profileId.hashCode() : 0);
	}

	/**
	 * @param round заезд
	 * @return <code>true</code> - если игрок принимал участие в заезде,
	 * <code>false</code> - в противном случае.
	 */
	@Json(exclude = true)
	public boolean isPresent(Round round) {
		for (PlayerRoundResult result : round.getResults())
			if (result.getPlayer().getProfileId().equals(this.profileId))
				return true;

		return false;
	}

	/**
	 * Код профиля.
	 */
	private Integer profileId;

	/**
	 * Никнейм (логин).
	 */
	private String name;

	/**
	 * Ранг.
	 */
	private Rank rank;

	/**
	 * Рекорд в обычном режиме. Должен быть больше 0.
	 */
	private Integer normalRecord;
}