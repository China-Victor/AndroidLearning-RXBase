package com.kioli.rx.network.request;

import android.content.ContentValues;

import com.kioli.rx.network.util.HttpRequestUtil;

import java.util.Map;

/**
 * Http service arguments
 */
public class ServiceArguments {

	public final static String CONTENT_TYPE_KEY = "Content-Type";
	private final static String CONTENT_TYPE_DEFAULT_VALUE = "application/json; charset=UTF-8";

	private final ContentValues _methodParams = new ContentValues();
	private final ContentValues _queryParams = new ContentValues();
	private final ContentValues _httpHeaders = new ContentValues();
	private final HttpMethod _httpMethod;
	private final String _body;

	public static ServiceArguments createHttpGet() {
		return new ServiceArguments(HttpMethod.GET);
	}

	public static ServiceArguments createHttpPost(final String body) {
		return new ServiceArguments(HttpMethod.POST, body);
	}

	public static ServiceArguments createHttpPost(final Map<String, Object> bodyValues) {
		return new ServiceArguments(HttpMethod.POST, bodyValues);
	}

	public static ServiceArguments createHttpPut(final String body) {
		return new ServiceArguments(HttpMethod.PUT, body);
	}

	public static ServiceArguments createHttpPut(final Map<String, Object> bodyValues) {
		return new ServiceArguments(HttpMethod.PUT, bodyValues);
	}

	public static ServiceArguments createHttpDelete() {
		return new ServiceArguments(HttpMethod.DELETE);
	}

	private ServiceArguments(final HttpMethod httpMethod) {
		_httpMethod = httpMethod;
		_body = null;
		setHttpHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_DEFAULT_VALUE);
	}

	private ServiceArguments(final HttpMethod httpMethod, final String body) {
		_httpMethod = httpMethod;
		_body = body;
		setHttpHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_DEFAULT_VALUE);
	}

	private ServiceArguments(final HttpMethod httpMethod, final Map<String, Object> bodyValues) {
		_httpMethod = httpMethod;
		_body = HttpRequestUtil.getBodyFromValues(bodyValues);
		setHttpHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_DEFAULT_VALUE);
	}

	/**
	 * Add a method parameter. The occurrences of key in the endpoint name will
	 * be replaced with the provided value
	 *
	 * @param key   key of the method parameter
	 * @param value value of the method parameter
	 */
	public void addMethodParameter(final String key, final String value) {
		_methodParams.put(key, value);
	}

	/**
	 * Add query parameter to the url (will be appended in the endpoint name
	 * starting with a ?)
	 *
	 * @param key   key of the query parameter
	 * @param value value of the query parameter
	 */
	public void addQueryParameter(final String key, final String value) {
		_queryParams.put(key, value);
	}

	/**
	 * Add Http header next to existing ones
	 *
	 * @param key   key of the header parameter
	 * @param value value of the header parameter
	 */
	public void addHttpHeader(final String key, final String value) {
		_httpHeaders.put(key, value);
	}

	/**
	 * Add Http header, replacing any existing header with the same key
	 *
	 * @param key   key of the header parameter
	 * @param value value of the header parameter
	 */
	public void setHttpHeader(final String key, final String value) {
		for (Map.Entry<String, Object> entry : _httpHeaders.valueSet()) {
			if (key.equals(entry.getKey())) {
				_httpHeaders.remove(key);
			}
		}

		_httpHeaders.put(key, value);
	}

	public ContentValues getMethodParams() {
		return _methodParams;
	}

	public ContentValues getQueryParams() {
		return _queryParams;
	}

	public ContentValues getHttpHeaders() {
		return _httpHeaders;
	}

	public HttpMethod getHttpMethod() {
		return _httpMethod;
	}

	public String getBody() {
		return _body;
	}

	@Override
	public String toString() {
		return "[methodParams=" + valuesToString(_methodParams) +
				", queryParams=" + valuesToString(_queryParams) +
				", httpHeaders=" + valuesToString(_httpHeaders) +
				", httpMethod=" + _httpMethod +
				", body=" + _body +
				"]";
	}

	private String valuesToString(final ContentValues contentValues) {
		final StringBuilder sb = new StringBuilder();
		if (contentValues == null) {
			sb.append("null");
		} else {
			sb.append("{\n");

			for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
				if (entry.getKey() == null) {
					sb.append("null");
				} else {
					sb.append(String.valueOf(entry.getKey()));
					sb.append(": ");
					sb.append(String.valueOf(entry.getValue()));
				}
				sb.append("\n");
			}
			sb.append("}");
		}

		return sb.toString();
	}

}
