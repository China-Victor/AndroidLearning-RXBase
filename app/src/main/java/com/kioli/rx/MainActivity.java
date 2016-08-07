package com.kioli.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kioli.rx.binding.ClassWiring;
import com.kioli.rx.data.model.User;

import java.util.List;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private RelativeLayout _spinner;
	private TextView _resultUsers;

	private Subscription _subscriptionLogin;
	private Subscription _subscriptionUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_spinner = (RelativeLayout) findViewById(R.id.spinner);
		_resultUsers = (TextView) findViewById(R.id.result_users);

		findViewById(R.id.button_login).setOnClickListener(this);
		findViewById(R.id.button_getusers).setOnClickListener(this);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.button_login:
				doLogin();
				break;
			case R.id.button_getusers:
				callListUsers();
				break;
			default:
				break;
		}
	}

	private void doLogin() {
		_spinner.setVisibility(View.VISIBLE);
		subscribeLogin();
	}

	private void callListUsers() {
		_spinner.setVisibility(View.VISIBLE);
		subscribeListUsers();
	}

	private void subscribeLogin() {
		final User user = new User();
		user.setEmail("email");
		user.setPassword("password");

		_subscriptionLogin = ClassWiring.getInstance().getUserManager().registerUser(user)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SingleSubscriber<Void>() {
					@Override
					public void onSuccess(final Void result) {
						_spinner.setVisibility(View.GONE);
						_resultUsers.setText(user.getEmail() + " properly registered");
					}

					@Override
					public void onError(final Throwable error) {
						onCommonError(error);
					}
				});
	}

	private void subscribeListUsers() {
		_subscriptionUsers = ClassWiring.getInstance().getUserManager().getAllUsers()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SingleSubscriber<List<User>>() {
					@Override
					public void onSuccess(final List<User> listUsers) {
						String outcome = "";
						for(User user : listUsers) {
							outcome = outcome.concat("email: " + user.getEmail()
									+ ", password: " + user.getPassword()
									+ ", token: " + user.getToken() + "\n");
						}
						_spinner.setVisibility(View.GONE);
						_resultUsers.setText(outcome);
					}

					@Override
					public void onError(final Throwable error) {
						onCommonError(error);
					}
				});
	}

	@Override
	protected void onDestroy() {
		_subscriptionUsers.unsubscribe();
		_subscriptionLogin.unsubscribe();
		super.onDestroy();
	}

	private void onCommonError(final Throwable throwable) {
		throwable.printStackTrace();
		_spinner.setVisibility(View.GONE);
		Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

}
