package com.handler.validation;

public class GameValidation {

	public boolean validate(String data){
		
		if(data==null||data.equals("null")||"null".equals(data.trim())||"".equals(data.trim())||"".equals(data))
			return false;
		else
			return true;
	}
}
