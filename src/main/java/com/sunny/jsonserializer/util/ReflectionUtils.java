package com.sunny.jsonserializer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionUtils {

	public static boolean isJavaType(Class type) {

		if (type.getName().startsWith("java.lang") || isDateType(type)) {
			return true;
		}

		return false;
	}

	public static boolean isStringType(Class type) {

		if (String.class.isAssignableFrom(type)) {
			return true;
		}

		return false;
	}

	public static boolean isBooleanType(Class type) {

		if (Boolean.class.isAssignableFrom(type)) {
			return true;
		}
		return false;
	}

	public static boolean isNumericType(Class type) {

		if (Number.class.isAssignableFrom(type)) {
			return true;
		}

		return false;
	}

	public static boolean isCollection(Class type) {

		if (Collection.class.isAssignableFrom(type)) {
			return true;
		}

		return false;
	}

	public static boolean isMap(Class type) {

		if (Map.class.isAssignableFrom(type)) {
			return true;
		}
		return false;
	}

	public static boolean isSkipped(Method method) {

		if (method.getName().equals("getClass")) {
			return true;
		}

		return false;
	}

	public static boolean isGetter(Method method) {

		if (!method.getName().startsWith("get")) {
			return false;
		}
		if (method.getParameterTypes().length != 0) {
			return false;
		}
		if (void.class.equals(method.getReturnType())) {
			return false;
		}
		return true;
	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set")) {
			return false;
		}
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		return true;
	}

	/**
	 * @param method
	 * @return
	 */
	public static String getFieldName(Method method) {

		String fieldName = method.getName().substring(3);

		String firstLetter = fieldName.substring(0, 1);

		fieldName = fieldName.replaceFirst(firstLetter,
				firstLetter.toLowerCase());

		return fieldName;
	}

	public static boolean isDateType(Class type) {

		if (Date.class.isAssignableFrom(type)
				|| Calendar.class.isAssignableFrom(type)) {
			return true;
		}
		return false;
	}

	public static boolean hasEnumConstants(Class<? extends Object> typeOfValue) {

		if (typeOfValue.getEnumConstants() != null) {
			return true;
		}
		return false;
	}

	public static boolean isEnum(Class type) {

		if (type.getName().equals(Enum.class.getName())) {
			return true;
		}
		return false;
	}

	public static List<Field> getAllFieldNames(Class type) {

		List<Field> fields = new ArrayList<Field>();

		while (type != null && type != Object.class
				&& type != LinkedHashMap.class && type != ArrayList.class) {

			Field[] declaredFields = type.getDeclaredFields();

			for (int i = 0; i < declaredFields.length; i++) {

				fields.add(declaredFields[i]);
			}

			// --- goto the superclass
			type = type.getSuperclass();

		}
		return fields;
	}
}
