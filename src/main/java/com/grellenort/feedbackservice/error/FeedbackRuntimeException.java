package com.grellenort.feedbackservice.error;

public class FeedbackRuntimeException extends RuntimeException{

	private ErrorCode errorCode;

	public FeedbackRuntimeException(final String message, final ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public FeedbackRuntimeException(final ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
