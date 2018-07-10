package com.chook9.board.domain;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.chook9.board.utils.Security;

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

	public String getUserId() throws NoSuchAlgorithmException {
		return userId;
	}

	public String getName() throws NoSuchAlgorithmException {
		return name;
	}

	public String getEmail() throws NoSuchAlgorithmException {
		return email;
	}

	public Long getId() {
		return id;
	}

	public boolean varifyPassword(String hashedPassword) {
		return password.equals(hashedPassword);
	}

}
