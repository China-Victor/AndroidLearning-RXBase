package com.kioli.rx.core.data.dao;

import android.support.annotation.NonNull;

import com.kioli.rx.core.binding.ClassWiring;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.core.service.ServiceDemandware;

import java.util.List;

import rx.Single;

/**
 * Production implementation of {@link UserDao} to retrieve user data
 */
public class UserDaoImpl implements UserDao {
	@Override
	public Single<List<User>> getAllUsers() {
		final ServiceDemandware service = ClassWiring.getInstance().getServiceManager().getServiceDemandware();
		return service.getUsers();
	}

	@Override
	public Single<Void> registerUser(@NonNull final User user) {
		final ServiceDemandware service = ClassWiring.getInstance().getServiceManager().getServiceDemandware();
		return service.registerUser(user.getEmail(), user.getPassword());
	}
}
