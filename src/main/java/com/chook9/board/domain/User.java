package com.chook9.board.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userId;
	private String name;
	private String email;
	private String password;

	public User() {

	}

	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public boolean varifyPassword(String hashedPassword) {
		return password.equals(hashedPassword);
	}

	public User update(String hashPassword, String name, String email) {
		this.password = hashPassword;
		this.name = name;
		this.email = email;
		return this;
	}

}
