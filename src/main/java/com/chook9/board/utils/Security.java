package com.chook9.board.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Security {
	public static final int DEFAULT_KEY_SIZE = 2048;
	public static final String KEY_FACTORY_ALGORITHM = "RSA";
	private static final String CHARSET = "UTF-8";

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(KEY_FACTORY_ALGORITHM);
		keyGenerator.initialize(DEFAULT_KEY_SIZE, new SecureRandom());
		KeyPair keyPair = keyGenerator.generateKeyPair();
		return keyPair;
	}

	public static String encrypt(String plainText, byte[] encodedPublicKey) throws NoSuchAlgorithmException {
		PublicKey publicKey = Security.generatePublicKey(encodedPublicKey);
		try {
			Cipher cipher = Cipher.getInstance(KEY_FACTORY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bytes = cipher.doFinal(plainText.getBytes(CHARSET));
			return Base64.getEncoder().encodeToString(bytes);
		} catch (NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	private static PublicKey generatePublicKey(byte[] encodedPublicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
			return keyFactory.generatePublic(new X509EncodedKeySpec(encodedPublicKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String decrypt(String cipherText, byte[] encodedPrivateKey) throws NoSuchAlgorithmException {
		PrivateKey privateKey = Security.generatePrivateKey(encodedPrivateKey);
		try {
			byte[] bytes = Base64.getDecoder().decode(cipherText);
			Cipher cipher = Cipher.getInstance(KEY_FACTORY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(bytes), CHARSET);
		} catch (NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	private static PrivateKey generatePrivateKey(byte[] encodedPrivateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
