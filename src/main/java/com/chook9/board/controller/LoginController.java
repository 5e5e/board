package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/loginFailed")
	public String failed() {
		return "/user/login_failed";
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("loginedUser");
		return String.format("redirect:/");
	}
}
