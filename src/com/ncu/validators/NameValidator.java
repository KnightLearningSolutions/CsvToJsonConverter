/**
* Created this class to check all validations on  file name
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.validators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ncu.exceptions.*;

public class NameValidator{

	String fileName;
	String csvPath = System.getProperty("user.dir")+ File.separator+"csvs/";
	String line;
	BufferedReader br;
	String cvsSplitBy = ",";
	NameValidator object;
	String permissionValue="y";
	String configMessages = System.getProperty("user.dir")+ File.separator + "configs/constants/exceptions.properties";
	Properties prop = new Properties();
	InputStream input = null;
	int fileLength;
	String RegexValue;
	String configValidation = System.getProperty("user.dir")+ File.separator + "configs/constants/constants.properties";
    
	/* method to check all validations of csv file name */

	public boolean nameValidator(String fileName, String flag)	{   
		Logger logger = Logger.getLogger(NameValidator.class);
		String log4jConfigFile = System.getProperty("user.dir")
			+ File.separator + "configs/logger/logger.properties";
		PropertyConfigurator.configure(log4jConfigFile);

		NameValidator object=new NameValidator();

		try {
			input = new FileInputStream(configMessages);
			// load a properties file
			prop.load(input);

			object.empltyFileName(fileName);

			object.missingDot(fileName);

			object.fileFormat(fileName);

			object.specialCharacter(fileName);
			if (flag.equals("csv")) {
				object.csvOnly(fileName);
			} else if (flag.equals("json")){
				object.jsonOnly(fileName);
			}
			
			object.fileLength(fileName);
			if (flag.equals("csv")) {
				object.fileNotAvailable(fileName);
			} else if (flag.equals("json")){
				object.fileExixts(fileName);
			}
		} catch(EmptyNameException e){
			logger.error("\n \n"+e+prop.getProperty("emptyNameMessage")+"\n");
			return false;
		}	catch(MissingExtensionException e){
			logger.error("\n \n"+e+prop.getProperty("extensionMessage")+"\n");
			return false;
		}	catch(InvalidExtensionException e){
			logger.error("\n \n"+e+prop.getProperty("notCsvMessage")+"\n");
			return false;
		} catch(FileNameLengthException e){
			logger.error("\n \n"+e+prop.getProperty("longFileNameMessage")+"\n");
			return false;
		}	catch(SpecialCharacterException e){
			logger.error("\n \n"+e+prop.getProperty("specialcharacterMessage")+"\n");
			return false;
		}	catch(FileNotAvailable e){
			NameValidator fileShowObj=new NameValidator();
			fileShowObj.showAllFiles(e);
			return false;
		}	catch(Exception e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}		
		return true;
	}

	/* Generate "EmptyNameException" Exception if user enters blank space as a file name  */
	private void empltyFileName(String fileName) throws EmptyNameException {	
		if (fileName == null || fileName.trim().isEmpty()) {
			throw new EmptyNameException("");
		}
	}

	/* Generate "MissingExtensionException" Exception if user does not enter "." before file extension  */
	private void missingDot(String fileName) throws MissingExtensionException {
		Pattern costPattern = Pattern.compile("[.]");
		Matcher costMatcher = costPattern.matcher(fileName);
		boolean value = costMatcher.find();
		if(!value){
			throw new MissingExtensionException("");
		}
	}

	/* Generate "InvalidExtensionException" Exception if user does not give any extension after file name. */
	private void fileFormat(String fileName) throws InvalidExtensionException {
		String [] haveExtenstion= fileName.split("\\.");
		if (haveExtenstion.length<=1) {
			throw new InvalidExtensionException("");
		}
	}

	/* Generate "SpecialCharacterException" Exception if user enters special character in file name. */
	private void specialCharacter(String fileName) throws SpecialCharacterException{
		fileName = fileName.split("\\.")[0];
		try{
			FileInputStream validSet = new FileInputStream(configValidation);
			Properties propSetvalue = new Properties();
	    propSetvalue.load(validSet);
	    String regexValue = propSetvalue.getProperty("fileRegex");
	    this.RegexValue=regexValue;
    }catch(Exception e) {
    	System.out.println(e);
    }
    Pattern  patternGet = Pattern.compile("["+this.RegexValue+"]");
		Matcher check = patternGet.matcher(fileName);
		boolean finalValue = check.find();
    if (finalValue == true){
      throw new SpecialCharacterException("");
    }
	}

	/* Generate "InvalidExtensionException" Exception if user give other then csv file extension. */
	private void csvOnly(String fileName) throws InvalidExtensionException {
		String name = fileName.split("\\.")[0];
		String currentExtension = fileName.split("\\.")[1];
		if(!currentExtension.equals("csv")){
			throw new InvalidExtensionException("");
		}
	}

	/* Generate "InvalidExtensionException" Exception if user give other then json file extension. */
	private void jsonOnly(String fileName) throws InvalidExtensionException {
		String name = fileName.split("\\.")[0];
		String currentExtension = fileName.split("\\.")[1];
		if(!currentExtension.equals("json")){
			throw new InvalidExtensionException("");
		}
	}

	/* Generate "FileNameLengthException" Exception if user more then 25 character as a file name . */
	private void fileLength(String fileName) throws FileNameLengthException {
		try{
			FileInputStream validationSet = new FileInputStream(configValidation);
			Properties propSet = new Properties();
	    propSet.load(validationSet);
	    String lengthValue = propSet.getProperty("fileNameLength");
	    int getLength=Integer.parseInt(lengthValue); 
	    this.fileLength=getLength;
    }catch(Exception e) {
    	System.out.println(e);
    }
		String namelength = fileName.split("\\.")[0];
		if(namelength.length()>this.fileLength){
			throw new FileNameLengthException("");
		}
	}

	/* Generate "FileNotAvailable" Exception if user given file is not available into directory */
	private void fileNotAvailable(String fileName) throws FileNotAvailable {
		if(!new File(csvPath+fileName).exists()){
			throw new FileNotAvailable("");
		}
	}

	/* Generate "FileAlreadyExists" Exception if user given file already exists into directory */
	private void fileExixts(String fileName) throws FileAlreadyExists {
		if(new File(csvPath+fileName).exists()){
			throw new FileAlreadyExists("");
		}
	}

	/* Method to display all files which available into directory  */
	public void showAllFiles(Exception expObj){ 

		Logger logger = Logger.getLogger(NameValidator.class);
		String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		try{
			input = new FileInputStream(configMessages);
			prop.load(input);
			logger.info("\n \n"+expObj+prop.getProperty("notAvailableMessage")+"\n");
			logger.info("Do You Want To See List Of Available csv file Press - y/n  ");
		}catch(Exception e)	{
			logger.error(e);
		}
		Scanner sobject = new Scanner(System.in);
		String permission = sobject.nextLine();

		if(permissionValue.equalsIgnoreCase(permission)){
			File file = new File(csvPath);
			logger.info("----------- All Available Files In Directory --------- ");

			File[] files = file.listFiles();
			for(File f: files){
				logger.info("-"+f.getName());
			}
			logger.info("------------------------------------------------------ ");
		}
	}
}