package com.kioli.rx.section.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kioli.rx.R;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.ui.InjectingActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeViewImpl extends InjectingActivity {

	private final static String EXTRA_USERS = "extraUsers";

	@BindView(R.id.home_result)
	TextView result;

	public static Intent getLaunchingIntent(@NonNull final Context context,
	                                        final ArrayList<User> listUsers) {
		final Intent intent = new Intent(context, HomeViewImpl.class);
		intent.putParcelableArrayListExtra(EXTRA_USERS, listUsers);
		return intent;
	}

	private ArrayList<User> getLaunchingIntentContent() {
		final Intent launchingIntent = getIntent();
		if (launchingIntent != null) {
			return launchingIntent.getParcelableArrayListExtra(EXTRA_USERS);
		}
		return new ArrayList<>();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ArrayList<User> listUsers = getLaunchingIntentContent();
		String res = "";
		for (User user : listUsers) {
			res += user.getEmail() + ", " + user.getPassword() + ", " + user.getToken() + "\n";
		}
		result.setText(res);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.view_home;
	}
}
