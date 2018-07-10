package com.chook9.board.utils;

import com.chook9.board.domain.User;
import com.chook9.board.domain.UserRepository;

public class UserUtils {
	public static User varifyUserId(String userId, UserRepository userRepository) {
		Object tempUser = userRepository.findByUserId(userId);
		if (tempUser == null) {
			throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
		}
		return (User) tempUser;
	}

	public static void varifyUser(User user, String hashedPassword) {
		if (!user.varifyPassword(hashedPassword)) {
			throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
		}
	}
}
