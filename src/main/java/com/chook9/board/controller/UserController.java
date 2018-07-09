package com.chook9.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {
	@GetMapping("/sign-up")
	public String signUp() {
		return "/user/sign-up-form";
	}

	@PostMapping("/create")
	public String userCreate() {
		return "/user/list";
	}
}
