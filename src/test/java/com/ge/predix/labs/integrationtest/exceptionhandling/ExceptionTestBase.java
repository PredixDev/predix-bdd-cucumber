package com.ge.predix.labs.integrationtest.exceptionhandling;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.ge.ams.dto.Message;
import com.ge.predix.labs.integrationtest.ExceptionUtil;
import com.ge.predix.labs.integrationtest.RestTestBase;

public class ExceptionTestBase extends RestTestBase {
	protected void verifyErrorMessage(Message message) {
//		assertNotNull(message.getMessage());
//		assertNotNull(message.getErrors());
		Map<String, String> errors = ExceptionUtil.extractedErrors((message));
		for (Entry<String, String> error : errors.entrySet()) {
//			assertNotNull(error.getKey());
//			assertNotNull(error.getValue());
		}
	}
	
	protected boolean contains(Collection<String> errors, String message) throws Throwable {
		for (String error : errors) {
			if (error.contains(message)) {
				return true;
			}
		}
		return false;
	}

}