package com.example.demo.utils;

import javax.servlet.http.HttpSession;

import com.example.demo.domain.User;

public class HttpSessionUtils {

	public static boolean isLoginUser(HttpSession session) {
		return session.getAttribute("sessionedUser") != null;
	}

	public static User getUserFromSession(HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		return (User) tempUser;
	}

}
