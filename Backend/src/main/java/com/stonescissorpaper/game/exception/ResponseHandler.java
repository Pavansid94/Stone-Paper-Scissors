package com.stonescissorpaper.game.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class ResponseHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ GameNotFoundException.class })
	public ResponseEntity<Object> interceptBadRequest(final GameNotFoundException e, final WebRequest request) {
		return handleExceptionInternal(e, new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND), new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ GameOverException.class })
	public ResponseEntity<Object> interceptBadRequest(final GameOverException e, final WebRequest request) {
		return handleExceptionInternal(e, new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	@Data
	@AllArgsConstructor
	private class ErrorResponse {

		private String message;
		private HttpStatus httpStatus;

	}

}
