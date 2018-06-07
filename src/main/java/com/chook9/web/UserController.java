package com.chook9.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chook9.domain.User;

@Controller
public class UserController {

	private List<User> users = new ArrayList<>();

	@GetMapping("/")
	public String init() {
		return "index";
	}

	@GetMapping("user/form")
	public String oepnSignUp() {
		return "/user/form";
	}

	@PostMapping("/user/create")
	public String signUp(User user) {
		users.add(user);
		for (User index : users) {
			System.out.println("user :" + index);

		}
		System.out.println(users.size());
		return "redirect:/";
	}

	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("fakeUsers", users);
		return "user/list";
	}
}
