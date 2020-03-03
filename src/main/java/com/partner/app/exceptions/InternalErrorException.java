package com.partner.app.exceptions;

/**
 * Extends Exception, represents the HTTP error 500 for Internal Server Error.
 * @author Alejandro Torreblanca
 *
 */
public class InternalErrorException extends Exception {

	private static final long serialVersionUID = 4002906416547739420L;

	public InternalErrorException() {
	}
	
	public InternalErrorException(String message) {
		super(message);
	}


}
