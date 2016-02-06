package com.kioli.rx.network.parsing;

import android.support.annotation.NonNull;

import com.kioli.rx.network.serializer.GsonJsonSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Json parser for web services using GSON library
 *
 * @param <T>
 */
public class JsonParser<T> extends GsonJsonSerializer<T> implements Parser<T> {

	public JsonParser(final Class<T> c) {
		super(c);
	}

	@Override
	public T parse(@NonNull InputStream inputStream) throws IOException {
		return readFrom(inputStream);
	}
}
