package com.kioli.rx.network.request;

/**
 * Enum of HTTP/1.1 methods
 */
public enum HttpMethod {
	GET("GET"),
	DELETE("DELETE"),
	POST("POST"),
	PUT(("PUT"));

	private final String _method;

	HttpMethod(String method) {
		_method = method;
	}

	public String getMethod() {
		return _method;
	}
}
