package com.chook9.board.rsa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.chook9.board.utils.Security;

public class RsaTest {

	@Test
	public void generateKeyPair() throws NoSuchAlgorithmException {
		KeyPair keyPair = Security.generateKeyPair();
		assertThat(keyPair.getPrivate());
		assertThat(keyPair.getPrivate());
	}

	@Test
	public void encryptAndDecrypt() throws NoSuchAlgorithmException {
		String plainText = "{}";
		KeyPair keyPair = Security.generateKeyPair();
		
		byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
		byte[] encodedPrivateKey = keyPair.getPrivate().getEncoded();
		
		String cipherText = Security.encrypt(plainText, encodedPublicKey);
		assertThat(plainText).isEqualTo(Security.decrypt(cipherText, encodedPrivateKey));
	}

}
