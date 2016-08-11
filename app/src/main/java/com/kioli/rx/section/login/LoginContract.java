package com.kioli.rx.section.login;

import android.support.annotation.NonNull;

import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.mvp.MVPPresenter;
import com.kioli.rx.core.mvp.MVPView;

import java.util.List;

public interface LoginContract {
	/**
	 * Interface for the LoginView (view)
	 */
	interface LoginView extends MVPView {

		/**
		 * Callback to the view for when the login is successful
		 *
		 * @param user the user just registered
		 */
		void onLoginSuccessful(@NonNull final User user);

		/**
		 * Callback to the view for when the login is successful
		 *
		 * @param listUsers the user just registered
		 */
		void onListUsersSuccessful(@NonNull final List<User> listUsers);

		/**
		 * Callback to the view for when a call fails
		 *
		 * @param error the error occurred
		 */
		void onFailure(@NonNull final Throwable error);

		/**
		 * Shows the loading page
		 */
		void showLoading();

		/**
		 * Hides the loading page
		 */
		void hideLoading();
	}

	/**
	 * Interface for the LoginPresenter
	 */
	interface LoginPresenter extends MVPPresenter<LoginView> {

		/**
		 * Method called when the login button is clicked
		 *
		 * @param email    email address of the user
		 * @param password password of the user
		 */
		void clickLoginButton(@NonNull final String email,
		                      @NonNull final String password);

		/**
		 * Method called when the button for the users list is clicked
		 */
		void clickListUsersButton();
	}
}
