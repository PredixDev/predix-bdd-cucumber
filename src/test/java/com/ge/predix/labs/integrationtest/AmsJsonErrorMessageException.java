package com.ge.predix.labs.integrationtest;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ge.ams.dto.Message;

abstract class AmsJsonErrorMessageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public final HttpStatus statusCode;
    public final Charset charsetl;
    public final String statusText;
    public final String body;
    public final Message errorMessage;
    public final Map<String, String> errors;
    
    AmsJsonErrorMessageException(HttpStatus statusCode, String statusText, String body, Charset charsetl) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.charsetl = charsetl;
        this.body = body;
        this.errorMessage = ExceptionUtil.getErrorMessageFromJsonString(body);
        this.errors = ExceptionUtil.extractedErrors(errorMessage);
    }

    public String getBody() {
        return body;
    }

}
