package com.chook9.board.utils;

import org.apache.commons.codec.digest.DigestUtils;

import com.chook9.board.domain.User;

public class UserUtils {
	public static void varifyUser(User user, String hashedPassword) {
		if (!user.varifyPassword(hashedPassword)) {
			throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
		}
	}

	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}
}
