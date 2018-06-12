package com.ball.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ball.board.domain.Answer;
import com.ball.board.domain.AnswerRepository;
import com.ball.board.utils.HttpSessionUtils;

@Controller
@RequestMapping("/answers")
public class AnswerController {
	@Autowired
	public AnswerRepository answerRepository;

	@PostMapping("/{id}")
	public String answer(@PathVariable Long id, String contents, HttpSession session) {
		Answer answer = new Answer(HttpSessionUtils.sessionedUser(session), contents);
		answerRepository.save(answer);
		// return String.format("redirect:/questions/%d", id);
		return "redirect:/questions/" + id;
	}

}
