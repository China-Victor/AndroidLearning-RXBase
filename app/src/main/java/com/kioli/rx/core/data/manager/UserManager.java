package com.kioli.rx.core.data.manager;

import android.support.annotation.NonNull;

import com.kioli.rx.core.data.model.User;

import java.util.List;

import rx.Single;

/**
 * Manager to get user data
 */
public interface UserManager {

	/**
	 * @return observable for an object of type bitmap, containing the meme image
	 */
	Single<List<User>> getAllUsers();

	/**
	 * @return observable for an object of type Acronym, containing the acronym most known meaning
	 */
	Single<Void> registerUser(@NonNull User user);
}
