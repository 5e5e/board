package com.ball.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 20)
	private String userId;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 16)
	private String name;

	@Column(nullable = false)
	private String email;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
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

	public boolean isNull() {
		return this == null;
	}

	public boolean isUser(String userId, String password) {

		return isUserId(userId) && isPassword(password);
	}

	private boolean isUserId(String userId) {
		return this.userId.equals(userId);
	}

	private boolean isPassword(String password) {
		return this.password.equals(password);
	}

	public boolean isUser(User sessionedUser) {
		return this.equals(sessionedUser);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

	public void edit(String password, String name, String email) {
		this.password = password;
		this.name = name;
		this.email = email;
	}

}
