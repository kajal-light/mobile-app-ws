package com.kajal.mobile.app.ws.ui.exception;

import java.util.Date;

public class ErrorMessage {


	private Date timeStap;
	private String message;
	
	
	public ErrorMessage() {}

	public ErrorMessage(Date timeStap,String message) {
		
		
		this.timeStap=timeStap;
		this.message=message;
		
		
		
	}

	public Date getTimeStap() {
		return timeStap;
	}

	public void setTimeStap(Date timeStap) {
		this.timeStap = timeStap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
