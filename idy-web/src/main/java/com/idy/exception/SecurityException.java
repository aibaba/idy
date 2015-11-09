package com.idy.exception;

public class SecurityException extends RuntimeException{
	
	private static final long serialVersionUID = -9031053787666539163L;

	public SecurityException() {
		super();
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

}
