package com.dynamo.mapper.pojo;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName="GameHours")
public class GameHours {

	private Integer gameId;
	private String gameSite;
	private List<Map<String,String>> holidayHours;
	private List<Map<String,String>> holidays;
	private List<Map<String,String>> hours;
	
	@DynamoDBAttribute(attributeName="hours")
	@DynamoDBTyped(DynamoDBAttributeType.L)
	public List<Map<String,String>> getHours() {
		return hours;
	}
	public void setHours(List<Map<String,String>> hours) {
		this.hours = hours;
	}
	@DynamoDBAttribute(attributeName="holidays")
	public List<Map<String,String>> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<Map<String,String>> holidays) {
		this.holidays = holidays;
	}
	@DynamoDBTyped(DynamoDBAttributeType.N)
	@DynamoDBHashKey(attributeName="gameId")
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	@DynamoDBAttribute(attributeName="gameSite")
	@DynamoDBIndexHashKey(globalSecondaryIndexName="gameSite-index",attributeName="gameSite")
	public String getGameSite() {
		return gameSite;
	}
	public void setGameSite(String gameSite) {
		this.gameSite = gameSite;
	}

	@DynamoDBAttribute(attributeName="holidayHours")
	public List<Map<String,String>> getHolidayHours() {
		return holidayHours;
	}
	public void setHolidayHours(List<Map<String,String>> hours) {
		this.holidayHours = hours;
	}

	@DynamoDBDocument
	private class HolidayHours{
		@DynamoDBAttribute(attributeName="holidayId")
		private String holidayId;
		@DynamoDBAttribute(attributeName="openTime")
		private String openTime;
		@DynamoDBAttribute(attributeName="closeTime")
		private String closeTime;
	
		public String getHolidayId() {
			return holidayId;
		}
		public void setHolidayId(String holidayId) {
			this.holidayId = holidayId;
		}
		public String getOpenTime() {
			return openTime;
		}
		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}
		public String getCloseTime() {
			return closeTime;
		}
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
	}
	
	@DynamoDBDocument
	private class Holidays{
		@DynamoDBAttribute(attributeName="holidayId")
		private String holidayId;
		@DynamoDBAttribute(attributeName="description")
		private String description;
		@DynamoDBAttribute(attributeName="day")
		private String day;
		@DynamoDBAttribute(attributeName="month")
		private String month;
		@DynamoDBAttribute(attributeName="year")
		private String year;
		
		public String getHolidayId() {
			return holidayId;
		}
		public void setHolidayId(String holidayId) {
			this.holidayId = holidayId;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
	}
	
	@DynamoDBDocument
	private class Hours{
		@DynamoDBAttribute(attributeName="weekDay")
		private String weekDay;
		@DynamoDBAttribute(attributeName="openTime")
		private String openTime;
		@DynamoDBAttribute(attributeName="closeTime")
		private String closeTime;
		
		public String getWeekDay() {
			return weekDay;
		}
		public void setWeekDay(String weekDay) {
			this.weekDay = weekDay;
		}
		public String getOpenTime() {
			return openTime;
		}
		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}
		public String getCloseTime() {
			return closeTime;
		}
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
	}
}
