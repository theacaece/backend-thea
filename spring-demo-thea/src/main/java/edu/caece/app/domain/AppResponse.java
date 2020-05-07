package edu.caece.app.domain;

import java.io.Serializable;

public class AppResponse<T>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private String message;
	private T result;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
}
