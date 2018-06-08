package com.chook9.domain;

public class Qna {
	private int no;
	private String writer;
	private String title;
	private String contents;

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return "Qna [writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
