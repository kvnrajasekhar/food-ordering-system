package com.feast.server_main.response;

public class StandardResponse<T> {
	
	private int code;
	private String message;
	private T details;
	public StandardResponse(int code, String message, T details) {
		super();
		this.code = code;
		this.message = message;
		this.details = details;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getDetails() {
		return details;
	}
	public void setDetails(T details) {
		this.details = details;
	}
	
}
