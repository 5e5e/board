package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Answer;
import com.example.demo.domain.AnswerRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.QuestionRepository;
import com.example.demo.domain.Result;
import com.example.demo.domain.User;
import com.example.demo.utils.HttpSessionUtils;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public Answer answer(@PathVariable String questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException();
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer newAnswer = new Answer(sessionedUser, question, contents);
		question.addAnswer();
		questionRepository.save(question);
		return answerRepository.save(newAnswer);
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String questionId, @PathVariable String id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인 한 사용자가 아닙니다.");
		}

		Question question = questionRepository.findById(questionId).get();
		Answer answer = answerRepository.findById(id).get();
		User user = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isWriter(user)) {
			return Result.fail("답변 작성자만 삭제 할 수 있습니다.");
		}
		answerRepository.deleteById(id);
		question.deleteAnswer();
		questionRepository.save(question);
		return Result.ok(question.getCountOfAnswer());
	}
}
