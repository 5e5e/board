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

	private boolean valid;

	public User() {

	}

	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.valid = true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + (valid ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (valid != other.valid)
			return false;
		return true;
	}

	public boolean isUser(User loginedUser) {
		if (!this.equals(loginedUser)) {
			throw new IllegalArgumentException("일치하지 않는 사용자입니다.");
		}
		return true;
	}

	public boolean isVaild() {
		if (!this.valid) {
			throw new IllegalArgumentException("탈퇴한 계정입니다.");
		}
		return true;
	}

	public void nonValid() {
		this.valid = false;
	}

	public void deletedUser(String userId, String hashPassword, String name, String email) {
		this.userId = userId;
		this.password = hashPassword;
		this.name = name;
		this.email = email;
		this.valid = false;
	}
}
