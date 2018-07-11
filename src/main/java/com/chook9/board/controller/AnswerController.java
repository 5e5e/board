package com.chook9.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chook9.board.domain.Answer;
import com.chook9.board.domain.AnswerRepository;
import com.chook9.board.domain.QuestionRepository;
import com.chook9.board.domain.User;
import com.chook9.board.utils.UserUtils;

@RequestMapping("/questions/{id}/answers")
@Controller
public class AnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public String answer(@PathVariable Long id, HttpSession httpSession, String contents) {
		try {
			User writer = UserUtils.getSessionUser(httpSession);
			answerRepository.save(new Answer(writer, questionRepository.findById(id).get(), contents));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return String.format("redirect:/questions/%d", id);

	}
}
