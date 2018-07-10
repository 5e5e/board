package com.chook9.board.controller;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chook9.board.domain.Keys;
import com.chook9.board.domain.KeysRepository;
import com.chook9.board.domain.User;
import com.chook9.board.domain.UserRespository;
import com.chook9.board.utils.Security;

@RequestMapping("/users")
@Controller
public class UserController {
	@Autowired
	private KeysRepository keyRespository;
	@Autowired
	private UserRespository userRepository;

	@GetMapping("/sign-up")
	public String signUp() {
		return "/user/sign-up-form";
	}

	@PostMapping("/create")
	public String userCreate(String userId, String password, String name, String email, Model model) {
		try {
			KeyPair keyPair = Security.generateKeyPair();
			keyRespository.save(new Keys(keyPair.getPublic()));
			userRepository.save(new User(Security.encrypt(userId, keyPair.getPublic().getEncoded()),
					Security.encrypt(password, keyPair.getPublic().getEncoded()), name,
					Security.encrypt(email, keyPair.getPublic().getEncoded()), keyPair.getPrivate()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "/user/list";
	}
}
