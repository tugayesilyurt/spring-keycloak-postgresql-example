package com.keycloak.example.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 2910806185835529056L;

	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(Exception cause) {
		super(cause);
	}

	public UnauthorizedException(String message, Exception cause) {
		super(message, cause);
	}
}