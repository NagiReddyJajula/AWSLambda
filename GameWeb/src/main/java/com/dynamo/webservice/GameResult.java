package com.dynamo.webservice;

import com.dynamo.mapper.pojo.Game;
import com.dynamo.mapper.pojo.GameHours;

public class GameResult {

	private Game game;
	private GameHours gameHours;
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public GameHours getGameHours() {
		return gameHours;
	}
	public void setGameHours(GameHours gameHours) {
		this.gameHours = gameHours;
	}
}
