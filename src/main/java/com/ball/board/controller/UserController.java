package com.ball.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ball.board.domain.User;
import com.ball.board.domain.UserRepository;
import com.ball.board.utils.HttpSessionUtils;

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

	@GetMapping("/{id}")
	public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
		User user = userRepository.findById(id).get();
		if (user.isNull()) {
			throw new IllegalStateException("꺼죠!");
		}

		if (!user.isUser(HttpSessionUtils.sessionedUser(session))) {
			throw new IllegalStateException("꺼죠!");
		}
		model.addAttribute("signUpedUser", user);
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String edit(@PathVariable Long id, String userId, String password, String name, String email,
			HttpSession session) {
		Object tempUser = session.getAttribute(HttpSessionUtils.SIGNED_USER);
		if (tempUser == null) {
			throw new IllegalStateException("메롱");
		}
		User sessionedUser = (User) tempUser;
		if (!userRepository.findByUserId(userId).isUser(sessionedUser)) {
			throw new IllegalStateException("메롱");
		}
		sessionedUser.edit(password, name, email);
		userRepository.save(sessionedUser);
		return "redirect:/users/list";
	}
}
