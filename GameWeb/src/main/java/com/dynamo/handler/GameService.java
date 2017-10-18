package com.dynamo.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amazon.dax.client.dynamodbv2.AmazonDaxClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dynamo.mapper.pojo.Game;
import com.dynamo.mapper.pojo.GameHours;
import com.dynamo.webservice.GameResult;
import com.dynamo.webservice.GameWebServiceResponse;
import com.dynamo.webservice.Status;

public class GameService {

	public GameWebServiceResponse get(GameWebServiceResponse response,Map<String,String> inputMap,Properties prop){
		
		String userId=inputMap.get("userId");
		String gameTitle=URLDecoder.decode(inputMap.get("gameTitle"));
		System.out.println(userId+":"+gameTitle);
		
/*		AWSCredentials cred=new BasicAWSCredentials(prop.getProperty("ACCESS_KEY"),prop.getProperty("SECRET_KEY"));
		AmazonDynamoDBClient client=new AmazonDynamoDBClient(cred);
		client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));;
		DynamoDBMapper mapper=new DynamoDBMapper(client);*/
		
		AmazonDaxClientBuilder daxClientBuilder = AmazonDaxClientBuilder.standard();
        daxClientBuilder.withRegion("ap-south-1").withEndpointConfiguration("gamescluster.srbcrj.clustercfg.dax.aps1.cache.amazonaws.com:8111");
        AmazonDynamoDB client = daxClientBuilder.build();
        DynamoDBMapper mapper=new DynamoDBMapper(client);
        
		/*
		 * reading game
		 */
		Map<String,AttributeValue> map=new HashMap<String,AttributeValue>();
		//map.put(":s1", new AttributeValue().withS(userId));
		map.put(":s2",new AttributeValue().withS(gameTitle));
		
		DynamoDBQueryExpression<Game> query=new DynamoDBQueryExpression<Game>();
		query.withConsistentRead(false).withIndexName("GameTitle-index")//.withSelect(Select.SPECIFIC_ATTRIBUTES)
		//.withProjectionExpression("GameTitle").withProjectionExpression("TopScore")
		.withKeyConditionExpression("GameTitle = :s2").withExpressionAttributeValues(map);
		List<Game> game=mapper.query(Game.class,query);
		System.out.println(game.get(0));
		
		/*
		 * reading gameHours
		 */
		Map<String,AttributeValue> gameMap=new HashMap<String,AttributeValue>();
		gameMap.put(":s1", new AttributeValue().withS(game.get(0).getGameTitle()));
		
		DynamoDBQueryExpression<GameHours> gameQuery=new DynamoDBQueryExpression<GameHours>();
		query.withConsistentRead(false).withIndexName("gameSite-index").withKeyConditionExpression("gameSite = :s1")
		.withExpressionAttributeValues(gameMap);
		List<GameHours> gameHours=mapper.query(GameHours.class, gameQuery);
		System.out.println(gameHours.get(0));
/*		DynamoDBScanExpression gameQuery=new DynamoDBScanExpression();
		query.withConsistentRead(false);
		List<GameHours> gameHours=mapper.scan(GameHours.class, gameQuery);
		System.out.println(gameHours.get(0));*/
		GameResult gameResult=new GameResult();
		gameResult.setGameHours(gameHours.get(0));
		response.setStatusCode(200);
		response.setStatus(Status.SUCCESSFUL);
		response.setPayload(gameResult);;
		return response;
	}
	public GameWebServiceResponse put(GameWebServiceResponse response,Map<String,String> inputMap,Properties prop){
		return null;
	}
}
