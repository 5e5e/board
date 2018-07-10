package com.chook9.board.rsa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chook9.board.utils.Security;

public class RsaTest {
	private static final Logger log = LoggerFactory.getLogger(RsaTest.class);

	@Test
	public void generateKeyPair() throws NoSuchAlgorithmException {
		KeyPair keyPair = Security.generateKeyPair();
		assertThat(keyPair.getPrivate());
		assertThat(keyPair.getPublic());
	}

	@Test
	public void encryptAndDecrypt() throws NoSuchAlgorithmException {
		String plainText = "{}";
		KeyPair keyPair = Security.generateKeyPair();

		byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
		byte[] encodedPrivateKey = keyPair.getPrivate().getEncoded();

		String cipherText = Security.encrypt(plainText, encodedPublicKey);
		assertEquals(Security.decrypt(cipherText, encodedPrivateKey), plainText);
	}

	@Test
	public void signAndVerify() throws NoSuchAlgorithmException {
		String plainText = "{}";
		KeyPair keyPair = Security.generateKeyPair();

		byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
		byte[] encodedPrivateKey = keyPair.getPrivate().getEncoded();

		String signature = Security.signature(plainText, encodedPrivateKey);
		log.debug(String.format("Signature = %s", signature));
		assertNotNull(signature);

		boolean result = Security.verify(plainText, signature, encodedPublicKey);
		assertTrue(result);
	}

}
