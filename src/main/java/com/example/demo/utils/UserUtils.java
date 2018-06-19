package com.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class UserUtils {
	public static String hashedPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}
}
