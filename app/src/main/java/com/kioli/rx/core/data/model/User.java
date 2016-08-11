package com.kioli.rx.core.data.model;

public class User {

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
}
