package com.chook9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("/")
	public String init() {
		return "index";
	}

	@GetMapping("user/form")
	public String signUp() {
		return "/user/form";
	}
}
