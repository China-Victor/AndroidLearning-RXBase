package com.kioli.rx.network.parsing;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parser interface
 *
 * @param <T>
 */
public interface Parser<T> {

	/**
	 * Parse incoming stream to an object
	 *
	 * @param inputStream data stream in input
	 *
	 * @return a new object from parsing the input
	 *
	 * @throws IOException
	 */
	T parse(@NonNull InputStream inputStream) throws IOException;

}