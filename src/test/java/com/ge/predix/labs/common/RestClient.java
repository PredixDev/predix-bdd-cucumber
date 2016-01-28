package com.ge.predix.labs.common;

import static com.ge.predix.labs.common.JsonMapper.*;
import static org.junit.Assert.*;

import java.util.*;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import com.ge.predix.labs.factories.RequestContext;
import com.ge.predix.labs.integrationtest.*;

@Component
public class RestClient implements RestConstants {
    public static final String RESPONSE_HEADER = "RESPONSE_HEADER";
    @Autowired private RestConfig config;
    
	private RestTemplate rest = new RestTemplate();

	@PostConstruct
	private void init() {
		rest.setErrorHandler(new AmsResponseErrorHandler());
	}

	private HttpHeaders requestHeaders() {
		return config.requestHeaders;
	}

	public ResponseEntity<String> call(HttpMethod method, String url, String request) {
        ResponseEntity<String> responseEntity = rest.exchange(url, method, new HttpEntity<>(request, requestHeaders()), String.class);
        RequestContext.put(RESPONSE_HEADER, responseEntity.getHeaders());
        return responseEntity;
	}
	
	public void delete(String url) {
		printRequest(url, HttpMethod.DELETE, null);
		call(HttpMethod.DELETE, url, null);
	}

	public String get(String url) {
		return callGet(url).getBody();
	}

	public <T> ResponseContainer<T> get(Class<T> clazz, String filter) {
        String uri = config.getPath(clazz);
        if (filter != null) {
            uri = uri + "?filter=" + filter;
        }
        System.out.println("Query URL: " + uri);
        ResponseEntity<String> entity = call(HttpMethod.GET, uri, null);
        @SuppressWarnings("unchecked")
		List<T> objects =  (List<T>) fromJson(config.getTypeReference(clazz), entity.getBody());
        return new ResponseContainer<>(objects, entity.getHeaders(), entity.getStatusCode());
    }

	public <T> ResponseContainer<T> get(Class<T> clazz, String url, String filter) {
        if (filter != null) {
        	url = url + "?filter=" + filter;
        }
        System.out.println("Query URL: " + url);
        ResponseEntity<String> entity = call(HttpMethod.GET, url, null);
        @SuppressWarnings("unchecked")
		List<T> objects =  (List<T>) fromJson(config.getTypeReference(clazz), entity.getBody());
        return new ResponseContainer<>(objects, entity.getHeaders(), entity.getStatusCode());
    }
	
	public String post(String url, String request) {
		return callPost(url, request).getBody();
	}

	public String put(String url, String request) {
		return callAndTrace(HttpMethod.PUT, url, request).getBody();
	}

	public ResponseEntity<String> callGet(String url) {
		return callAndTrace(HttpMethod.GET, url, null);
	}
	
	public ResponseEntity<String> callPost(String url, String request) {
		return callAndTrace(HttpMethod.POST, url, request);
	}

	public void assertNotFound(String url) {
		printRequest(url, HttpMethod.GET, null);
		try {
			call(HttpMethod.GET, url, null).getBody();
		} catch (AmsHttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.statusCode);
		}
	}

	private ResponseEntity<String> callAndTrace(HttpMethod method, String url, String request) {
		printRequest(url, method, request);
        ResponseEntity<String> responseEntity = call(method, url, request);
        System.out.println("Response Header:\n" + toPrettyJson(responseEntity.getHeaders()));
        System.out.println("Response status code: " + responseEntity.getStatusCode());
		System.out.println("Response body(JSON):  " + responseEntity.getBody());
		return responseEntity;
	}

	private void printRequest(String url, HttpMethod method, String request) {
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("\nURL: " + url);
		System.out.println("HTTP method: " + method);
		System.out.println("Request headers: " + requestHeaders());
		System.out.println("Request body(JSON):  " + request);
	}

}
