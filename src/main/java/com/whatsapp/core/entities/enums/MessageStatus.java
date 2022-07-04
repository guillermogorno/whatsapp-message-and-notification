package com.whatsapp.core.entities.enums;

public enum MessageStatus {
	
	RECEIVED ("RECEIVED"), 
	
	NOT_RECEIVED ("NOT_RECEIVED");

	private String status;
	
	MessageStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
