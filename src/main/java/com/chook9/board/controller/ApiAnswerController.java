package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("")
	public Answer answer(@PathVariable Long questionId, HttpSession httpSession, String contents) {
		try {
			return answerRepository.save(new Answer(UserUtils.getSessionUser(httpSession),
					questionRepository.findById(questionId).get(), contents));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PutMapping("/{id}")
	public Answer updateform(@PathVariable Long questionId, @PathVariable Long id, HttpSession httpSession,
			String contents) {
		try {
			User loginedUser = UserUtils.getSessionUser(httpSession);
			Answer answer = answerRepository.findById(id).get();
			answer.isWriter(loginedUser);
			answer.edit(contents);
			return answerRepository.save(answer);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public Answer delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession httpSession) {
		try {
			User loginedUser = UserUtils.getSessionUser(httpSession);
			Answer answer = answerRepository.findById(id).get();
			answer.isWriter(loginedUser);
			answerRepository.delete(answer);
			return answer;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
