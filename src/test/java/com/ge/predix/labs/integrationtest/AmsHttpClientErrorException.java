package com.ge.predix.labs.integrationtest;

import java.nio.charset.*;

import org.springframework.http.*;

public class AmsHttpClientErrorException extends AmsJsonErrorMessageException {
	private static final long serialVersionUID = 1L;

	public AmsHttpClientErrorException(HttpStatus statusCode, String statusText, String body, Charset charsetl) {
		super(statusCode, statusText, body, charsetl);
	}

}
