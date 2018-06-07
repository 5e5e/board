package com.chook9.domain;

public class User {
	private String email;
	private String name;
	private String password;
	private String id;

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(String test) {
		this.id = test;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + password + ", id=" + id + "]";
	}

}
