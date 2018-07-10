package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		userRepository.save(new User(userId, UserUtils.hashPassword(password), name, email));
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
			UserUtils.varifyUser(dbUser, UserUtils.hashPassword(password));
		} catch (IllegalArgumentException e) {
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

	@GetMapping("/edit")
	public String editForm(HttpSession httpSession, Model model) {
		Object tempUser = httpSession.getAttribute("loginedUser");
		try {
			if (tempUser == null) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		model.addAttribute("dbUser", (User) tempUser);
		return "/user/user-info-form";
	}

	@PutMapping("/editSubmit")
	public String edit(String userId, String password, String name, String email) {
		User user = userRepository.findByUserId(userId);
		userRepository.save(user.update(UserUtils.hashPassword(password), name, email));
		return String.format("redirect:/users/list");
	}

	@GetMapping("/{id}/profile")
	public String profile(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.findById(id).get());
		return "/user/profile";
	}

}
