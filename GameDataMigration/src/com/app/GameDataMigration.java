package com.app;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.opencsv.CSVReader;

public class GameDataMigration {

	static String home=null;
	static String tableName = null;
	static List<String> headers = null;
	static List<String> dataType = null;
	static List<String> data = null;
	AmazonDynamoDBClient client = null;
	DynamoDB db = null;
	
	public static void main(String[] args) throws Exception{
		home = System.getProperty("user.home");
		System.out.println(home);
		//FileInputStream file=new FileInputStream(new File("Game.CSV"));
		GameDataMigration mig = new GameDataMigration();
		readFile();
		mig.writeData(mig.getTable(tableName.substring(0,tableName.lastIndexOf("."))),mig.db);
	}
	
	private static void readFile()throws Exception{
		headers = new ArrayList<String>();
		dataType = new ArrayList<String>();
		data = new ArrayList<String>();
		
		/*File file=new File("/home/ec2-user");
		File[] files=file.listFiles();
		for(File f:files)
			System.out.println(f.getName());
		Reader read=new FileReader(new File(home+"/Game.csv"));*/
		File file = new File("Game.csv");
		tableName = file.getName();
		Reader read=new FileReader(file);
		//CSVReader reader=new CSVReader(read,',','"',1);
		CSVReader reader=new CSVReader(read,',');
		String[] r=null;
		int count=0;
		while((r=reader.readNext())!=null){
			System.out.println("....... "+r[0]+" "+r[1]);
			if(count==0){
				for(int i=0;i<r.length;i++){
					if(r[i]!=null)
						headers.add(r[i]);
				}
				count++;
			}
			else if(count==1){
				for(int i=0;i<r.length;i++){
					if(r[i]!=null)
						dataType.add(r[i]);
					System.out.println();
				}
				count++;
			}
			else{
				for(int i=0;i<r.length;i++){
					if(r[i]!=null)
						data.add(r[i]);
				}
			}
		}
		System.out.println(headers);
		System.out.println(dataType);
		System.out.println(data);
	}
	
	private TableDescription getTable(String tableName){
		
		AWSCredentials cred = new BasicAWSCredentials("","");
		client = new AmazonDynamoDBClient(cred);
		client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
		//System.out.println(client.describeTable("Game").getTable().getKeySchema());
		db = new DynamoDB(client);
		return client.describeTable(tableName).getTable();
	}
	private boolean writeData(TableDescription desc,DynamoDB db){
		System.out.println(tableName.substring(0,tableName.lastIndexOf(".")));
		
		List<AttributeDefinition> attDef = desc.getAttributeDefinitions();
		List<String> attNames = new ArrayList<String>();
		List<String> attTypes = new ArrayList<String>();
		for(AttributeDefinition def : attDef){
			attNames.add(def.getAttributeName());
			attTypes.add(def.getAttributeType());
		}
		System.out.println(attNames+" : "+attTypes);
		Table table = db.getTable("Game");
		PutItemSpec spec = new PutItemSpec();
		//db.getTable("Game").putItem(new Item().withPrimaryKey("UserId", "1","GameTitle","OOP's"));
		//System.out.println(db.getTable("Game").getItem("UserId", "103","GameTitle","Attack Ships"));
		Iterator<String> it = headers.iterator();
		String header = null;
		for(int i=0;i<headers.size();i++){
			header = headers.get(i);
			Item item = new Item();
			Map<String,String> nameMap = spec.getNameMap();
			Map<String,Object> valueMap = spec.getValueMap();
			if(attNames.contains(header)){
				switch(dataType.get(i)){
				
					case "N":
						nameMap.put(header, header);
						valueMap.put(header, data.get(i));
						break;
				}
				spec.withNameMap(nameMap);
				spec.withValueMap(valueMap);
				table.putItem(spec);
			}
		}
		return false;
	}
}
