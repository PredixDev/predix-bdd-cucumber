package com.ge.predix.labs.common;

import static com.google.common.base.Throwables.*;

import java.util.*;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.*;
import org.codehaus.jettison.json.*;

public final class JsonMapper {
	static private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

	private JsonMapper() {
	}

	public static <T> String toAllFieldsJson(T object) {
		try {
			mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw propagate(e);
		}
	}

	public static <T> String toJson(T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw propagate(e);
		}
	}

	public static <T> String toPrettyJson(T object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			throw propagate(e);
		}
	}
	
	public static <T> String toJsonOrBlank(T object) {
		try {
			return toJson(object);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw propagate(e);
		}
	}
	
	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
		try {
			return mapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			throw propagate(e);
		}
	}
	
	public static <T> T fromJson(TypeReference<T> type, String json) {
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			throw propagate(e);
		}
	}
	
	public static <T> T fromJsonOrNull(Class<T> clazz, String json) {
		try {
			return fromJson(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T> List<T> fromJsonArray(Class<T> clazz, String json) {
		try {
			List<T> results = new ArrayList<>();
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				results.add(fromJson(array.getString(i), clazz));
			}
			return results;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
    @SuppressWarnings("unchecked")
    public static <T> T copy(T t){
        return fromJson(toJson(t), (Class<T>)t.getClass());
    }

}
