package com.kioli.rx.network.parsing;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.kioli.rx.network.serializer.BitmapSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap parser for web services returning images
 */
public class BitmapParser implements Parser<Bitmap> {

	private final BitmapSerializer _serializer;

	public BitmapParser(BitmapSerializer.ScaleType scaleType, int width, int height) {
		_serializer = new BitmapSerializer(scaleType, width, height);
	}

	@Override
	public Bitmap parse(@NonNull final InputStream inputStream) throws IOException {
		return _serializer.readFrom(inputStream);
	}

}
