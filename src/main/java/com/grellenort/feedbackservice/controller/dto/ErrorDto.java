package com.grellenort.feedbackservice.controller.dto;

public class ErrorDto {

	private int code;
	private String message;

	public ErrorDto() {
	}

	public ErrorDto(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

}
