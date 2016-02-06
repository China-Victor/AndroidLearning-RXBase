package com.kioli.rx.network.request;

import com.kioli.rx.network.response.HttpResponseData;

import java.io.IOException;

/**
 * Http client interface to perform http connections
 */
public interface HttpRequestExecutor {

	/**
	 * Execute a Http request
	 *
	 * @param httpRequestData data of the http request
	 *
	 * @return http response
	 *
	 * @throws IOException
	 */
	HttpResponseData executeHttpRequest(HttpRequestData httpRequestData) throws IOException;

}
