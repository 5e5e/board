package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chook9.board.domain.Question;
import com.chook9.board.domain.QuestionRepository;
import com.chook9.board.domain.User;
import com.chook9.board.utils.UserUtils;

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
		model.addAttribute("question", findQuestion(id));
		return "/question/show";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model, HttpSession httpSession) {
		try {
			User sessionUser = UserUtils.getSessionUser(httpSession);
			Question question = findQuestion(id);
			question.isWriter(sessionUser);
			model.addAttribute("question", question);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return String.format("redirect:/questions/%s", id);
		}
		return "/question/edit-form";
	}

	@PutMapping("/{id}/create")
	public String edit(@PathVariable Long id, HttpSession httpSession, String title, String contents) {
		Question question = null;
		try {
			User sessionUser = UserUtils.getSessionUser(httpSession);
			question = findQuestion(id);
			System.out.println("작성자 : " + question.isWriter(sessionUser));
			questionRepository.save(question.update(title, contents));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return String.format("redirect:/questions/%s", id);
		}
		return String.format("redirect:/questions/%s", question.getId());
	}

	private Question findQuestion(Long id) {
		return questionRepository.findById(id).get();
	}
}
