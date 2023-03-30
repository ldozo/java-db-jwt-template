package com.enginaar.jwtapp.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enginaar.jwtapp.auth.exception.EnginaarException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class EnginaarExceptionHandler {

	@ExceptionHandler(EnginaarException.class)
	public ResponseEntity<Map<String, Object>> handleException(EnginaarException le) {
		log.error("EnginaarException", le);
		Map<String, Object> response = new HashMap<>();
		response.put("Error", le.getMessage());
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNPROCESSABLE_ENTITY);

	}


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleException(RuntimeException le) {
		log.error("EnginaarException", le);
		Map<String, Object> response = new HashMap<>();
		response.put("Error", le.getMessage());
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNPROCESSABLE_ENTITY);

	}
}
