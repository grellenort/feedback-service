package com.grellenort.feedbackservice.error;

public enum ErrorCode {
	UNKNOWN(0, ErrorType.SERVER_ERROR, "Unknown error."),

	NOT_FOUND(404, ErrorType.NOT_FOUND, "Feedback not found."),

	INPUT_IS_TOO_LONG(1000, ErrorType.INVALID_INPUT, "Input attribute bad format or size.");

	private final int code;
	private final ErrorType errorType;
	private final String description;

	ErrorCode(int code, ErrorType errorType, String description) {
		this.code = code;
		this.errorType = errorType;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getDescription() {
		return description;
	}
}
