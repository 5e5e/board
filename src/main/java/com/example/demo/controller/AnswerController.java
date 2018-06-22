package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Answer;
import com.example.demo.domain.AnswerRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.User;
import com.example.demo.utils.HttpSessionUtils;

@RestController
@RequestMapping("/questions/{id}/answers")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public Answer answer(@PathVariable String id, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException();
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		Answer newAnswer = new Answer(sessionedUser, question, contents);
		return answerRepository.save(newAnswer);
	}

}
