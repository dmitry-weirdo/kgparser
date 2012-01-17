/**
 * User: 1
 * Date: 17.01.2012
 * Time: 22:05:59
 */
package ru.klavogonki.kgparser;

/**
 * ���� ������. ����������� � ����������� �� ������� � ������� ������.
 */
public enum Rank
{
	/**
	 * �������. ������ < 100.
	 */
	novice,

	/**
	 * ��������. ������ �� 100 �� 199.
	 */
	amateur,

	/**
	 * �������. ������ �� 200 �� 299.
	 */
	cabman,

	/**
	 * �����. ������ �� 300 �� 399.
	 */
	pro,

	/**
	 * ������. ������ �� 400 �� 499.
	 */
	racer,

	/**
	 * ������. ������ �� 500 �� 599.
	 */
	maniac,

	/**
	 * ��������. ������ �� 600 �� 699.
	 */
	superman,

	/**
	 * �����������. ������ >= 700.
	 */
	cyberracer;

	/**
	 * @param normalRecord ������ � ������ "�������"
	 * @return ����, ��������������� ���������� �������
	 */
	public static Rank getRank(int normalRecord) {
		if (normalRecord < 100)
			return Rank.novice;

		if (normalRecord < 200)
			return Rank.amateur;

		if (normalRecord < 300)
			return Rank.cabman;

		if (normalRecord < 400)
			return Rank.pro;

		if (normalRecord < 500)
			return Rank.racer;

		if (normalRecord < 600)
			return Rank.maniac;

		if (normalRecord < 700)
			return Rank.superman;

		return Rank.cyberracer; // > 700 -> �����������
	}

	/**
	 * @param rank ����
	 * @return ���� �� ��, ��������������� �����, � ����� "#00FF02".
	 */
	public static String getColor(Rank rank) {
		switch (rank)
		{
			case novice: return "#8D8D8D";
			case amateur: return "#4F9A97";
			case cabman: return "#187818";
			case pro: return "#8C8100";
			case racer: return "#BA5800";
			case maniac: return "#BC0143";
			case superman: return "#5E0B9E";
			case cyberracer: return "#00037C";

			default: throw new IllegalArgumentException("Incorrect rank: " + rank);
		}
	}
}