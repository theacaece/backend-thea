package edu.caece.app.controller;

public class DetectionResult {
	
	private boolean authorized = false;
	
	private String name;
	
	private String message;
	
	public DetectionResult() {
	}
	
	public DetectionResult(boolean authorized, String name, String message) {
		super();
		this.authorized = authorized;
		this.name = name;
		this.message = message;
	}
	public boolean isAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
