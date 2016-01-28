package com.ge.predix.labs.common;

import static com.google.common.base.Throwables.*;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.*;
import org.json.simple.parser.*;

public final class JsonParsing {
	static private final JSONParser parser = new JSONParser();
	static private ObjectMapper mapper = new ObjectMapper();

	private JsonParsing() {
	}
	
	public static JsonNode getJsonNode(String json, String fieldName) throws JsonProcessingException, IOException {
		JsonNode rootNode = mapper.readTree(json);
		return rootNode.findValue(fieldName);
		
	}

	public static String get(String json, String field) {
		try {
			JSONArray array = toJSONArray(json);
			return array.isEmpty() ? null : (String) JSONObject.class.cast(array.iterator().next()).get(field);
		} catch (Exception e) {
			propagate(e);
			return null;
		}
	}

	public static String getFirstStringFromJSONArray(String json) {
		JSONArray array = toJSONArray(json);
		return array.isEmpty() ? null : array.iterator().next().toString();
	}

	public static JSONArray toJSONArray(String json) {
		try {
			return (JSONArray) parser.parse(json);
		} catch (org.json.simple.parser.ParseException e) {
			return new JSONArray();
		}
	}

}
