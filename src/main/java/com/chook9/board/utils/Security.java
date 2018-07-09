package com.chook9.board.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Security {
	public static final int DEFAULT_KEY_SIZE = 2048;
	public static final String KEY_FACTORY_ALGORITHM = "RSA";

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(KEY_FACTORY_ALGORITHM);
		keyGenerator.initialize(DEFAULT_KEY_SIZE, new SecureRandom());
		KeyPair keyPair = keyGenerator.generateKeyPair();
		return keyPair;
	}

}
