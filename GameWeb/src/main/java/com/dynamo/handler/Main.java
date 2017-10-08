package com.dynamo.handler;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.dynamo.webservice.GameWebServiceResponse;

public class Main {

	public static void main(String[] args) {
		String url="https://www.local.com/GameAPI/rest/game/get";
		String path=url.substring(url.substring(0,url.substring(0,url.lastIndexOf('/')).lastIndexOf('/')).lastIndexOf('/'));
		System.out.println(path);
		
		GameService serv=new GameService();
		Map<String,String> map=new HashMap<String,String>();
		map.put("userId", "103");
		map.put("gameTitle", "Attack Ships");
		Properties prop=new Properties();
		try{
			prop.load(Main.class.getClassLoader().getResourceAsStream("local/application.properties"));
		}catch(Exception e){
		}
		serv.get(new GameWebServiceResponse(), map, prop);
		
	}
}
