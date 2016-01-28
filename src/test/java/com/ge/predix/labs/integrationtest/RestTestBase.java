package com.ge.predix.labs.integrationtest;

import static com.ge.predix.labs.common.JsonMapper.fromJson;
import static com.ge.predix.labs.common.JsonMapper.toJson;
import static com.ge.predix.labs.common.JsonMapper.toPrettyJson;
import static com.google.common.base.Throwables.propagate;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ge.predix.labs.common.RestClient;
import com.ge.predix.labs.common.RestConfig;

@ContextConfiguration(locations = {"classpath*:cucumber.xml"})
abstract public class RestTestBase extends AbstractJUnit4SpringContextTests implements RestConstants {
	protected static long SLEEP_TIME = 2000;
	protected static int MAX_ITEMS_PER_PAGE = 100;
	
	@Autowired
    protected RestConfig config;
    @Autowired
    protected RestClient rest;


    @Rule
    public TestName name = new TestName();

    @Before
    public void setup() {
        separateInfo("Start testing " + name.getMethodName());
    }

    @Test
    public void avoidAnnoyingErrorMessageWhenRunningTestsInAnt() {
        assertTrue(true); // do nothing;
    }
    
    @After
    public void teardown() {
        separateInfo("End testing " + name.getMethodName());
    }

    protected void separateInfo(String message) {
        String separator = "*******************";
        System.out.println("\n" + separator + message + separator + "\n");
    }

    protected HttpHeaders requestHeaders() {
        return config.requestHeaders;
    }

    protected void setTenant(String tenant) {
//        config.setTenant(tenant);
    }

    protected RestConfig getConfig() {
        return config;
    }

    @SuppressWarnings("unchecked")
    protected <T> T firstOf(Object collection) {
        return ((Collection<T>) collection).iterator().next();
    }


    protected void notFound(String uri) {
        rest.assertNotFound(config.paths.prefix + uri);
    }

    protected String generateName() {
        return getClass().getSimpleName() + "_" + UUID.randomUUID();
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> getObjectFromJson(Class<?> clazz, String response) {
        return (List<T>) fromJson(config.getTypeReference(clazz), response);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getOneObjectFromJson(Class<?> clazz, String response) {
    	return (T) fromJson(response, clazz);
    }

    protected void replaceHeader(String key, String value) {
        config.replaceHeader(key, value);
    }

    protected void delete(String... uris) {
        for (String uri : uris) {
            if (uri != null) {
                rest.delete(config.paths.prefix + uri);
            }
        }
    }

    protected <T> List<T> retrieve(Class<T> clazz, String uri) {
        List<T> objects = getObjectFromJson(clazz, rest.get(config.paths.prefix + uri));
        System.out.println("Retrieved JSon:\n" + toPrettyJson(objects));
        return objects;
    }
    
    protected String retrieve(String uri) {
    	return rest.get(config.paths.prefix + uri);
    }
    
    protected String get(String uri) {
    	return rest.get(uri);
    }

    protected <T> T retrieveOne(Class<T> clazz, String uri) {
        List<T> objects = getObjectFromJson(clazz, rest.get(config.paths.prefix + uri));
        System.out.println("Retrieved JSon:\n" + toPrettyJson(objects));
        return objects == null || objects.isEmpty() ? null : objects.get(0);
    }
    
    
    @SuppressWarnings("unchecked")
	protected <T> T retrieveById(Class<T> clazz, String uri) {
    	T objects = (T) getOneObjectFromJson(clazz, rest.get(config.paths.prefix + uri));
    	System.out.println("Retrieved JSon:\n" + toPrettyJson(objects));
    	return objects;
    }
    
	
	protected <T> List<T> retrieveAll(Class<T> clazz, String filter) throws Throwable {
		List<T> objects = new ArrayList<T>();
		ResponseContainer<T> response = null;
		do {
			response = rest.get(clazz, filter);
			objects.addAll(response.objects);
		} while	(updateRangeHeaderToNextPage(response, MAX_ITEMS_PER_PAGE));
		config.requestHeaders.remove(HTTP_HEADER_RANGE);
		return objects;
	}

	protected <T> boolean updateRangeHeaderToNextPage(ResponseContainer<T> response, int pageSize) {
		if (response == null) {
			return true;
		}
		String contentRange = response.httpHeaders.getFirst("Content-Range");
		int indexOfStart = contentRange.indexOf("items") + 6;
		int indexOfEnd = contentRange.indexOf("/");
		int indexOfSeparator = contentRange.indexOf("-");
		int start = Integer.parseInt(contentRange.substring(indexOfStart, indexOfSeparator));
		int end = Integer.parseInt(contentRange.substring(indexOfSeparator + 1, indexOfEnd));
		int total = Integer.parseInt(contentRange.substring(indexOfEnd + 1));
		int newStart = start + pageSize < total? start + pageSize : total - 1;
		int newEnd = end + pageSize < total? end + pageSize : total - 1;
		replaceHeader(HTTP_HEADER_RANGE, "items=" + newStart + "-" + newEnd);
		return end < total - 1;
	}

    protected <T> T theOne(ResponseContainer<T> response) {
        List<T> result = (List<T>) response.objects;
        return result.isEmpty() ? null : result.get(0);
    }

    @SuppressWarnings("unchecked")
    protected <T> T update(T t) {
        try {
            Method method = t.getClass().getDeclaredMethod("getUri", new Class<?>[0]);
            String uri = (String) method.invoke(t);
            System.out.println("Request:\n" + toPrettyJson(Arrays.<T>asList(t)));
            String response = rest.put(config.paths.prefix + uri, toJson(Arrays.<T>asList(t)));
            T result = (T) getObjectFromJson(t.getClass(), response).get(0);
            System.out.println("Response:\n" + toPrettyJson(result));
            return result;
        } catch (Exception e) {
            propagate(e);
            return null;
        }
    }
    
    protected  <T> String post( T t, String url) {
    	try {
    		System.out.println("Request:\n" + toPrettyJson(t));
    		String response = rest.post(config.paths.prefix + url, toJson(t));
    		return response;
    	} catch (Exception e) {
    		propagate(e);
    		return null;
    	}
    }
  
    protected  <T> String put( T t, String url) {
    	try {
    		System.out.println("Request:\n" + toPrettyJson(t));
    		String response = rest.put(config.paths.prefix + url, toJson(t));
    		return response;
    	} catch (Exception e) {
    		propagate(e);
    		return null;
    	}
    }
    
    protected <T> void updateWithNoResponse(T t) {
        try {
            Method method = t.getClass().getDeclaredMethod("getUri", new Class<?>[0]);
            String uri = (String) method.invoke(t);
            System.out.println("Request:\n" + toPrettyJson(Arrays.<T>asList(t)));
            rest.put(config.paths.prefix + uri, toJson(Arrays.<T>asList(t)));
        } catch (Exception e) {
            propagate(e);
        }
    }

    protected <T> void put(String url, T t) {
        try {
            System.out.println("Request:\n" + toPrettyJson(Arrays.<T>asList(t)));
            rest.put(url, toJson(Arrays.<T>asList(t)));
        } catch (Exception e) {
            propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T save(T t) {
        try {
            String url = config.getPath(t.getClass());
            String response = rest.post(url, toJson(Arrays.<T>asList(t)));
            T result = (T) getObjectFromJson(t.getClass(), response).get(0);
            System.out.println("Request:\n" + toPrettyJson(Arrays.<T>asList(t)));
            System.out.println("Response:\n" + toPrettyJson(result));
            return result;
        } catch (Exception e) {
            propagate(e);
            return null;
        }
    }
    
    protected <T> void saveWithNoResponse(T t) {
        try {
            String url = config.getPath(t.getClass());
            rest.post(url, toJson(Arrays.<T>asList(t)));
            System.out.println("Request:\n" + toPrettyJson(Arrays.<T>asList(t)));
        } catch (Exception e) {
            propagate(e);
        }
    }
}