package com.haurylenka.projects.reflection.exceptions;

public class ObjectAnalyzerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectAnalyzerException() {
		super();
	}

	public ObjectAnalyzerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ObjectAnalyzerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectAnalyzerException(String message) {
		super(message);
	}

	public ObjectAnalyzerException(Throwable cause) {
		super(cause);
	}

}
