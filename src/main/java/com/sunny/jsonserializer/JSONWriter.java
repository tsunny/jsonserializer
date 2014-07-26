package com.sunny.jsonserializer;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.jsonserializer.util.ReflectionUtils;

public class JSONWriter {

	private static final Logger logger = LoggerFactory
			.getLogger(JSONWriter.class);

	private StringBuffer buffer = new StringBuffer();

	private boolean nextIsComma = false;

	private String latestToken = "";

	private ObjectMapper objectMapper = new ObjectMapper();

	public void writeBeginObject() {
		latestToken = ParserConstants.BEGIN_OBJECT;
		buffer.append(latestToken);
	}

	public void writeEndObject() {

		latestToken = ParserConstants.END_OBJECT;
		buffer.append(latestToken);
	}

	public void writeComma() {
		latestToken = ParserConstants.COMMA;
		buffer.append(latestToken);
	}

	public void writeField(String fieldName) {

		buffer.append("\"" + fieldName + "\"");
		writeFieldSeparator();
	}

	public void writeFieldSeparator() {
		buffer.append(":");
	}

	public void writeBeginArray() {
		buffer.append("[");
	}

	public void writeEndArray() {
		buffer.append("]");
	}

	public void writeNull() {
		buffer.append("null");
	}

	public void writeValue(Object value) {
		buffer.append(value);
	}

	public void writeValue(Object value, Class type) {

		try {

			if (ReflectionUtils.isStringType(type)) {

				String string = objectMapper.writeValueAsString(value);
				buffer.append(string);

			} else if (ReflectionUtils.isNumericType(type)) {

				buffer.append(value);

			} else if (ReflectionUtils.isBooleanType(type)) {

				buffer.append((Boolean) value);

			} else if (ReflectionUtils.isEnum(type)) {

				buffer.append("\"" + ((Enum) value).toString() + "\"");

			} else if (ReflectionUtils.isDateType(type)) {

				String string = objectMapper.writeValueAsString(value);
				buffer.append("\"" + string + "\"");
			}

		} catch (Exception e) {
			logger.error("Error during writing value : " + e);
		}
	}

	/**
	 * @return the nextIsComma
	 */
	public boolean isNextIsComma() {
		return nextIsComma;
	}

	/**
	 * @param nextIsComma
	 *            the nextIsComma to set
	 */
	public void setNextIsComma(boolean nextIsComma) {
		this.nextIsComma = nextIsComma;
	}

	/**
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer
	 *            the buffer to set
	 */
	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

}
