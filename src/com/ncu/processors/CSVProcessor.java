/**
* The CSVProcessor class is main processor to convert csv file 
* into json file
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.processors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 
import java.util.Scanner;
import java.util.Date;
import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CSVProcessor{
	
	String csvPath =System.getProperty("user.dir")+ File.separator+"csvs/";
	String jsonPath=System.getProperty("user.dir")+ File.separator+"jsons/";
	String line;
	String cvsSplitBy = ",";
	String permissionValue="y";

	@SuppressWarnings("unchecked")
	public boolean processCSV(String csvFileName,String jsonFileName){

		Logger logger = Logger.getLogger(CSVProcessor.class);
		String log4jConfigFile = System.getProperty("user.dir")
			+ File.separator + "configs/logger/logger.properties";

		PropertyConfigurator.configure(log4jConfigFile);
		
		try{
			// Ask user permission to convert file
	    logger.info("Do You Want To Convert This Csv data into Json ..? Please Press y/n :-");

	    Scanner scanObj = new Scanner(System.in);
	    String usrOption = scanObj.nextLine();
	    if(permissionValue.equalsIgnoreCase(usrOption)){

	    	// display before file convert 
				logger.info("---------------Time Duration ---------------------");
				logger.info("Time Before Process :- "+new Date( ));
				long start = System.currentTimeMillis( );

				// Read file from directory by using BufferedReader

				BufferedReader br = new BufferedReader(new FileReader(csvPath+csvFileName));
				String headerLine = br.readLine();
				String[] keyNames = headerLine.split(cvsSplitBy);
				JSONArray jsonArry = new JSONArray();

				// write data into file by using BufferedWriter 
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(jsonPath+jsonFileName));
					while ((line = br.readLine()) != null) {
						JSONObject jObj = new JSONObject();
						String[] dataLine = line.split(cvsSplitBy);
						for(int i=0;i<dataLine.length;i++){
							jObj.put(keyNames[i],dataLine[i]);
						}
						jsonArry.add(jObj);
					}
					bw.write(jsonArry.toJSONString());
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("Format of this Csv file is not valid plz try other csv file.....Plz try Again !");
	        return false;
				}

				logger.info("Time After Process :- "+new Date( ));

				long end = System.currentTimeMillis( );
				long diff = end - start;
				logger.info("Duration In MilliSecond -: " + diff+"ms");
				logger.info("-------------------------");
			}else{
				return false;
			}

		}catch (Exception e) {
			logger.info("Internal Problem  Occurred Please Try Again..... !"+e+"\n"+"\n");
			return false;
		}
		logger.info("______! Successfully Converted This File Into Json - (*.*)");
		logger.info("\n \n 'Notice' - You Can Get Your "+jsonFileName+" On Path - "+"'"+jsonPath+"'"+"\n\n");
		return true;
	}
}