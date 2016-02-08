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
import com.kioli.rx.data.model.CombinedResult;

import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private final static String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static Random rnd = new Random();
	private final static int MIN_ACRONYM_LENGTH = 2;
	private final static int MAX_ACRONYM_LENGTH = 4;

	private ImageView _image;
	private RelativeLayout _spinner;

	private Subscription _subscriptionMeme;
	private Subscription _subscriptionAcronym;
	private Subscription _subscriptionCombined;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_spinner = (RelativeLayout) findViewById(R.id.spinner);
		_image = (ImageView) findViewById(R.id.image);
		Button _buttonMeme = (Button) findViewById(R.id.button_meme);
		Button _buttonAcronym = (Button) findViewById(R.id.button_acronym);
		Button _buttonBoth = (Button) findViewById(R.id.button_both);

		_buttonMeme.setOnClickListener(this);
		_buttonAcronym.setOnClickListener(this);
		_buttonBoth.setOnClickListener(this);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.button_meme:
				callMeme();
				break;
			case R.id.button_acronym:
				callAcronym();
				break;
			case R.id.button_both:
				callCombined();
				break;
			default:
				break;
		}
	}

	private void callMeme() {
		_spinner.setVisibility(View.VISIBLE);
		subscribeMeme();
	}

	private void callAcronym() {
		_spinner.setVisibility(View.VISIBLE);
		subscribeAcronym();
	}

	private void callCombined() {
		_spinner.setVisibility(View.VISIBLE);
		subscribeCombined();
	}

	private void subscribeMeme() {
		final Observable<Bitmap> observable = ClassWiring.getMyManager().getRandomMeme();

		_subscriptionMeme = observable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Bitmap>() {
					@Override
					public void onCompleted() {
						// Do nothing
					}

					@Override
					public void onError(final Throwable e) {
						onCommonError(e);
					}

					@Override
					public void onNext(final Bitmap bitmap) {
						_spinner.setVisibility(View.GONE);
						_image.setImageBitmap(bitmap);
					}
				});
	}

	private void subscribeAcronym() {
		final String random = getRandomAcronym();
		final Observable<String> observable = ClassWiring.getMyManager().getAcronymMeaning(random);

		_subscriptionAcronym = observable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<String>() {
					@Override
					public void onCompleted() {
						// Do nothing
					}

					@Override
					public void onError(final Throwable e) {
						onCommonError(e);
					}

					@Override
					public void onNext(final String acronym) {
						_spinner.setVisibility(View.GONE);
						Toast.makeText(MainActivity.this, random + ": " + acronym, Toast.LENGTH_LONG).show();
					}
				});
	}

	private void subscribeCombined() {
		final String random = getRandomAcronym();
		final Observable<CombinedResult> observable = ClassWiring.getMyManager().getCombinedResult(random);

		_subscriptionCombined = observable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<CombinedResult>() {
					@Override
					public void onCompleted() {
						// Do nothing
					}

					@Override
					public void onError(final Throwable e) {
						onCommonError(e);
					}

					@Override
					public void onNext(final CombinedResult result) {
						_spinner.setVisibility(View.GONE);
						_image.setImageBitmap(result.getMeme());
						Toast.makeText(MainActivity.this, random + ": " + result.getAcronym(), Toast.LENGTH_SHORT).show();
					}
				});
	}

	private String getRandomAcronym() {
		final StringBuilder salt = new StringBuilder();
		final int lengthAcronym = MIN_ACRONYM_LENGTH +
				(int) (Math.random() *
						((MAX_ACRONYM_LENGTH - MIN_ACRONYM_LENGTH) + 1));
		while (salt.length() < lengthAcronym) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();

	}

	@Override
	protected void onDestroy() {
		_subscriptionMeme.unsubscribe();
		_subscriptionAcronym.unsubscribe();
		_subscriptionCombined.unsubscribe();
		super.onDestroy();
	}

	private void onCommonError(final Throwable throwable) {
		throwable.printStackTrace();
		_spinner.setVisibility(View.GONE);
		Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

}
