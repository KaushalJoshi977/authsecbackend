package com.realnet.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/*
 * @Aurthor: Niladri Sen
 * This is for model class validation (same as @Valid ResultBiding).
 * this class will handle the model class validation and if failed then throw error message to client.
 * */
@Slf4j
@ControllerAdvice
@RestController
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//final List<String> errors = new ArrayList<String>();
		final Map<String, String> errors = new HashMap<String, String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            //errors.add(error.getField() + ": " + error.getDefaultMessage());
        	errors.put(error.getField(), error.getDefaultMessage());
        }
		
		ErrorDetails error = new ErrorDetails(new Date(), "Validation Failed", errors);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
