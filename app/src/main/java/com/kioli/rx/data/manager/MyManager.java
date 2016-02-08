package com.kioli.rx.data.manager;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.kioli.rx.data.model.Acronym;
import com.kioli.rx.data.model.CombinedResult;

import rx.Observable;

/**
 * Dao to get different data
 */
public interface MyManager {

	/**
	 * @return observable for an object of type bitmap, containing the meme image
	 */
	Observable<Bitmap> getRandomMeme();

	/**
	 * @return observable for an object of type Acronym, containing the acronym most known meaning
	 */
	Observable<String> getAcronymMeaning(@NonNull String acronym);

	/**
	 * @return observable for a combined result of an Acronym and a Bitmap
	 */
	Observable<CombinedResult> getCombinedResult(@NonNull String acronym);

	/**
	 * @return observable for a result of an waterfall call (Acronym and then Bitmap)
	 */
	Observable<Bitmap> getWaterfallResult(@NonNull String acronym);
}
