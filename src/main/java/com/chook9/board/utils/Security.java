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
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.chook9.board.exception.InvalidSignatureException;

public class Security {
	private static final int DEFAULT_KEY_SIZE = 2048;
	private static final String KEY_FACTORY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
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

	public static String signature(String plainText, byte[] encodedPrivateKey) {
		try {
			Signature privateSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
			privateSignature.initSign(Security.generatePrivateKey(encodedPrivateKey));
			privateSignature.update(plainText.getBytes(CHARSET));
			byte[] signature = privateSignature.sign();
			return Base64.getEncoder().encodeToString(signature);
		} catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verify(String plainText, String signature, byte[] encodedPublicKey) {
		PublicKey publicKey = Security.generatePublicKey(encodedPublicKey);
		return Security.verifySignature(plainText, signature, publicKey);
	}

	private static boolean verifySignature(String plainText, String signature, PublicKey publicKey) {
		Signature sig;
		try {
			sig = Signature.getInstance(SIGNATURE_ALGORITHM);
			sig.initVerify(publicKey);
			sig.update(plainText.getBytes(CHARSET));
			if (!sig.verify(Base64.getDecoder().decode(signature))) {
				throw new InvalidSignatureException("Signature hasn't be invalid");
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}
