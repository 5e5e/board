package com.chook9.board.domain;

import java.security.PublicKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Keys {
	@Id
	@GeneratedValue
	private Long id;
	@Lob
	private PublicKey publicKey;

	public Keys() {
	}

	public Keys(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
