package com.chook9.board.domain;

import java.security.PrivateKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	private String userId;
	private String name;
	@Lob
	private String email;
	@Lob
	private PrivateKey privateKey;
	@Lob
	private String password;

	public User(String userId, String password, String name, String email, PrivateKey privateKey) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.privateKey = privateKey;
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

}
