package com.chook9.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/loginFailed")
	public String failed() {
		return "/user/login_failed";
	}
}
