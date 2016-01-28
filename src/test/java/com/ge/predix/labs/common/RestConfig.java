package com.ge.predix.labs.common;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.ge.ams.dto.Asset;
import com.ge.ams.dto.Customer;

@Component
@SuppressWarnings("unchecked")
public class RestConfig {

	public  String configFileName = "bddTest.properties";
	public  boolean isInitialized = false;

	private  String restHostname;
	private  String predixOathHostname;

	public final Properties prop = new Properties();
	public final HttpHeaders requestHeaders = new HttpHeaders();
	private Map<Class<?>, String> pathMap = new HashMap<>();
	private Map<Class<?>, TypeReference<?>> typeReferenceMap = new HashMap<>();
	public Paths paths;
	private HttpHost proxy;

	@PostConstruct
	private void init() {
		if (isInitialized)
			return;
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream(configFileName));
			restHostname = prop.getProperty("asset.service.base.url");
			predixOathHostname = prop.getProperty("predix.oauth.restHost");
			paths = new Paths(restHostname);
			initPathMap();
			initTypeReferenceMap();
			initRequestHeaders();
			/**
			 * Get proxy from browser settings (Chrome) Change Proxy Setings...
			 * after that your rest client does not through
			 * org.springframework.web.client.ResourceAccessException: I/O error
			 */
			if (!StringUtils.isEmpty(prop.getProperty("predix.oauth.proxyHost"))) {
				System.out.println("Set up Proxy - " + prop.getProperty("predix.oauth.proxyHost") + ":" + prop.getProperty("predix.oauth.proxyPort"));
	            proxy = new HttpHost(prop.getProperty("predix.oauth.proxyHost"), Integer.parseInt(prop.getProperty("predix.oauth.proxyPort")));
			}

			initToken();
			isInitialized = true;
		} catch (Exception e) {
			propagate(e);
		}
	}

	private void initToken() {
		System.out.println("Init Token");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		if (proxy != null) httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
 		List<Header> headerList = new ArrayList<Header>();
		headerList.add(new BasicHeader("Content-Type","application/x-www-form-urlencoded"));
		String oauthHeader = prop.getProperty("predix.oauth.authHeader");
		if (oauthHeader == null) oauthHeader = "Basic " + new String(Base64.encodeBase64(prop.getProperty("predix.oauth.clientId").getBytes()));
		headerList.add(new BasicHeader("Authorization", oauthHeader));
		headerList.add(new BasicHeader("Pragma", "no-cache"));
		String requestBody = "grant_type=client_credentials";
		String token = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = null;
			token = getToken(predixOathHostname, httpClient, requestBody, headerList);
			node = (ObjectNode) mapper.readTree(token);
			token = "Bearer " + node.get("access_token").asText();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(token);
		replaceHeader("Authorization", token);
		replaceHeader("Predix-Zone-Id", prop.getProperty("predix.asset.zoneid"));
		replaceHeader("Content-Type", "application/json;charset=UTF-8");
	}

	private String getToken(String url, CloseableHttpClient httpClient,
			String queryParams, List<Header> headers)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		String url2 = url;
		url2 += "?" + queryParams;
		HttpGet method = new HttpGet(url2);
		method.setHeaders(headers.toArray(new Header[headers.size()]));
		CloseableHttpResponse httpResponse = httpClient.execute(method);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("unable able to connect to the UAA url="
					+ url2 + " response=" + httpResponse);
		}
		HttpEntity responseEntity = httpResponse.getEntity();
		httpResponse.close();
		String token = EntityUtils.toString(responseEntity);
		return token;
	}
	
	public String getRestHostname() {
		return restHostname;
	}
	
	private void initPathMap() {
		pathMap.put(Customer.class, paths.customer);

	}

	private void initTypeReferenceMap() {
		typeReferenceMap.put(Object.class, new TypeReference<List<Object>>() {});
		typeReferenceMap.put(String.class, new TypeReference<List<String>>() {});
		typeReferenceMap.put(Asset.class, new TypeReference<List<Asset>>() {});
		typeReferenceMap.put(Customer.class, new TypeReference<List<Customer>>() {});
	}

	private void initRequestHeaders() {
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");
	}

	public void replaceHeader(String key, String value) {
		requestHeaders.remove(key);
		requestHeaders.set(key, value);
	}

	public String getPath(Class<?> clazz) {
		return pathMap.get(clazz);
	}

	public String getBaseUrl() {
		return paths.prefix;
	}

	public <T> TypeReference<List<T>> getTypeReference(Class<?> clazz) {
		return (TypeReference<List<T>>) typeReferenceMap.get(clazz);
	}

	public Class<?> getClassFromUri(String uri) {
		try {
			if (uri == null) {
				return null;
			}
			String path = paths.prefix + uri.substring(0, uri.indexOf("/", 2));
			for (Map.Entry<Class<?>, String> entry : pathMap.entrySet()) {
				if (entry.getValue().equals(path)) {
					return entry.getKey();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public class Paths {
		public final String prefix;
		public final String asset;
		public final String customer;

		private Paths(String prefix) {
			this.prefix = prefix;
			customer = prefix + "/customer";
			asset = prefix + "/asset";
		}
	}
}
