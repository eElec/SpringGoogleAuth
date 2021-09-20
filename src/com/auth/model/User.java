package com.auth.model;

import java.text.SimpleDateFormat;

public class User {
	private String name;
	private String email;
	private String expirationTime;
	
	public User() {}

	public User(String name, String email, Long expirationTime) {
		this.name = name;
		this.email = email;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");
		this.expirationTime = sdf.format(expirationTime * 1000);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
}
