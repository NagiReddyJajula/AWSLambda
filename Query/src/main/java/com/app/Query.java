package com.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class Query {
	
	private static DynamoDBMapper createMapper(){
		AWSCredentials cred=new BasicAWSCredentials("AKIAJHZD7AWHLCW4XYLA","gKo9pDnEOWsyu0XzXRQ8SY7tvapt+IwQZ8KIAlE4");
		AmazonDynamoDBClient client=new AmazonDynamoDBClient(cred);
		client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));;
		DynamoDBMapper mapper=new DynamoDBMapper(client);
		return mapper;
	}

	public static void get(DynamoDBMapper mapper){
		
		Game game=new Game();
		//game.setGameTitle("Attack Ships");
		game.setUserId("102");
		DynamoDBQueryExpression<Game> query=new DynamoDBQueryExpression<Game>();
		query.withHashKeyValues(game);
		
		List<Game> list=mapper.query(Game.class, query);
		Stream<Game> stream=list.stream().filter(n->n.getUserId().equals("102"));
		Iterator<Game> it=stream.iterator();
		while(it.hasNext())
			System.out.println(it.next());
		
		Map<String,AttributeValue> expressionAttributeNames=new HashMap<String,AttributeValue>();
		expressionAttributeNames.put(":s1", new AttributeValue().withS("15"));
		query.withFilterExpression("begins_with(TopScore,:s1)").withExpressionAttributeValues(expressionAttributeNames);
		List<Game> li=mapper.query(Game.class, query);
		Iterator<Game> ite=li.iterator();
		while(ite.hasNext())
			System.out.println(ite.next());
	}
	public static void main(String[] args) {
		DynamoDBMapper mapper=createMapper();
		get(mapper);
		load(mapper);
	}
	public static void load(DynamoDBMapper mapper){
		Map<String,AttributeValue> map=new HashMap<String,AttributeValue>();
		map.put(":s1", new AttributeValue().withS("103"));
		map.put(":s2", new AttributeValue().withS("Attack"));
		DynamoDBQueryExpression<Game> query=new DynamoDBQueryExpression<Game>();
		query.withKeyConditionExpression("UserId=:s1 and begins_with(GameTitle,:s2)").withExpressionAttributeValues(map);
		
		List<Game> list=mapper.query(Game.class, query);
		Stream<Game> st=list.stream().filter(n->n.getLosses()>0);
		Iterator<Game> it=st.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
