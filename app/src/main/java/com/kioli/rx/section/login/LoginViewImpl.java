package com.kioli.rx.section.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kioli.rx.R;
import com.kioli.rx.core.data.model.User;

public class LoginViewImpl extends AppCompatActivity implements View.OnClickListener, LoginContract.LoginView {

	private RelativeLayout _spinner;
	private TextInputLayout _emailLayout;
	private TextInputEditText _emailField;
	private TextInputEditText _passwordField;

	private LoginContract.LoginPresenter _loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_login);

		_spinner = (RelativeLayout) findViewById(R.id.spinner);
		_emailLayout = (TextInputLayout) findViewById(R.id.input_email_layout);
		_emailField = (TextInputEditText) findViewById(R.id.input_email);
		_passwordField = (TextInputEditText) findViewById(R.id.input_password);

		findViewById(R.id.button_login).setOnClickListener(this);
		_emailField.addTextChangedListener(new EmailTextWatcher());
		_passwordField.setTransformationMethod(new PasswordTransformationMethod());

		_loginPresenter = new LoginPresenterImpl();
		_loginPresenter.attachView(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_loginPresenter.detachView();
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.button_login:
				doLogin();
				break;
			default:
				break;
		}
	}

	@Override
	public void showLoading() {
		_spinner.setVisibility(View.GONE);
	}

	@Override
	public void hideLoading() {
		_spinner.setVisibility(View.GONE);
	}

	@Override
	public void onLoginSuccessful(@NonNull final User user) {
		// DO SOMETHING
	}

	@Override
	public void onLoginFailed(@NonNull final Throwable error) {
		error.printStackTrace();
		Toast.makeText(LoginViewImpl.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

	private void doLogin() {
		_spinner.setVisibility(View.VISIBLE);
		_loginPresenter.clickLoginButton(_emailField.getText().toString(), _passwordField.getText().toString());
	}

	public class EmailTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
		}

		@Override
		public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			final boolean isValidEmail = !TextUtils.isEmpty(editable) && android.util.Patterns.EMAIL_ADDRESS.matcher(editable).matches();

			if (isValidEmail) {
				_emailLayout.setErrorEnabled(false);
			} else {
				_emailLayout.setError(getString(R.string.login_email_error));
				if (_emailLayout.requestFocus()) {
					getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		}
	}
}