package com.chook9.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.chook9.domain.Qna;

@Controller
public class QnaController {
	private List<Qna> qnas = new ArrayList<>();

	@GetMapping("/qna/form")
	public String form() {
		return "qna/form";
	}

	@PostMapping("/qna/create")
	public String create(Qna qna) {
		qnas.add(qna);
		qna.setNo(qnas.size());
		Collections.sort(qnas, new Comparator<Qna>() {

			@Override
			public int compare(Qna o1, Qna o2) {
				if (o1.getNo() > o2.getNo()) {
					return -1;
				}
				return 0;
			}
		});
		return "redirect:/";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("qnas", qnas);
		return "index";
	}

	@GetMapping("/qna/{no}")
	public String show(@PathVariable int no, Model model) {
		Qna qna = findQna(no);
		model.addAttribute("qna", qna);
		return "qna/show";
	}

	private Qna findQna(int no) {
		for (Qna index : qnas) {
			if (index.getNo() == no) {
				return index;
			}
		}
		throw new RuntimeException();
	}
}
