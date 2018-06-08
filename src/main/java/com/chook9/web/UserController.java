package com.chook9.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.chook9.domain.Qna;
import com.chook9.domain.User;

@Controller
public class UserController extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3159408382090910170L;

	private List<User> users = new ArrayList<>();
	private int count;

	@GetMapping("")
	public String init() {
		return "index";
	}

	@GetMapping("/user/form")
	public String oepnSignUp() {
		return "user/form";
	}

	@PostMapping("/user/create")
	public String signUp(User user) {
		users.add(user);
		count++;
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/profile/{id}")
	public String profile(@PathVariable String id, Model model) {
		User user = findUser(id, count);
		model.addAttribute("profile", user);
		return "user/profile";
	}

	private User findUser(String id, int count) {
		for (int i = 0; i < count; i++) {
			if (users.get(i).getId().equals(id)) {
				return users.get(i);
			}
		}
		throw new RuntimeException("존재하지 않는 회원입니다.");
	}
}
