package com.whatsapp.core.entities.enums;

public enum UserType {
	
	COMMON ("COMMON"), 

	GROUP ("GROUP");
	
	private String type;
	
	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
