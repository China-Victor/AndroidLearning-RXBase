package com.kioli.rx.data.dao;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.kioli.rx.data.model.MemeResult;
import com.kioli.rx.network.MyException;
import com.kioli.rx.network.serializer.BitmapSerializer;

/**
 * Dao to get meme data
 */
public interface MyDao {

	/**
	 * @return memes data from the server
	 */
	MemeResult getMemes() throws MyException;

	/**
	 * @param url       url of the image to download
	 * @param width     desired width for the downloaded image
	 * @param height    desired height for the downloaded image
	 * @param scaleType type of scaling to apply to the image
	 *
	 * @return bitmap containing a random meme
	 *
	 * @throws MyException custom exception
	 */
	Bitmap getMemeImage(@NonNull String url,
						int width,
						int height,
						@NonNull BitmapSerializer.ScaleType scaleType) throws MyException;
}
