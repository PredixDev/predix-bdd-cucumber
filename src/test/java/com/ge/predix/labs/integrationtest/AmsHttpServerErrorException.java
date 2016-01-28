package com.ge.predix.labs.integrationtest;

import java.nio.charset.*;

import org.springframework.http.*;

public class AmsHttpServerErrorException extends AmsJsonErrorMessageException {
	private static final long serialVersionUID = 1L;

	AmsHttpServerErrorException(HttpStatus statusCode, String statusText, String body, Charset charsetl) {
		super(statusCode, statusText, body, charsetl);
	}

}
