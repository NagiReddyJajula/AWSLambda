package com.dynamo.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dynamo.webservice.GameWebServiceResponse;
import com.dynamo.webservice.Status;
import com.handler.validation.GameValidation;

public class GameHandler implements RequestHandler<Object,Object> {
	
	private final String get="/rest/game/get";
	private final String put="/rest/game/put";
	
	@Override
	public Object handleRequest(Object input, Context context) {
	
		Map<String,String> inputMap=(Map<String,String>)input;
		String resourcePath=inputMap.get("resourcePath");
		String userId=inputMap.get("userId");
		String gameTitle=inputMap.get("gameTitle");
		
		String environment=System.getenv("environment").toString();
		String path=resourcePath.substring(resourcePath.substring(0,resourcePath.
				substring(0,resourcePath.lastIndexOf('/')).lastIndexOf('/')).lastIndexOf('/'));
		
		GameValidation gameValidation=new GameValidation();
		GameWebServiceResponse response=new GameWebServiceResponse();
		if(!gameValidation.validate(userId)||!gameValidation.validate(gameTitle)){
			response.setStatusCode(400);
			response.setStatus(Status.VALIDATION_ERROR);
			response.setPayload("Please provide userId and Gametitle");
			return response;
		}
		
		InputStream is=GameHandler.class.getClassLoader().getResourceAsStream(environment+"/application.properties");
		Properties prop=new Properties();
		try{
			prop.load(is);
			is.close();
		}
		catch(IOException e){
			System.out.println("Invalid properties file");
		}
		
		GameService service=new GameService();
		switch(path){
		case(get):
			response=service.get(response,inputMap,prop);
			
		case(put):
			response=service.put(response, inputMap, prop);
		}
		return response;
	}

}
