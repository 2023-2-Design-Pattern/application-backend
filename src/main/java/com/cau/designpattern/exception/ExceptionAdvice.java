package com.cau.designpattern.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Runtime 중 발생하는 Exception을 Handling 하는 Class (AOP)
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public String exceptionHandler(final HttpMessageNotReadableException e) {
		return "HttpMessageNotReadable Exception";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String exceptionHandler(final MethodArgumentNotValidException e) {
		return "MethodArgumentNotValid Exception";
	}

	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(final RuntimeException e) {
		log.error("Runtime Exception", e);
		return "Runtime Exception";
	}
}
