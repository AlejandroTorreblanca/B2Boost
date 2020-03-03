package com.partner.app.exceptions;

/**
 * Extends Exception, represents the HTTP error 400 for Bad Request.
 * @author Alejandro Torreblanca
 *
 */
public class BadRequestError extends Exception {

	private static final long serialVersionUID = -5552299437393446269L;

	public BadRequestError() {
	}

	public BadRequestError(String message) {
		super(message);
	}

	

}
