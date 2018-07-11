package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chook9.board.domain.Answer;
import com.chook9.board.domain.AnswerRepository;
import com.chook9.board.domain.QuestionRepository;
import com.chook9.board.domain.User;
import com.chook9.board.utils.UserUtils;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("")
	public Answer answer(@PathVariable Long questionId) {
		return answerRepository.findById(questionId).get();
	}

	@PostMapping("")
	public Answer answer(@PathVariable Long questionId, HttpSession httpSession, String contents) {
		try {
			User writer = UserUtils.getSessionUser(httpSession);
			return answerRepository.save(new Answer(writer, questionRepository.findById(questionId).get(), contents));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
