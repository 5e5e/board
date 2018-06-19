package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Answer;
import com.example.demo.domain.AnswerRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.User;
import com.example.demo.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public String answer(@PathVariable String id, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "user/login";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		System.out.println(sessionedUser);
		System.out.println(question);
		Answer newAnswer = new Answer(sessionedUser, question, contents);
		answerRepository.save(newAnswer);
		return String.format("redirect:/questions/%s", id);
	}

}
