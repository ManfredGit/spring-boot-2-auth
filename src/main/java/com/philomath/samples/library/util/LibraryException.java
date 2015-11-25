package com.philomath.samples.library.util;

public class LibraryException extends Exception {

	private static final long serialVersionUID = -457531723818045056L;

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryException(String message) {
		super(message);
	}
}
