package com.whatsapp.core.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.whatsapp.core.exceptions.WhatsAppException;

@ControllerAdvice
public class TrafficMessageHandler {

	
	@ExceptionHandler(WhatsAppException.class)
	public ResponseEntity<ApiError> HandleWhatsAppException (WhatsAppException ex) {
		
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	
}
