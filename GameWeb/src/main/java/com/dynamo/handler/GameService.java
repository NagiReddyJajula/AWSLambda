package com.dynamo.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.dynamo.mapper.pojo.Game;
import com.dynamo.webservice.GameWebServiceResponse;

public class GameService {

	public GameWebServiceResponse get(GameWebServiceResponse response,Map<String,String> inputMap,Properties prop){
		
		String userId=inputMap.get("userId");
		String gameTitle=inputMap.get("gameTitle");
		AWSCredentials cred=new BasicAWSCredentials(prop.getProperty("ACCESS_KEY"),prop.getProperty("SECRET_KEY"));
		AmazonDynamoDBClient client=new AmazonDynamoDBClient(cred);
		client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));;
		DynamoDBMapper mapper=new DynamoDBMapper(client);
		
		Map<String,String> map=new HashMap<String,String>();
		map.put(":s1", userId);
		map.put(":s2",gameTitle);
		/*DynamoDBScanExpression scan=new DynamoDBScanExpression();
		scan.withConsistentRead(false).withIndexName("GameTitleTopScore-index").withSelect(Select.SPECIFIC_ATTRIBUTES)
		.withProjectionExpression("GameTitle").withProjectionExpression("TopScore");*/
		//mapper.load(clazz, hashKey, rangeKey, config);
		//Stream st=mapper.scan(Game.class, scan).stream();
		//Game game=(Game)st.iterator().next();
		//System.out.println(game);
		/*Game game=new Game();
		game.setUserId(userId);
		game.setGameTitle(gameTitle);*/
		//System.out.println(mapper.load(game));
		//System.out.println(result);
		Game game=mapper.load(Game.class, userId, gameTitle);
		response.setPayload(game);;
		return response;
	}
	public GameWebServiceResponse put(GameWebServiceResponse response,Map<String,String> inputMap,Properties prop){
		return null;
	}
}
