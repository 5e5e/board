package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chook9.board.domain.User;
import com.chook9.board.domain.UserRepository;
import com.chook9.board.utils.UserUtils;

@RequestMapping("/users")
@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/sign-up")
	public String signUp() {
		return "/user/sign-up-form";
	}

	@PostMapping("/create")
	public String userCreate(String userId, String password, String name, String email) {
		userRepository.save(new User(userId, DigestUtils.sha256Hex(password), name, email));
		return String.format("redirect:/users/list");
	}

	@GetMapping("/list")
	public String userList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession httpSession) {
		User dbUser = null;
		try {
			dbUser = varifyUser(userId);
			UserUtils.varifyUser(dbUser, DigestUtils.sha256Hex(password));
		} catch (IllegalArgumentException | NullPointerException e) {
			e.printStackTrace();
			return String.format("redirect:/loginFailed");
		}
		httpSession.setAttribute("loginedUser", dbUser);
		return String.format("redirect:/");
	}

	private User varifyUser(String userId) {
		User user = userRepository.findByUserId(userId);
		if (user != null) {
			return user;
		}
		throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
	}

}
