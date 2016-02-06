package com.kioli.rx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kioli.rx.binding.ClassWiring;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<Bitmap> {

	private ImageView _image;
	private Button _button;
	private RelativeLayout _spinner;

	private Subscription _subscriptionMeme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_image = (ImageView) findViewById(R.id.image);
		_button = (Button) findViewById(R.id.button);
		_spinner = (RelativeLayout) findViewById(R.id.spinner);

		call();

		_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				call();
			}
		});
	}

	private void call() {
		_spinner.setVisibility(View.VISIBLE);

		final Observable<Bitmap> observable = ClassWiring.getMyManager().getRandomMeme();
		_subscriptionMeme = observable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this);
	}

	@Override
	protected void onDestroy() {
		_subscriptionMeme.unsubscribe();
		super.onDestroy();
	}

	@Override
	public void onCompleted() {
		Toast.makeText(this, "Finished observing!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onError(final Throwable throwable) {
		throwable.printStackTrace();
		_spinner.setVisibility(View.GONE);
		Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNext(final Bitmap bitmap) {
		_spinner.setVisibility(View.GONE);
		_image.setImageBitmap(bitmap);
	}
}
