package com.kioli.rx.network.serializer;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * JSON serializer
 */
public class GsonJsonSerializer<T> implements Serializer<T> {

	private final Gson _gson;
	private final Class<T> _class;

	public GsonJsonSerializer(final Class<T> c) {
		_class = c;
		_gson = new GsonBuilder().create();
	}

	@Override
	public void writeTo(@NonNull final OutputStream outputStream,
						@NonNull final T obj) throws IOException {
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		outputStreamWriter.write(_gson.toJson(obj));
	}

	@Override
	public T readFrom(@NonNull final InputStream inputStream) throws IOException {
		final JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
		final T obj = _gson.fromJson(jsonReader, _class);
		jsonReader.close();
		return obj;
	}
}