package com.example.demo.domain;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Question {
	@Id
	private String id;
	private String contents;
	private String title;
	private LocalDateTime time;

	@DBRef
	private User user;

	public Question() {

	}

	public Question(User user, String title, String contents, LocalDateTime time) {
		super();
		this.user = user;
		this.title = title;
		this.contents = contents;
		this.time = time;
	}

	public Question(User user, String title, String contents) {
		super();
		this.user = user;
		this.title = title;
		this.contents = contents;
		this.time = LocalDateTime.now();
	}

	private static final Logger log = LoggerFactory.getLogger(Question.class);

	public String getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public User getUser() {
		return user;
	}

	public void update(User writer, String title, String contents) {
		this.user = writer;
		this.title = title;
		this.contents = contents;
	}

}
