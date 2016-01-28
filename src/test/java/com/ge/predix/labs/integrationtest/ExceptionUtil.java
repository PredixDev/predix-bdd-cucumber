package com.ge.predix.labs.integrationtest;

import java.io.*;
import java.util.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.*;

import com.ge.ams.dto.*;

public abstract class ExceptionUtil {
	@SuppressWarnings("unchecked")
	public static Map<String, String> extractedErrors(Message message) {
		return (message == null) ? null : (Map<String, String>) message.getErrors();
	}

	public static Message getErrorMessageFromJsonString(String errorResponseBody) {
		if (errorResponseBody == null) {
			return null;
		}
		TypeReference<Message> listOfRefType = new TypeReference<Message>() {
		};
		try {
			return new ObjectMapper().readValue(errorResponseBody, listOfRefType);
		} catch (JsonParseException ignore) {
		} catch (IOException ignore) {
		}
		return null;
	}

}