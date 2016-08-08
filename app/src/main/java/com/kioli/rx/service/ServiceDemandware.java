package com.kioli.rx.service;

import com.kioli.rx.data.model.User;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Single;

public interface ServiceDemandware {

	@GET("getAllUsers")
	Single<List<User>> getUsers();

	@POST("register")
	Single<Void> registerUser(@Query("email") String email,
	                          @Query("password") String password);

	class Factory {
		public static ServiceDemandware create() {
			final Retrofit retrofit = new Retrofit.Builder()
					.baseUrl("http://kioliserver.herokuapp.com/")
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.build();
			return retrofit.create(ServiceDemandware.class);
		}
	}

}
