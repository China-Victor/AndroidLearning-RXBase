package com.kioli.rx.section.login;

import android.support.annotation.NonNull;

import com.kioli.rx.core.binding.ClassWiring;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.mvp.BasePresenter;

import java.util.List;

import rx.SingleSubscriber;

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

	@Override
	public void clickLoginButton(@NonNull final String email,
	                             @NonNull final String password) {
		final User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		checkViewAttached();
		getView().showLoading();
		addSubscription(ClassWiring.getInstance().getUserManager().registerUser(user)
				.subscribeOn(getIoScheduler())
				.observeOn(getMainScheduler())
				.subscribe(new SingleSubscriber<Void>() {
					@Override
					public void onSuccess(final Void result) {
						getView().hideLoading();
						getView().onLoginSuccessful(user);
					}

					@Override
					public void onError(final Throwable error) {
						getView().hideLoading();
						getView().onFailure(error);
					}
				}));
	}

	@Override
	public void clickListUsersButton() {
		checkViewAttached();
		getView().showLoading();
		addSubscription(ClassWiring.getInstance().getUserManager().getAllUsers()
				.subscribeOn(getIoScheduler())
				.observeOn(getMainScheduler())
				.subscribe(new SingleSubscriber<List<User>>() {
					@Override
					public void onSuccess(final List<User> result) {
						getView().hideLoading();
						getView().onListUsersSuccessful(result);
					}

					@Override
					public void onError(final Throwable error) {
						getView().hideLoading();
						getView().onFailure(error);
					}
				}));
	}
}
