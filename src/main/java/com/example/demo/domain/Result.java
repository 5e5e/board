package com.example.demo.domain;

public class Result {

	private boolean valid;

	private String errorMessage;

	private int countOfAnswer;

	private Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}

	public Result(boolean valid, String errorMessage, int countOfAnswer) {
		this.valid = valid;
		this.errorMessage = errorMessage;
		this.countOfAnswer = countOfAnswer;
	}

	public static Result fail(String errorMessage) {
		throw new IllegalStateException(errorMessage);
	}

	public static Result ok() {
		return new Result(true, null);
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getCountOfAnswer() {
		return countOfAnswer;
	}

	public static Result ok(int countOfAnswer) {
		return new Result(true, null, countOfAnswer);
	}

}
