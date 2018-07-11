package com.chook9.board.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	private String title;
	private String contents;

	@OneToMany(mappedBy = "question")
	@OrderBy("id DESC")
	private List<Answer> answers;

	public Question() {

	}

	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public User getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}

	public boolean isWriter(User sessionUser) {
		if (!sessionUser.equals(this.writer)) {
			throw new IllegalArgumentException("글 작성자만 수정을 할 수 있습니다.");
		}
		return true;
	}

	public Question update(String title, String contents) {
		this.title = title;
		this.contents = contents;
		return this;
	}

}
