package com.kioli.rx.core.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class InjectingActivity extends AppCompatActivity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		ButterKnife.bind(this);
	}

	/**
	 * Provides the layout resource id
	 *
	 * @return layout resource id
	 */
	@LayoutRes
	protected abstract int getLayoutResId();
}
