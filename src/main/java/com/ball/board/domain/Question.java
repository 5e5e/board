package com.ball.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User user;

	@Column(nullable = false, length = 20)
	private String title;

	@Lob
	@Column(nullable = false, length = 20)
	private String contents;

	public Question() {
	}

	public Question(User user, String title, String contents) {
		super();
		this.user = user;
		this.title = title;
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

}
