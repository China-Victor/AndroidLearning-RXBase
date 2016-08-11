package com.kioli.rx.core.data.dao;

import android.support.annotation.NonNull;

import com.kioli.rx.core.data.model.User;

import java.util.List;

import rx.Single;

/**
 * Dao to get user data
 */
public interface UserDao {

	/**
	 * @return list of all users in the DB
	 */
	Single<List<User>> getAllUsers();

	/**
	 * @param user the user in need of registration
	 *
	 * @return the user just registered
	 */
	Single<Void> registerUser(@NonNull User user);
}
