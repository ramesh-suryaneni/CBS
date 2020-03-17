package com.imagination.cbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CBSAuthenticationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CBSAuthenticationException(String errorMessage){
		super(errorMessage);
	}
}
