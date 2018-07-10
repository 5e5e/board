package com.chook9.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chook9.board.domain.QuestionRepository;

@Controller
public class IndexController {
	@Autowired
	private QuestionRepository QuestionRepository;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("questions", QuestionRepository.findAll());
		return "/index";
	}
}
