package com.kioli.rx.data.manager;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kioli.rx.binding.ClassWiring;
import com.kioli.rx.data.model.AcronymResult;
import com.kioli.rx.data.model.CombinedResult;
import com.kioli.rx.data.model.Meme;
import com.kioli.rx.data.model.MemeResult;
import com.kioli.rx.network.serializer.BitmapSerializer;

import java.util.ArrayList;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MyManagerImpl implements MyManager {

	private Random _random = new Random();

	private ArrayList<Meme> _memes;

	@Override
	public Observable<String> getAcronymMeaning(@NonNull final String acronym) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try {
					final AcronymResult result = ClassWiring.getMyDao().getAcronymExplanation(acronym);
					subscriber.onNext(result.acronyms.get(0).meaning);
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

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

	private Observable<Bitmap> getSpecificMeme(@NonNull final String acronym) {
		return Observable.create(new Observable.OnSubscribe<Bitmap>() {
			@Override
			public void call(Subscriber<? super Bitmap> subscriber) {
				try {
					if (_memes == null) {
						final MemeResult memeResult = ClassWiring.getMyDao().getMemes();
						_memes = memeResult.data.memes;
					}

					final int memePosition = acronym.length();
					final Meme meme = _memes.get(memePosition);

					Log.i("LORENZO",  "thread:" + Thread.currentThread().getId() +
							" word: " + acronym + ", meme position: " + memePosition);

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

	@Override
	public Observable<CombinedResult> getCombinedResult(@NonNull final String acronym) {
		final Observable<Bitmap> obsMeme = getRandomMeme().subscribeOn(Schedulers.newThread());
		final Observable<String> obsAcronym = getAcronymMeaning(acronym).subscribeOn(Schedulers.newThread());

		return Observable.zip(obsMeme, obsAcronym, new Func2<Bitmap, String, CombinedResult>() {
			@Override
			public CombinedResult call(Bitmap memeImage, String acronymResult) {
				return new CombinedResult(acronymResult, memeImage);
			}
		});
	}

	@Override
	public Observable<Bitmap> getWaterfallResult(@NonNull final String acronym) {
		final Observable<String> obsAcronym = getAcronymMeaning(acronym);
		return obsAcronym.flatMap(new Func1<String, Observable<? extends Bitmap>>() {
			@Override
			public Observable<? extends Bitmap> call(final String s) {
				Log.i("LORENZO", "thread:" + Thread.currentThread().getId() + " word: " + s);
				return getSpecificMeme(s);
			}
		});
	}
}
