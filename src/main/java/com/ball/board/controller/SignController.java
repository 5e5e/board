package com.ball.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ball.board.domain.User;
import com.ball.board.domain.UserRepository;
import com.ball.board.utils.HttpSessionUtils;

@Controller
public class SignController {
	@Autowired
	private UserRepository userRepository;


	@GetMapping("/sign-in")
	public String form() {
		return "/sign/form";
	}

	@PostMapping("/sign-in")
	public String signIn(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			return "/user/form";
		}
		if (!user.isUser(userId, password)) {
			return "/sign/signIn_failed";
		}
		session.setAttribute(HttpSessionUtils.SIGNED_USER, user);
		return "redirect:/";
	}

	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.SIGNED_USER);
		return "redirect:/";
	}
}
