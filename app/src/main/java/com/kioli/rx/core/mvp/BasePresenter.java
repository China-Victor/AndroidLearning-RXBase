package com.kioli.rx.core.mvp;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends MVPView> implements MVPPresenter<T> {

	private T _view;
	private CompositeSubscription _compositeSubscription = new CompositeSubscription();

	private Scheduler _ioScheduler = Schedulers.newThread();
	private Scheduler _mainScheduler = AndroidSchedulers.mainThread();

	@Override
	public void attachView(T mvpView) {
		_view = mvpView;
	}

	@Override
	public void detachView() {
		_compositeSubscription.clear();
		_view = null;
	}

	public Scheduler getIoScheduler() {
		return _ioScheduler;
	}

	public Scheduler getMainScheduler() {
		return _mainScheduler;
	}

	public void setIoScheduler(final Scheduler ioScheduler) {
		_ioScheduler = ioScheduler;
	}

	public void setMainScheduler(final Scheduler mainSchedulers) {
		_mainScheduler = mainSchedulers;
	}

	public T getView() {
		return _view;
	}

	public void checkViewAttached() {
		if (!isViewAttached()) {
			throw new MvpViewNotAttachedException();
		}
	}

	@Contract(pure = true)
	private boolean isViewAttached() {
		return _view != null;
	}

	protected void addSubscription(@NonNull final Subscription subscription) {
		_compositeSubscription.add(subscription);
	}
}
