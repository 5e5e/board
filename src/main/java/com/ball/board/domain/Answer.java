package com.ball.board.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User user;
	private String contents;

	public Answer() {

	}

	public Answer(User user, String contents) {
		super();
		this.user = user;
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	public User getUser() {
		return user;
	}

}
