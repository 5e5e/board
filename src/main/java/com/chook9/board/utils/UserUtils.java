package com.chook9.board.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.chook9.board.domain.User;

public class UserUtils {
	public static final String LOGIN_USER = "loginedUser";

	public static void varifyUser(User user, String hashedPassword) {
		if (!user.varifyPassword(hashedPassword)) {
			throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
		}
	}

	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}

	public static User getSessionUser(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute(LOGIN_USER);
		if (user != null) {
			return user;
		}
		throw new IllegalArgumentException("로그인된 이용자가 아닙니다.");
	}
}
