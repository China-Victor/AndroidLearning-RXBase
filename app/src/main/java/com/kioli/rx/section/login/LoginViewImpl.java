package com.kioli.rx.section.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kioli.rx.R;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.ui.InjectingActivity;
import com.kioli.rx.section.home.HomeViewImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoginViewImpl extends InjectingActivity implements View.OnClickListener, View.OnTouchListener, LoginContract.LoginView {

	private static final String STATE_PASSWORD_VISIBILITY_ICON = "statePasswordVisibilityIcon";

	@BindView(R.id.spinner)
	RelativeLayout spinner;
	@BindView(R.id.input_email_layout)
	TextInputLayout emailLayout;
	@BindView(R.id.input_email)
	TextInputEditText emailField;
	@BindView(R.id.input_password)
	TextInputEditText passwordField;
	@BindView(R.id.button_login)
	AppCompatButton buttonLogin;
	@BindView(R.id.button_list_users)
	AppCompatButton buttonListUsers;

	private Drawable _passwordVisibilityOff;
	private Drawable _passwordVisibilityOn;

	private boolean _isPasswordVisible;
	private PasswordTransformationMethod _passwordTransMethod;

	private LoginContract.LoginPresenter _loginPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_passwordVisibilityOn = ContextCompat.getDrawable(this, R.drawable.icon_visibility_on);
		_passwordVisibilityOff = ContextCompat.getDrawable(this, R.drawable.icon_visibility_off);
		_passwordTransMethod = new PasswordTransformationMethod();

		_loginPresenter = new LoginPresenterImpl();
		_loginPresenter.attachView(this);

		setListeners();

		passwordField.setTypeface(Typeface.DEFAULT);
		passwordField.setTransformationMethod(_passwordTransMethod);

		if (savedInstanceState != null) {
			_isPasswordVisible = savedInstanceState.getBoolean(STATE_PASSWORD_VISIBILITY_ICON, false);
		}
		setPasswordFieldBehaviourAndIcon();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_loginPresenter.detachView();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.view_login;
	}

	private void setListeners() {
		buttonLogin.setOnClickListener(this);
		buttonListUsers.setOnClickListener(this);
		emailField.addTextChangedListener(new EmailTextWatcher());
		passwordField.setOnTouchListener(this);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.button_login:
				_loginPresenter.clickLoginButton(emailField.getText().toString(), passwordField.getText().toString());
				break;
			case R.id.button_list_users:
				_loginPresenter.clickListUsersButton();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onTouch(final View view, final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (event.getRawX() >= (passwordField.getRight() - passwordField.getCompoundDrawables()[2].getBounds().width())) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						int indexCursor = passwordField.getSelectionEnd();
						_isPasswordVisible = !_isPasswordVisible;
						setPasswordFieldBehaviourAndIcon();
						passwordField.setSelection(indexCursor);
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void showLoading() {
		spinner.setVisibility(View.GONE);
	}

	@Override
	public void hideLoading() {
		spinner.setVisibility(View.GONE);
	}

	@Override
	public void onLoginSuccessful(@NonNull final User user) {
		final ArrayList<User> listUsers = new ArrayList<>();
		listUsers.add(user);
		final Intent intent = HomeViewImpl.getLaunchingIntent(this, listUsers);
		startActivity(intent);
	}

	@Override
	public void onListUsersSuccessful(@NonNull final List<User> listUsers) {
		final Intent intent = HomeViewImpl.getLaunchingIntent(this, new ArrayList<>(listUsers));
		startActivity(intent);
	}

	@Override
	public void onFailure(@NonNull final Throwable error) {
		error.printStackTrace();
		Toast.makeText(LoginViewImpl.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		outState.putBoolean(STATE_PASSWORD_VISIBILITY_ICON, _isPasswordVisible);
		super.onSaveInstanceState(outState);
	}

	private void setPasswordFieldBehaviourAndIcon() {
		if (_isPasswordVisible) {
			passwordField.setTransformationMethod(null);
			passwordField.setCompoundDrawablesWithIntrinsicBounds(null, null, _passwordVisibilityOn, null);
		} else {
			passwordField.setTransformationMethod(_passwordTransMethod);
			passwordField.setCompoundDrawablesWithIntrinsicBounds(null, null, _passwordVisibilityOff, null);
		}
	}

	private class EmailTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
		}

		@Override
		public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			final boolean isValidEmail = !TextUtils.isEmpty(editable) && android.util.Patterns.EMAIL_ADDRESS.matcher(editable).matches();

			if (isValidEmail || editable.length() == 0) {
				emailLayout.setErrorEnabled(false);
			} else {
				emailLayout.setError(getString(R.string.login_email_error));
				if (emailLayout.requestFocus()) {
					getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		}
	}
}