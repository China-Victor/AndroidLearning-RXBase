package com.kioli.rx.core.mvp;

import org.jetbrains.annotations.Contract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter <T extends MVPView> implements MVPPresenter<T> {

	private T _view;
	private CompositeSubscription _compositeSubscription = new CompositeSubscription();

	@Override
	public void attachView(T mvpView) {
		_view = mvpView;
	}

	@Override
	public void detachView() {
		_compositeSubscription.clear();
		_view = null;
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

	protected void addSubscription(Subscription subscription) {
		_compositeSubscription.add(subscription);
	}
}
