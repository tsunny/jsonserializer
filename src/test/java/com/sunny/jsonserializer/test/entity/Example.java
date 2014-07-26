package com.sunny.jsonserializer.test.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Example {

	private Long id;
	private DummyEnumType type;
	private Date restartDate;
	private String guid;
	private Boolean isActive;
	private String balance;
	private String picture;
	private Long age;
	private String eyeColor;
	private String name;
	private String gender;
	private String company;
	private String email;
	private String phone;
	private String address;
	private String about;
	private String registered;
	private Double latitude;
	private Double longitude;
	private String greeting;
	private String favoriteFruit;
	private List<String> tags;
	private List<Friend> friends;
	private Map<String, Object> additionalProperties;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getFavoriteFruit() {
		return favoriteFruit;
	}

	public void setFavoriteFruit(String favoriteFruit) {
		this.favoriteFruit = favoriteFruit;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the friends
	 */
	public List<Friend> getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	/**
	 * @param additionalProperties
	 *            the additionalProperties to set
	 */
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	/**
	 * @return the restartDate
	 */
	public Date getRestartDate() {
		return restartDate;
	}

	/**
	 * @param restartDate
	 *            the restartDate to set
	 */
	public void setRestartDate(Date restartDate) {
		this.restartDate = restartDate;
	}

	/**
	 * @return the type
	 */
	public DummyEnumType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DummyEnumType type) {
		this.type = type;
	}

}