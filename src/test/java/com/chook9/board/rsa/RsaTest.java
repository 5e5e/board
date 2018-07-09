package com.chook9.board.rsa;

import static org.assertj.core.api.Assertions.assertThat;

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

}
