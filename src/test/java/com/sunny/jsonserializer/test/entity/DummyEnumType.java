package com.sunny.jsonserializer.test.entity;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum DummyEnumType {

	ONE("ONE");

	private String value;

	DummyEnumType(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return value;
	}

	@JsonValue
	public String value() {
		return value;
	}

	@JsonCreator
	public static DummyEnumType fromValue(String typeCode) {
		for (DummyEnumType c : DummyEnumType.values()) {
			if (c.value.equals(typeCode)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Invalid Status type code: "
				+ typeCode);
	}

}