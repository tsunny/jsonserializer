package com.sunny.jsonserializer.test.utils;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;

public class JSONParserTestUtils {

	public static Object readInputJSON(String fileName, Class jsonType)
			throws JsonParseException, JsonMappingException, IOException {

		InputStream inputStream = Object.class.getResourceAsStream(fileName);

		ObjectMapper mapper = new ObjectMapper();
		
		Object jsonObject = mapper.readValue(inputStream, jsonType);

		inputStream.close();

		return jsonObject;

	}

	/**
	 * @param fileName
	 * @param serializedJSON
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static void assertJSONs(String fileName, String serializedJSON)
			throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		InputStream inputStream = Object.class.getResourceAsStream(fileName);

		JsonNode expectedTree = mapper.readTree(inputStream);
		JsonNode actualTree = mapper.readTree(serializedJSON);

		if (!expectedTree.equals(actualTree)) {
			Assert.fail("JSONs are not same");
		}

		inputStream.close();

	}
}
