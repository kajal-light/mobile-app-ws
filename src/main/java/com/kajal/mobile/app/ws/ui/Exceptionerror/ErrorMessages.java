package com.kajal.mobile.app.ws.ui.Exceptionerror;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELD("Missing required field.please check doc for required fields"),
	RECORD_ALREADY_EXISTS("RECORD ALREADY EXIST"),
	INTERNAL_SERVER_ERROR("ERROR"),
	NO_RECORD_FOUND("RECORD WITH PROVIDED ID IS NOT FOUND"),
	AUTHENTICATION_FAILED("AUTHENTICATION FAILED"),
	COULD_NOT_UPDATE_RECORD("COULD  NOT UPDATE RECORD"),
	COULD_NOT_DELETE_RECORD("COULD NOT DELETE RECORD"),
	EMAIL_ADDRESS_NOT_VERIFIED("EMAIL ADRESS COULD NOT VERIFIRED");
	
	
	
	private String errorMessage;
	ErrorMessages(String errorMessage){
		
		this.errorMessage=errorMessage;
		
	}
	
	
	public String getErrorMessages() {
		
		return errorMessage;
	}
	
	public void seterrorMessag(String errorMessage) {
		this.errorMessage = errorMessage;
		
		
		
	}
	
}
