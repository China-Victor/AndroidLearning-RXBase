package com.kioli.rx.network.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Utility class to facilitate operations with http requests
 */
public final class HttpRequestUtil {

	private HttpRequestUtil() {
	}

	/**
	 * Build flat json string with provided values
	 *
	 * @param values map of the values to create the http body with
	 *
	 * @return json as a String
	 */
	public static String getBodyFromValues(final Map<String, Object> values) {
		if (values == null) {
			return "";
		} else {
			return new Gson().toJson(values);
		}
	}
}
