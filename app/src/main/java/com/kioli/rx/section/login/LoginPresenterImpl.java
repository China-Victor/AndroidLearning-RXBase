package com.kioli.rx.section.login;

import com.kioli.rx.core.binding.ClassWiring;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.mvp.BasePresenter;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

	@Override
	public void clickLoginButton(final String email, final String password) {
		final User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		checkViewAttached();
		getView().showLoading();

		addSubscription(ClassWiring.getInstance().getUserManager().registerUser(user)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SingleSubscriber<Void>() {
					@Override
					public void onSuccess(final Void result) {
						getView().hideLoading();
						getView().onLoginSuccessful(user);
					}

					@Override
					public void onError(final Throwable error) {
						getView().hideLoading();
						getView().onLoginFailed(error);
					}
				}));
	}
}
