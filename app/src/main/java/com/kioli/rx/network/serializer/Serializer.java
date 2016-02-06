package com.kioli.rx.network.serializer;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @param <T> The type to writeTo/readFrom
 */
public interface Serializer<T> {

	/**
	 * Serializes the given object.
	 *
	 * @param outputStream the stream of data in output
	 * @param value        the type to writeTo
	 *
	 * @throws IOException exception in Input/Output
	 */
	void writeTo(@NonNull OutputStream outputStream, @NonNull T value) throws IOException;

	/**
	 * @param inputStream containing data that should be serialized
	 *
	 * @return item of the type specified for the serializer
	 *
	 * @throws IOException exception in Input/Output
	 */
	T readFrom(@NonNull InputStream inputStream) throws IOException;
}
