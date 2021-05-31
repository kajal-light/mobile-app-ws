package com.kajal.mobile.app.ws.ui.exception;



import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

	
	
	@ExceptionHandler(value= {UserServiceException.class})
	
	public ResponseEntity<Object> handleServiceException(UserServiceException ex,WebRequest request){
		
		
		ErrorMessage errorMessage=new ErrorMessage(new Date(),ex.getMessage());
		return new ResponseEntity<>(errorMessage,  new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
}
