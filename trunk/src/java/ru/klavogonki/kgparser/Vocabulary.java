/**
 * User: 1
 * Date: 17.01.2012
 * Time: 22:24:23
 */
package ru.klavogonki.kgparser;

/**
 * �������.
 */
public class Vocabulary
{
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	private int id;

	/**
	 * �������� �������.
	 */
	private String name;
}
