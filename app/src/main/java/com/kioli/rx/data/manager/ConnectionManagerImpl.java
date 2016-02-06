package com.kioli.rx.data.manager;

import com.kioli.rx.network.request.HttpRequestExecutor;

public class ConnectionManagerImpl implements ConnectionManager {

	private final HttpRequestExecutor _executor;

	public ConnectionManagerImpl(HttpRequestExecutor executor) {
		_executor = executor;
	}

	@Override
	public HttpRequestExecutor getExecutor() {
		return _executor;
	}
}
