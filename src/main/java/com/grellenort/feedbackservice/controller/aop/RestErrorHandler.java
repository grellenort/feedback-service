package com.grellenort.feedbackservice.controller.aop;


import com.grellenort.feedbackservice.controller.dto.ErrorDto;
import com.grellenort.feedbackservice.error.ErrorCode;
import com.grellenort.feedbackservice.error.FeedbackRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {FeedbackRuntimeException.class})
	protected ResponseEntity<Object> handleFeedbackRuntimeException(FeedbackRuntimeException ex, WebRequest request) {

		final ErrorCode errorCode = ex.getErrorCode();
		final ErrorDto bodyOfResponse = constructErrorDto(errorCode);

		final HttpStatus httpStatus;

		switch (errorCode.getErrorType()) {
			case INVALID_INPUT:
				httpStatus = HttpStatus.BAD_REQUEST;
				break;
			case NOT_FOUND:
				httpStatus = HttpStatus.NOT_FOUND;
				break;
			default:
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
		}

		return handleExceptionInternal(
				ex,
				bodyOfResponse,
				new HttpHeaders(),
				httpStatus,
				request);
	}

	private ErrorDto constructErrorDto(final ErrorCode errorCode) {
		if (errorCode == null) {
			return constructErrorDto(ErrorCode.UNKNOWN);
		}
		return new ErrorDto(errorCode.getCode(), errorCode.getDescription());
	}

}