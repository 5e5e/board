package com.example.demo.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Answer {

	@Id
	private String id;
	@DBRef
	private Question question;
	private String contents;
	private LocalDateTime time;
	@DBRef
	private User writer;

	public Answer() {

	}

	public Answer(Question question, String contents) {
		this.question = question;
		this.contents = contents;
		this.time = LocalDateTime.now();
	}

	public Answer(User writer, Question question, String contents) {
		super();
		this.writer = writer;
		this.question = question;
		this.contents = contents;
		this.time = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public Question getQuestion() {
		return question;
	}

	public String getContents() {
		return contents;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public User getWriter() {
		return writer;
	}

	public boolean isWriter(User user) {
		return user.equals(this.writer);
	}

}
