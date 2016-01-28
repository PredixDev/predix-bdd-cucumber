package com.ge.predix.labs.common;


import static com.google.common.base.Throwables.*;

import java.net.*;

public final class UrlEncoder {
	private UrlEncoder() {
	}

	public static String encode(String content) {
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (Exception e) {
			propagate(e);
			return null;
		}
	}

}
