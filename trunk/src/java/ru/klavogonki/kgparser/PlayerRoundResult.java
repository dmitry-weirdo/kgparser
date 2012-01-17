/**
 * User: 1
 * Date: 17.01.2012
 * Time: 22:25:37
 */
package ru.klavogonki.kgparser;

/**
 * ��������� ������ � ���������� ������.
 */
public class PlayerRoundResult
{
	public PlayerRoundResult() {
	}
	public PlayerRoundResult(Round round, Player player) {
		this.round = round;
		this.player = player;
	}

	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getPlace() {
		return place;
	}
	public void setPlace(Integer place) {
		if (place < 1)
			throw new IllegalArgumentException("Incorrect place: " + place);

		this.place = place;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		if (speed <= 0)
			throw new IllegalArgumentException("Incorrect speed: " + speed);

		this.speed = speed;
	}
	public Integer getErrorsCount() {
		return errorsCount;
	}
	public void setErrorsCount(Integer errorsCount) {
		if (errorsCount < 0)
			throw new IllegalArgumentException("Incorrect errorsCount: " + errorsCount);

		this.errorsCount = errorsCount;
	}
	public Float getErrorPercentage() {
		return errorPercentage;
	}
	public void setErrorPercentage(Float errorPercentage) {
		if (errorPercentage < 0)
			throw new IllegalArgumentException("Incorrect errorPercentage: " + errorPercentage);

		this.errorPercentage = errorPercentage;
	}
	public boolean isRecord() {
		return record;
	}
	public void setRecord(boolean record) {
		this.record = record;
	}
	public Float getTime() {
		return time;
	}
	public void setTime(Float time) {
		if (time < 0)
			throw new IllegalArgumentException("Incorrect time: " + time);

		this.time = time;
	}

	/**
	 * �����.
	 */
	private Round round;

	/**
	 * �����.
	 */
	private Player player;

	/**
	 * ����� � ������. ����� ���� 1 ��� ������.
	 */
	private Integer place;

	/**
	 * �������� ������, � ������\������. ������ ���� ������ 0.
	 */
	private Integer speed;

	/**
	 * ���������� ������. ����� ���� 0 ��� ������.
	 */
	private Integer errorsCount;

	/**
	 * ������� ������. ����� ���� 0 ��� ������.
	 */
	private Float errorPercentage;

	/**
	 * <code>true</code> - ���� ����� �������� ������ � ������ ������.
	 * <code>false</code> - � ��������� ������.
	 */
	private boolean record = false;

	/**
	 * ����� � ��������, �� ������� ����� ������ �����. ������ ���� ������ 0.
	 */
	private Float time;
}