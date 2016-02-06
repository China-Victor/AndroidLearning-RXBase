package com.kioli.rx.data.manager;

import android.graphics.Bitmap;

import com.kioli.rx.binding.ClassWiring;
import com.kioli.rx.data.model.Meme;
import com.kioli.rx.data.model.MemeResult;
import com.kioli.rx.network.serializer.BitmapSerializer;

import java.util.ArrayList;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;

public class MyManagerImpl implements MyManager {

	private Random _random = new Random();

	private ArrayList<Meme> _memes;

	@Override
	public Observable<Bitmap> getRandomMeme() {
		return Observable.create(new Observable.OnSubscribe<Bitmap>() {
			@Override
			public void call(Subscriber<? super Bitmap> subscriber) {
				try {
					if (_memes == null) {
						final MemeResult memeResult = ClassWiring.getMyDao().getMemes();
						_memes = memeResult.data.memes;
					}

					final int memePosition = _random.nextInt((_memes.size()));
					final Meme meme = _memes.get(memePosition);

					final Bitmap bitmap = ClassWiring.getMyDao().getMemeImage(meme.url,
							meme.width,
							meme.height,
							BitmapSerializer.ScaleType.NO_SCALE);

					subscriber.onNext(bitmap);
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}
}
