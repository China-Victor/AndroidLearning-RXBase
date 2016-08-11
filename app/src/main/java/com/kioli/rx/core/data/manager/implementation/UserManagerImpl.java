package com.kioli.rx.core.data.manager.implementation;

import android.support.annotation.NonNull;

import com.kioli.rx.core.binding.ClassWiring;
import com.kioli.rx.core.data.manager.UserManager;
import com.kioli.rx.core.data.model.User;

import java.util.List;

import rx.Scheduler;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;

public class UserManagerImpl implements UserManager {

	@Override
	public Single<List<User>> getAllUsers() {
		final ClassWiring classWiring = ClassWiring.getInstance();
		final Scheduler subscribeScheduler = classWiring.getSchedulerManager().getSubscribeScheduler();
		final Single<List<User>> single = classWiring.getUserDao().getAllUsers();

		return single
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(subscribeScheduler);
	}

	@Override
	public Single<Void> registerUser(@NonNull final User user) {
		final ClassWiring classWiring = ClassWiring.getInstance();
		final Scheduler subscribeScheduler = classWiring.getSchedulerManager().getSubscribeScheduler();
		final Single<Void> single = classWiring.getUserDao().registerUser(user);

		return single
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(subscribeScheduler);
	}

//	@Override
//	public Observable<CombinedResult> getCombinedResult(@NonNull final String acronym) {
//		final Observable<Bitmap> obsMeme = getRandomMeme().subscribeOn(Schedulers.newThread());
//		final Observable<String> obsAcronym = getAcronymMeaning(acronym).subscribeOn(Schedulers.newThread());
//
//		return Observable.zip(obsMeme, obsAcronym, new Func2<Bitmap, String, CombinedResult>() {
//			@Override
//			public CombinedResult call(Bitmap memeImage, String acronymResult) {
//				return new CombinedResult(acronymResult, memeImage);
//			}
//		});
//	}
//
//	@Override
//	public Observable<Bitmap> getWaterfallResult(@NonNull final String acronym) {
//		final Observable<String> obsAcronym = getAcronymMeaning(acronym);
//		return obsAcronym.flatMap(new Func1<String, Observable<? extends Bitmap>>() {
//			@Override
//			public Observable<? extends Bitmap> call(final String s) {
//				Log.i("LORENZO", "thread:" + Thread.currentThread().getId() + " word: " + s);
//				return getSpecificMeme(s);
//			}
//		});
//	}
}
