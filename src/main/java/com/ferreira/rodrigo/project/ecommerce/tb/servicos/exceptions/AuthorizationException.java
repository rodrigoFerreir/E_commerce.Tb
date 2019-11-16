package com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
