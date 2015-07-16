package com.sunny.jsonserializer;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.jsonserializer.util.ReflectionUtils;

public class JSONSerializer {

	private static final Logger logger = LoggerFactory
			.getLogger(JSONSerializer.class);

	public boolean USE_METHOD = true;

	public boolean SKIP_NULL_FIELDS = false;

	private JSONWriter jsonWriter = null;

	private Set<Object> vistedObjects = new HashSet<Object>();

	public void serialize(Writer w, Object value) {

		if (value == null) {
			String msg = "Object to be serialized cannot be null";
			logger.error(msg);
			throw new NullPointerException(msg);
		}

		int depth = 0;

		jsonWriter = new JSONWriter();

		try {

			if (USE_METHOD) {

				serializeUsingMethodBasedReflection(value, depth);

			} else {

				serializeUsingFieldBasedReflection(value, depth);
			}

			w.write(jsonWriter.getBuffer().toString());

		} catch (IOException e) {

			logger.error("Error during serialization ", e);

		} catch (Exception e) {

			logger.error("Error during serialization ", e);
			logger.error(jsonWriter.getBuffer().toString());
		}

	}

	/**
	 * 
	 * @param rootObject
	 * @param vistedObjects
	 * @param depth
	 */
	private void serializeUsingMethodBasedReflection(Object rootObject,
			int depth) {

		logger.debug("Begin serializeUsingMethodBasedReflection");

		if (rootObject == null || vistedObjects.contains(rootObject)) {

			if (rootObject == null) {

				logger.debug("Input is null for serializeUsingMethodBasedReflection");

			} else {

				logger.debug("The object is already visited for serializeUsingMethodBasedReflection");
			}

			return;
		}

		depth++;

		// --- add it to the visited set
		vistedObjects.add(rootObject);

		Class<? extends Object> currentClass = rootObject.getClass();

		logger.debug("Serializing at depth : " + depth);
		logger.debug("Serializing : " + currentClass);

		if (currentClass.isArray()) {

			serializeArray(rootObject);
			return;

		} else if (ReflectionUtils.isCollection(currentClass)) {

			serializeCollection(rootObject);
			return;
		}

		else if (ReflectionUtils.isMap(currentClass)) {

			serializeMap(rootObject);
			return;
		}

		jsonWriter.writeBeginObject();

		final Method[] methods = currentClass.getMethods();
		boolean isFirstComma = true;

		for (int i = 0; i < methods.length; i++) {

			Method method = methods[i];
			String fieldName = method.getName();

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			if (ReflectionUtils.isSkipped(method)) {
				logger.debug("Skipping method : " + method.getName());
				continue;
			}

			try {

				if (ReflectionUtils.isGetMethod(method)) {

					fieldName = ReflectionUtils.getFieldName(method);

				} else if (ReflectionUtils.isBooleanMethod(method)) {

					fieldName = ReflectionUtils.getBooleanFieldName(method);

				}

				logger.debug("Current Method : " + fieldName);
				logger.debug("Adding to visited fields : " + fieldName);

				Object value = method.invoke(rootObject);

				if (value == null) {

					if (SKIP_NULL_FIELDS) {
						continue;
					}

					if (!isFirstComma) {
						jsonWriter.writeComma();
					}
					isFirstComma = false;
					jsonWriter.writeField(fieldName);
					jsonWriter.writeNull();
					continue;
				}

				Class<? extends Object> typeOfValue = value.getClass();

				if (ReflectionUtils.isCollection(typeOfValue)) {

					if (!isFirstComma) {
						jsonWriter.writeComma();
					}
					isFirstComma = false;

					jsonWriter.writeField(fieldName);
					serializeCollection(value);

				} else if (typeOfValue.isArray()) {

					if (!isFirstComma) {
						jsonWriter.writeComma();
					}
					isFirstComma = false;

					jsonWriter.writeField(fieldName);
					serializeArray(value);

				} else if (ReflectionUtils.hasEnumConstants(typeOfValue)) {

					logger.debug("Serializing a EnumType : " + typeOfValue);

					if (!isFirstComma) {
						jsonWriter.writeComma();
					}
					isFirstComma = false;
					jsonWriter.writeField(fieldName);
					jsonWriter.writeValue(value, Enum.class);

				}

				else if (ReflectionUtils.isJavaType(typeOfValue)) {

					logger.debug("Serializing a JavaType : " + typeOfValue);

					if (!isFirstComma) {
						jsonWriter.writeComma();
					}
					isFirstComma = false;
					jsonWriter.writeField(fieldName);
					jsonWriter.writeValue(value, typeOfValue);

				} else {

					// --- custom type ... lets take the leap of
					// faith
					if (!vistedObjects.contains(value)) {

						if (!isFirstComma) {
							jsonWriter.writeComma();
						}
						isFirstComma = false;

						logger.debug("Serializing a CustomType : "
								+ typeOfValue);
						jsonWriter.writeField(fieldName);
						serializeUsingMethodBasedReflection(value, depth);

					}

				}

			} catch (IllegalAccessException e) {

				logger.error("Error during serialization ", e);

			} catch (IllegalArgumentException e) {

				logger.error("Error during serialization ", e);

			} catch (InvocationTargetException e) {

				logger.error("Error during serialization ", e);
			}

		} // --- End of processing all fields at this level

		jsonWriter.writeEndObject();

		logger.debug("End of Serializing at depth : " + depth);
		logger.debug("End Serializing : " + currentClass);

		logger.debug("End serializeUsingMethodBasedReflection");

	} // --- End serialize

	/**
	 * 
	 * @param rootObject
	 * @param vistedObjects
	 * @param depth
	 */
	private void serializeUsingFieldBasedReflection(Object rootObject, int depth) {

		if (rootObject == null || vistedObjects.contains(rootObject)) {
			return;
		}

		depth++;

		logger.debug("Serializing at depth : " + depth);

		if (rootObject.getClass().isArray()) {

			serializeArray(rootObject);
			return;

		} else if (ReflectionUtils.isCollection(rootObject.getClass())) {

			serializeCollection(rootObject);
			return;
		}

		else if (ReflectionUtils.isMap(rootObject.getClass())) {

			serializeMap(rootObject);
			return;
		}

		jsonWriter.writeBeginObject();

		final List<Field> fields = ReflectionUtils.getAllFieldNames(rootObject
				.getClass());

		boolean isFirstComma = true;

		for (int i = 0; i < fields.size(); i++) {

			Field field = fields.get(i);
			String fieldName = field.getName();
			logger.debug("Current Field : " + fieldName);

			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			try {

				logger.debug("Adding to visited fields : " + fieldName);

				// --- add it to the visited set
				vistedObjects.add(rootObject);

				Object value = field.get(rootObject);

				if (value == null) {
					continue;
				}

				if (!isFirstComma) {
					jsonWriter.writeComma();
				}
				isFirstComma = false;

				jsonWriter.writeField(fieldName);

				Class<? extends Object> typeOfValue = value.getClass();

				if (ReflectionUtils.isCollection(typeOfValue)) {

					serializeCollection(value);

				} else if (typeOfValue.isArray()) {

					serializeArray(value);

				} else if (ReflectionUtils.hasEnumConstants(typeOfValue)) {

					logger.debug("Serializing a EnumType : " + typeOfValue);
					jsonWriter.writeValue(value, Enum.class);

				}

				else if (ReflectionUtils.isJavaType(typeOfValue)) {

					logger.debug("Serializing a JavaType : " + typeOfValue);
					jsonWriter.writeValue(value, typeOfValue);

				} else {

					logger.debug("Serializing a CustomType : " + typeOfValue);

					// --- custom type
					serializeUsingFieldBasedReflection(value, depth);
				}

			} catch (IllegalAccessException e) {

				logger.error("Error during serialization ", e);

			} catch (IllegalArgumentException e) {

				logger.error("Error during serialization ", e);
			}

		} // --- End of processing all fields at this level

		jsonWriter.writeEndObject();

		logger.debug("End of Serializing at depth : " + depth);

	} // --- End serialize

	/**
	 * 
	 * @param rootObject
	 * @param vistedObjects
	 */
	private void serializeArray(Object rootObject) {

		logger.debug("Begin serializeArray");

		Object[] arrayObj = (Object[]) rootObject;

		jsonWriter.writeBeginArray();

		for (int i = 0; i < arrayObj.length; i++) {

			Object aObj = arrayObj[i];

			if (aObj == null) {
				break;
			}

			if (i != 0) {
				jsonWriter.writeComma();
			}

			Class<? extends Object> typeOfValue = aObj.getClass();

			if (ReflectionUtils.isJavaType(typeOfValue)) {

				jsonWriter.writeValue(aObj, typeOfValue);

			} else {

				if (USE_METHOD) {
					serializeUsingMethodBasedReflection(aObj, 0);

				} else {
					serializeUsingFieldBasedReflection(aObj, 0);
				}
			}
		}

		jsonWriter.writeEndArray();

		logger.debug("End serializeArray");
	}

	/**
	 * 
	 * @param rootObject
	 * 
	 */
	private void serializeCollection(Object rootObject) {

		logger.debug("Begin serializeCollection");

		Collection collectionObj = (Collection) rootObject;

		jsonWriter.writeBeginArray();

		int i = 0;

		int size = collectionObj.size();

		for (Object object : collectionObj) {

			// if (i > 0 && i < size - 1) {
			//
			// jsonWriter.writeComma();
			// }

			if (i != 0) {

				jsonWriter.writeComma();
			}

			Class<? extends Object> typeOfValue = object.getClass();

			if (ReflectionUtils.isJavaType(typeOfValue)) {

				jsonWriter.writeValue(object, typeOfValue);

			} else {

				if (USE_METHOD) {
					serializeUsingMethodBasedReflection(object, 0);

				} else {
					serializeUsingFieldBasedReflection(object, 0);
				}
			}

			i++;
		}

		jsonWriter.writeEndArray();

		logger.debug("End serializeCollection");
	}

	/**
	 * 
	 * @param rootObject
	 * 
	 */
	private void serializeMap(Object rootObject) {

		logger.debug("Begin serializeMap");

		@SuppressWarnings("unchecked")
		Map<Object, Object> collectionObj = (Map<Object, Object>) rootObject;

		jsonWriter.writeBeginObject();

		int i = 0;

		for (Entry<Object, Object> entry : collectionObj.entrySet()) {

			Object key = entry.getKey();
			Object value = entry.getValue();

			if (!(key instanceof String)) {
				throw new RuntimeException("Key of the map should be a string");
			}

			if (i != 0) {
				jsonWriter.writeComma();
			}

			String stringObj = (String) key;
			jsonWriter.writeField(stringObj);

			if (value == null) {
				jsonWriter.writeNull();
				continue;
			}

			Class typeOfValue = value.getClass();

			if (ReflectionUtils.isJavaType(typeOfValue)) {

				logger.debug("Serializing a JavaType : " + typeOfValue);
				jsonWriter.writeValue(value, typeOfValue);

			} else {

				if (USE_METHOD) {
					serializeUsingMethodBasedReflection(value, 0);

				} else {
					serializeUsingFieldBasedReflection(value, 0);
				}
			}

			i++;
		}

		jsonWriter.writeEndObject();

		logger.debug("End serializeMap");
	}
}
