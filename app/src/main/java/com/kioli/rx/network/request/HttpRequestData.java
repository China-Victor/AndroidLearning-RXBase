package com.kioli.rx.network.request;

/**
 * Class holding all data required for an Http request
 */
public class HttpRequestData {

	private final ServiceArguments _serviceArguments;
	private final String _url;

	public HttpRequestData(final String url, final ServiceArguments serviceArguments) {
		_url = url;
		_serviceArguments = serviceArguments;
	}

	public ServiceArguments getServiceArguments() {
		return _serviceArguments;
	}

	public String getUrl() {
		return _url;
	}

	public void setContentType(final String contentType) {
		_serviceArguments.setHttpHeader(ServiceArguments.CONTENT_TYPE_KEY, contentType);
	}
}
