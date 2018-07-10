package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chook9.board.domain.Question;
import com.chook9.board.domain.QuestionRepository;
import com.chook9.board.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/create")
	public String questionForm() {
		return String.format("/question/form");
	}

	@PostMapping("/create")
	public String create(String title, String contents, HttpSession httpSession) {
		Object tempUser = httpSession.getAttribute("loginedUser");
		try {
			if (tempUser == null) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		Question question = new Question((User) tempUser, title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%s", question.getId());
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/question/show";
	}
}
