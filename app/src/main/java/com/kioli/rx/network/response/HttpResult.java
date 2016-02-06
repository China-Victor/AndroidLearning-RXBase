package com.kioli.rx.network.response;

/**
 * Wrapper around actual http result
 *
 * @param <T>
 */
public class HttpResult<T> {
	public T result;
	public int resultCode;
	public long lastModified;
}
