package com.kioli.rx.data.manager;

import android.graphics.Bitmap;

import rx.Observable;

/**
 * Dao to get different data
 */
public interface MyManager {

	/**
	 * @return observable for an object of type bitmap, containing the meme image
	 */
	Observable<Bitmap> getRandomMeme();
}
