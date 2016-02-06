package com.kioli.rx.data.model;

import android.graphics.Bitmap;

public class CombinedResult {

	private final String _acronym;
	private final Bitmap _meme;

	public CombinedResult(final String acronym, final Bitmap memeImage) {
		_acronym = acronym;
		_meme = memeImage;
	}

	public String getAcronym() {
		return _acronym;
	}

	public Bitmap getMeme() {
		return _meme;
	}
}