/**
 * User: 1
 * Date: 13.01.2012
 * Time: 23:33:06
 */
package ru.klavogonki.kgparser;

/**
 * ����� ����������.
 */
public class Player
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
	
	/**
	 * ��� �������.
	 */
	private Integer profileId;

	/**
	 * ������� (�����).
	 */
	private String name;
}