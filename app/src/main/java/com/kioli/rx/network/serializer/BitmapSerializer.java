package com.kioli.rx.network.serializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Bitmap serializer
 */
public class BitmapSerializer implements Serializer<Bitmap> {

	public enum ScaleType {
		NO_SCALE,
		ASPECT_FILL,
		ASPECT_FIT,
		CONSTRAINT_SIZE
	}

	private final ScaleType _scaleType;
	private final int _width;
	private final int _height;

	public BitmapSerializer(final ScaleType scaleType, final int width, final int height) {
		_scaleType = scaleType;
		_width = width;
		_height = height;
	}

	@Override
	public void writeTo(@NonNull final OutputStream outputStream,
						@NonNull final Bitmap bitmap) throws IOException {
		bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, outputStream);
	}

	@Override
	public Bitmap readFrom(@NonNull final InputStream inputStream) throws IOException {
		if (_scaleType == ScaleType.NO_SCALE) {
			return BitmapFactory.decodeStream(inputStream);
		}

		final byte[] data = IOUtils.toByteArray(inputStream);
		final InputStream decodeBoundsInput = new ByteArrayInputStream(data);
		final InputStream decodeImageInput = new ByteArrayInputStream(data);

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(decodeBoundsInput, null, options);
		decodeBoundsInput.close();

		options.inSampleSize = calculateSampleSize(_scaleType, options.outWidth, options.outHeight);

		options.inJustDecodeBounds = false;
		final Bitmap bmp = BitmapFactory.decodeStream(decodeImageInput, null, options);
		decodeImageInput.close();
		return bmp;
	}

	private int calculateSampleSize(@NonNull final ScaleType scaleType,
									final float srcWidth,
									final float srcHeight) {
		switch (scaleType) {
			case ASPECT_FIT: {
				final float srcAspect = srcWidth / srcHeight;
				final float dstAspect = (float) _width / (float) _height;

				if (srcAspect > dstAspect) {
					return (int) Math.floor((srcWidth / _width) + 1.0f);
				} else {
					return (int) Math.floor((srcHeight / _height) + 1.0f);
				}
			}

			case ASPECT_FILL: {
				final float srcAspect = srcWidth / srcHeight;
				final float dstAspect = (float) _width / (float) _height;

				if (srcAspect > dstAspect) {
					return (int) Math.floor((srcHeight / _height) + 1.0f);
				} else {
					return (int) Math.floor((srcWidth / _width) + 1.0f);
				}
			}

			case CONSTRAINT_SIZE:
				if (srcWidth > _width || srcHeight > _height) {
					// The image is bigger than the maximum size, so do an ASPECT_FIT
					return calculateSampleSize(ScaleType.ASPECT_FIT, srcWidth, srcHeight);
				} else {
					// The image is smaller than the maximum size so we don't scale it
					return 1;
				}
		}

		// By default don't do any scaling (ScaleType.NO_SCALE)
		return 1;
	}
}
