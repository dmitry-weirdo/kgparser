/**
 * User: 1
 * Date: 13.01.2012
 * Time: 23:36:17
 */
package ru.klavogonki.kgparser;

import java.util.List;

public interface Parser
{
	/**
	 * @param fileName ��� html-����� � ����������� �������
	 * @return ������ �������, ����������� �� ��������.
	 * // todo: ���������� ����������
	 */
	List<Player> getPlayers(String fileName);
}