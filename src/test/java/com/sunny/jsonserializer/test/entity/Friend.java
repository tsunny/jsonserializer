package com.sunny.jsonserializer.test.entity;

import java.util.Set;

public class Friend {

	private Long id;

	private String name;

	private Set<Friend> friends;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the friends
	 */
	public Set<Friend> getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}

}
