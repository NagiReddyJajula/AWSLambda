package com.app.aws.lambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class GameLambda implements RequestHandler<Object,Object>{

	@Override
	public Object handleRequest(Object obj, Context context) {
		
		//context.getLogger().log("Starting...");
		Map<String,String> map=(Map<String,String>)obj;
		String resourcePath=(String)map.get("resourcePath");
		String userId=(String)map.get("userId");
		String gameTitle=(String)map.get("gameTitle");
		//System.out.println("resource path :"+resourcePath+" path parameter :"+pathParam);
		
		return getDetails(userId,gameTitle);
	}
	private static Object getDetails(String userId,String gameTitle){
		
		AWSCredentials basic=new BasicAWSCredentials("AKIAJHZD7AWHLCW4XYLA","gKo9pDnEOWsyu0XzXRQ8SY7tvapt+IwQZ8KIAlE4");
		AmazonDynamoDBClient client=new AmazonDynamoDBClient(basic);
		Region mumbai=Region.getRegion(Regions.AP_SOUTH_1);
		client.setRegion(mumbai);
		
		Map<String,AttributeValue> key=new HashMap<String,AttributeValue>();
		key.put("UserId", new AttributeValue().withS(userId));
		key.put("GameTitle", new AttributeValue(gameTitle));
		GetItemResult result=client.getItem("Game", key);
		return result.toString();
	}
	/*public static void main(String[] args) {
		//GameLambda.getDetails();
	}*/
}
