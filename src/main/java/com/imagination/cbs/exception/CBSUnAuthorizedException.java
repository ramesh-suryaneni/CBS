/**
 * 
 */
package com.imagination.cbs.exception;

/**
 * @author Ramesh.Suryaneni
 *
 */
public class CBSUnAuthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CBSUnAuthorizedException(String errorMessage) {
		super(errorMessage);
	}
}
