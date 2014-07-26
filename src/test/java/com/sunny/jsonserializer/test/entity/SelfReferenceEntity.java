package com.sunny.jsonserializer.test.entity;

/**
 * @contributors sunny
 * 
 */
public class SelfReferenceEntity {

	DummyEnumType type;

	private String name;

	private SelfReferenceEntity self;

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
	 * @return the self
	 */
	public SelfReferenceEntity getSelf() {
		return self;
	}

	/**
	 * @param self
	 *            the self to set
	 */
	public void setSelf(SelfReferenceEntity self) {
		this.self = self;
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
