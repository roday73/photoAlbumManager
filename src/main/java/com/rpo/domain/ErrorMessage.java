package com.rpo.domain;

import org.springframework.http.HttpStatus;

public class ErrorMessage { 
	private HttpStatus status;
    private String message;
    private String requestMethod;
    private String path;
    
	public ErrorMessage(HttpStatus status, String message, String requestMethod, String path) {
		super();
		this.status = status;
		this.message = message;
		this.requestMethod = requestMethod;
		this.path = path;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public String getPath() {
		return path;
	}
    
}
