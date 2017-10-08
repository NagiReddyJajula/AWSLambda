package com.dynamo.mapper.pojo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName="Game")
public class Game {

	@DynamoDBHashKey(attributeName="UserId")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String userId;
	
	@DynamoDBRangeKey(attributeName="GameTitle")
	@DynamoDBIndexHashKey(attributeName="GameTitle",globalSecondaryIndexName="GameTitleTopScore-index")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String gameTitle;
	
	@DynamoDBTyped(DynamoDBAttributeType.N)
	@DynamoDBIndexRangeKey(attributeName="TopScore",globalSecondaryIndexName="GameTitleTopScore-index")
	@DynamoDBAttribute(attributeName="TopScore")
	private int topScore;
	
	@DynamoDBTyped(DynamoDBAttributeType.N)
	@DynamoDBAttribute(attributeName="Wins")
	private int wins;
	
	@DynamoDBTyped(DynamoDBAttributeType.N)
	@DynamoDBAttribute(attributeName="Losses")
	private int losses;

	public Game() {
		super();
	}

	public Game(String userId, String gameTitle, int topScore, int wins, int losses) {
		super();
		this.userId = userId;
		this.gameTitle = gameTitle;
		this.topScore = topScore;
		this.wins = wins;
		this.losses = losses;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public int getTopScore() {
		return topScore;
	}

	public void setTopScore(int topScore) {
		this.topScore = topScore;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	@Override
	public String toString() {
		return "Game [userId=" + userId + ", gameTitle=" + gameTitle + ", topScore=" + topScore + ", wins=" + wins
				+ ", losses=" + losses + "]";
	}
	
}
