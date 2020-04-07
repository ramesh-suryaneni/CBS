/**
 * 
 */
package com.imagination.cbs.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imagination.cbs.dto.ErrorResponse;

/**
 * @author Ramesh.Suryaneni
 *
 */

@RestControllerAdvice
public class CBSGlobalExceptionHandler {
	
	@ExceptionHandler(CBSApplicationException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errMsg = error.getDefaultMessage();
			errors.put(fieldName, errMsg);
		});

		return errors;
	}
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CBSApplicationException> handleResourceNotFoundException(RuntimeException runtimeException) {
		CBSApplicationException cbsErrorMessage =  new CBSApplicationException(runtimeException.getMessage());
		return new ResponseEntity<>(cbsErrorMessage, HttpStatus.NOT_FOUND);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CBSValidationException.class)
	public ResponseEntity<CBSApplicationException> handleValidationException(RuntimeException runtimeException) {
		CBSApplicationException cbsErrorMessage =  new CBSApplicationException(runtimeException.getMessage());
		return new ResponseEntity<>(cbsErrorMessage, HttpStatus.BAD_REQUEST);

	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(CBSUnAuthorizedException.class)
	public String handleHTTP403Exception(RuntimeException runtimeException){
		return runtimeException.getMessage();
	}
	

}
