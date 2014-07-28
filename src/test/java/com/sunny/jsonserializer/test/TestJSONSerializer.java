package com.sunny.jsonserializer.test;

import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.jsonserializer.JSONSerializer;
import com.sunny.jsonserializer.test.entity.Example;
import com.sunny.jsonserializer.test.entity.SelfReferenceEntity;
import com.sunny.jsonserializer.test.utils.JSONParserTestUtils;

public class TestJSONSerializer {

	private static final Logger logger = LoggerFactory
			.getLogger(TestJSONSerializer.class);

	@Test
	public void test() throws Exception {

		String fileName = "/example-input.json";
		Object jsonObject = JSONParserTestUtils.readInputJSON(fileName,
				Example.class);

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(fileName, serializedJSON);

	}

	@Test
	public void testDateAndEnum() throws Exception {

		String fileName = "/example-input-date-enum.json";
		Object jsonObject = JSONParserTestUtils.readInputJSON(fileName,
				Example[].class);

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(fileName, serializedJSON);

	}

	@Test
	public void testEnum() throws Exception {

		String fileName = "/enum.json";
		Object jsonObject = JSONParserTestUtils.readInputJSON(fileName,
				SelfReferenceEntity.class);

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(fileName, serializedJSON);

	}

	@Test
	public void testExampleArray() throws Exception {

		String fileName = "/example-array-input.json";
		Object jsonObject = JSONParserTestUtils.readInputJSON(fileName,
				Example[].class);

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(fileName, serializedJSON);

	}

	@Test
	public void testExampleList() throws Exception {

		String fileName = "/example-list-input.json";
		Object jsonObject = JSONParserTestUtils.readInputJSON(fileName,
				new ArrayList<Example>().getClass());

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(fileName, serializedJSON);

	}

	@Test
	public void testSelfReference() throws Exception {

		String inputFileName = "/self-reference-simple-type.json";

		Object jsonObject = JSONParserTestUtils.readInputJSON(inputFileName,
				SelfReferenceEntity.class);

		StringWriter sw = new StringWriter();
		String serializedJSON = callSerialize(jsonObject, sw);

		logger.info("Output from serialize : " + serializedJSON);

		JSONParserTestUtils.assertJSONs(inputFileName, serializedJSON);
	}

	/**
	 * @param jsonObject
	 * @param sw
	 * @return
	 */
	private String callSerialize(Object jsonObject, StringWriter sw) {

		JSONSerializer js = new JSONSerializer();
		js.serialize(sw, jsonObject);
		String serializedJSON = sw.toString();
		return serializedJSON;
	}

}
