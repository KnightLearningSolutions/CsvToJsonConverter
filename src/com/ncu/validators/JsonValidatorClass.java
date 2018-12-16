/**
* The JsonValidatorClass class to check all validation of json file
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.validations;
import com.ncu.exceptions.*;

import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File; 
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JsonValidatorClass{
	
	String outputFolderPath= System.getProperty("user.dir")+ File.separator+"jsons/";
	String jsonFileName;
	String convertAcceptExtension = "json";
	JsonValidatorClass currentobj;

	/* method to check all validation of json file  */

	public boolean jsonValidatorMethod(String takejsonFileName)
	{   
		Logger logger = Logger.getLogger(JsonValidatorClass.class);
		String log4jConfigFile = System.getProperty("user.dir")
		+ File.separator + "configs/logger/logger.properties";
		BasicConfigurator.configure();
		PropertyConfigurator.configure(log4jConfigFile);
		
		String jsonFileName=takejsonFileName;
		JsonValidatorClass objvalid=new JsonValidatorClass();
		objvalid.jsonFileName=takejsonFileName;

		try{

		// Generate "EmptyNameException" Exception if gives blank space as a file name
			objvalid.empltyFileMethod(jsonFileName);

        // Generate "FileFormatException" Exception if user does not . before extension
			objvalid.fileFormat(jsonFileName);
			
        // Generate "FileFormatException" Exception if user does not . before extension
			objvalid.dotMessingMethod(jsonFileName);

        // Generate "InvalidFileException" Exception if user give other then csv file extension. 
			objvalid.invalidFileMethod(jsonFileName);

        // Generate "FileNameLengthException" Exception if user more then 25 character as a file name .
			objvalid.lengthMethod(jsonFileName);

        // Method to check user given file name is not already into directory.

			Boolean fileExists= new File(outputFolderPath+jsonFileName).exists();
			if(fileExists)
			{   
				logger.error("\n \n Oops.. This File Is Already Exist Into Directory , Please Try With Different Name...!\n");

				// System.out.print("\n Oops.. This File Is Already Exist Into Directory , Please Try With Different Name...!\n");
				return false;
			}
			
		}

        // All Excetion will taken in this section 

		catch(InvalidFileException e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}

		catch(FileFormatException e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}

		catch(EmptyNameException e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}
		catch(Exception e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}

		return true;
	}

	/* Generate "EmptyNameException" Exception if gives blank space as a file name  */

	private void empltyFileMethod(String jsonFileName) throws EmptyNameException {
		if (jsonFileName == null || jsonFileName.trim().isEmpty()) {
			throw new EmptyNameException("Oops.. Sorry Empty Filename Is Not Acceptable .....!");
		}
	}

	private void fileFormat(String jsonFileName) throws FileFormatException{
		Pattern costPattern = Pattern.compile("[.]");
		Matcher costMatcher = costPattern.matcher(jsonFileName);
		Boolean value = costMatcher.find();
		if(!value){
			throw new FileFormatException("Oops.. Extension Is Messing .You Should Also Give .json Extension .....!");
		}
	}

	private void dotMessingMethod(String jsonFileName) throws InvalidFileException {
		String [] haveExtenstion= jsonFileName.split("\\.");
		if (haveExtenstion.length<=1) {
			throw new InvalidFileException("Sorry this is not json file ! This System Accept Only json file");
		}
	}

	private void invalidFileMethod(String jsonFileName) throws InvalidFileException {
		String name = jsonFileName.split("\\.")[0];
		String currentExtension = jsonFileName.split("\\.")[1];
		if(!this.convertAcceptExtension.equals(currentExtension)){
			throw new InvalidFileException("Sorry this is not json file ! This System Accept Only json file");
		}
	}

	private void lengthMethod(String jsonFileName) throws FileNameLengthException {
		String namelength = jsonFileName.split("\\.")[0];
		if(namelength.length()>25){
			throw new FileNameLengthException("you have Given Long File Name .This System Accept Only Less Than 25 Characters To File Name");
		}
	}
}