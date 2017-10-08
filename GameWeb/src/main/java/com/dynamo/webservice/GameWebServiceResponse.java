package com.dynamo.webservice;

public class GameWebServiceResponse {

	private int statusCode;
	private Status status;
	private Object payload;
	public GameWebServiceResponse() {
		super();
	}
	public GameWebServiceResponse(int statusCode, Status status, Object payload) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.payload = payload;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
}
