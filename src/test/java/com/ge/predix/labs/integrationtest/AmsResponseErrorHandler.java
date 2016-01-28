package com.ge.predix.labs.integrationtest;

import java.io.*;
import java.nio.charset.*;

import org.apache.commons.io.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.web.client.*;


public class AmsResponseErrorHandler extends DefaultResponseErrorHandler {
    String errorResponseBody = null;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        MediaType contentType = response.getHeaders().getContentType();
        Charset charset = contentType != null ? contentType.getCharSet() : null;
        String body = null;
        try {
         body = IOUtils.toString(response.getBody(), "UTF-8");
        } catch (IOException e) {
        	//ignore
        }
        System.out.println(String.format("status code: %s, status text: %s, body : %s ", statusCode.value(), response.getStatusText(), body));
        switch (statusCode.series()) {
        case CLIENT_ERROR:
            throw new AmsHttpClientErrorException(statusCode, response.getStatusText(), body, charset);
        case SERVER_ERROR:
            throw new AmsHttpServerErrorException(statusCode, response.getStatusText(), body, charset);
        default:
            throw new RestClientException("Unknown status code [" + statusCode  + "]");
       }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return hasError(response.getStatusCode());
    }

    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR || statusCode
                .series() == HttpStatus.Series.SERVER_ERROR);
    }
}