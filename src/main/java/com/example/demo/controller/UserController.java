package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.utils.UserUtils;

@Controller()
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/signUp")
	public String form() {
		return "user/form";
	}

	@PostMapping("")
	public String create(String userId, String password, String name, String email) {
		userRepository.save(new User(userId, UserUtils.hashedPassword(password), name, email));
		return "redirect:/users/list";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		Object tempUser = userRepository.findByUserId(userId);
		if (tempUser == null) {
			return "user/form";
		}
		User user = (User) tempUser;
		if (!user.getPassword().equals(UserUtils.hashedPassword(password))) {
			return "user/login_failed";
		}
		if (session.getAttribute("sessionedUser") != null) {
			throw new IllegalStateException("이미 로그인된 유저입니다.");
		}
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logOut(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String editForm(@PathVariable String id, Model model) {
		model.addAttribute("user", userRepository.findById(id).get());
		return "user/editForm";
	}

	@PutMapping("{id}")
	public String edit(@PathVariable String id, String password, String name, String email, HttpSession session) {
		Object tempUser = userRepository.findById(id).get();
		if (tempUser == null) {
			throw new IllegalStateException();
		}
		User dbUser = (User) tempUser;
		User loginedUser = (User) session.getAttribute("sessionedUser");
		if (!dbUser.equals(loginedUser)) {
			throw new IllegalStateException();
		}
		dbUser.update(UserUtils.hashedPassword(password), name, email);
		userRepository.save(dbUser);
		return "redirect:/users/list";
	}

	@GetMapping("/{id}/profile")
	public String profiel(@PathVariable String id, Model model) {
		Object tempUser = userRepository.findById(id).get();
		if (tempUser == null) {
			throw new IllegalStateException();
		}
		model.addAttribute("user", (User) tempUser);
		return "user/profile";
	}
}
