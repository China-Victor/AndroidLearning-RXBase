package com.kioli.rx;

import android.app.Application;

import com.kioli.rx.binding.ClassFactoryImpl;
import com.kioli.rx.binding.ClassWiring;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ClassWiring.getInstance().setClassFactory(new ClassFactoryImpl(this));
	}
}
