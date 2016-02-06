package com.kioli.rx.data.manager;

import com.kioli.rx.network.request.HttpRequestExecutor;

/**
 * Manager handling the http request executor
 */
public interface ConnectionManager {

	/**
	 * @return the http request executor implementation specific to this connection manager
	 */
	HttpRequestExecutor getExecutor();
}
