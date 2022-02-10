package com.mint.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.mint.challenge.Dtos.ServiceResponse;

@RestControllerAdvice
public class AdviceControllerThrowable {

	@ExceptionHandler(NullPointerException.class)
	public ServiceResponse<?> nullException(NullPointerException e) {
		return new ServiceResponse(HttpStatus.EXPECTATION_FAILED, e);
	}

	@ExceptionHandler(ProductServiceException.class)
	public ServiceResponse<?> userServiceException(ProductServiceException e) {
		return new ServiceResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(BadRequest.class)
	public ServiceResponse<?> badRequestException(BadRequest e) {
		return new ServiceResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ServiceResponse<?> illegalArgumentException(IllegalArgumentException e) {
		return new ServiceResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(Exception.class)
	public ServiceResponse<?> generalException(Exception e) {
		return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}
}
