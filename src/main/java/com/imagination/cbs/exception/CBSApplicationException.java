/**
 * 
 */
package com.imagination.cbs.exception;

/**
 * @author Ramesh.Suryaneni
 *
 */
public class CBSApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final String errorMessage;

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public CBSApplicationException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}
