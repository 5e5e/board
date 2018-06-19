package com.example.demo.controller;

import java.nio.channels.IllegalSelectorException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.AnswerRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.User;
import com.example.demo.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("")
	public String questionForm(HttpSession session, Model model) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "user/login";
		}
		model.addAttribute("loginedUser", (User) tempUser);
		return "question/form";
	}

	@PostMapping("")
	public String question(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String form(@PathVariable String id, Model model) {
		Question question = questionRepository.findById(id).get();
		model.addAttribute("question", question);
		model.addAttribute("answers", answerRepository.findAll());
		return "question/show";
	}

	@GetMapping("/{id}/updateForm")
	public String updateForm(@PathVariable String id, HttpSession session, Model model) {
		Question question = questionRepository.findById(id).get();
		if (question == null) {
			throw new IllegalSelectorException();
		}
		if (!question.getUser().equals(session.getAttribute("sessionedUser"))) {
			throw new IllegalSelectorException();
		}
		model.addAttribute("question", question);
		return "question/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable String id, String title, String contents, HttpSession session) {
		Question question = questionRepository.findById(id).get();
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		question.update(sessionUser, title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%s", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		User user = questionRepository.findById(id).get().getUser();
		if (!user.equals(sessionUser)) {
			throw new IllegalSelectorException();
		}
		questionRepository.deleteById(id);
		return "redirect:/";
	}
}
