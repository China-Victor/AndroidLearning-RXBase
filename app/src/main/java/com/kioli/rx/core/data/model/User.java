package com.kioli.rx.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;

public class User implements Parcelable {

	private String email;
	private String password;
	private String token;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.email);
		dest.writeString(this.password);
		dest.writeString(this.token);
	}

	public User(){}

	private User(Parcel in) {
		this.email = in.readString();
		this.password = in.readString();
		this.token = in.readString();
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Contract("_ -> !null")
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Contract(value = "_ -> !null", pure = true)
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
