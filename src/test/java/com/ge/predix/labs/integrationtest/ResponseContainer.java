package com.ge.predix.labs.integrationtest;

import java.util.*;

import org.springframework.http.*;

public class ResponseContainer<T> {
	public final List<T> objects;
	public final HttpHeaders httpHeaders;
	public final HttpStatus httpStatus;

	public ResponseContainer(List<T> objects, HttpHeaders httpHeaders, HttpStatus httpStatus) {
		this.objects = objects;
		this.httpHeaders = httpHeaders;
		this.httpStatus = httpStatus;
	}

}
