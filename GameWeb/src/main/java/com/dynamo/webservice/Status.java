package com.dynamo.webservice;

public enum Status {

	VALIDATION_ERROR("Validation_Error"),
	AUTHENTICATION_ERROR("Authentication_Error"),
	BUSINESS_ERROR("Business_Error"),
	DATABASE_ERROR("Database_Error"),
	UNKNOWN_ERROR("Unknown_Error");
	
	private final String msg;
	private Status(final String data){
		msg=data;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
