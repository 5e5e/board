package com.ball.board.controeller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ball.board.domain.User;
import com.ball.board.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String singUp(User user) {
		userRepository.save(user);
		return "redirect:/users/list";
	}

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("/user/list");
		mav.addObject("users", userRepository.findAll());
		return mav;
	}
}
