package com.ball.board.utils;

import javax.servlet.http.HttpSession;

import com.ball.board.domain.User;

public class HttpSessionUtils {
	public final static String SIGNED_USER = "signedUser";

	public static User sessionedUser(HttpSession session) {
		return (User) session.getAttribute(SIGNED_USER);
	}
}
