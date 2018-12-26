/**
* This class take a csv file as a input and convert into json file  
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.main;
import com.ncu.validators.*;
import com.ncu.processors.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.Scanner;
import java.io.File;


public class JSONConverter{

	static String exitValue = "exit";
	static String permissionValue="y";
  static String log4jConfigFile = System.getProperty("user.dir") + File.separator + "configs/logger/logger.properties";
  static Logger logger; 

	/* main method - process will be start from here and calling  validatorMethod method to check 
	all validations on file name */

	public static void main(String... args) {
		System.out.println("=======================================");
		System.out.println("||        CSV to JSON Converter      ||");
		System.out.println("=======================================");
    logger = Logger.getLogger(JSONConverter.class);
    PropertyConfigurator.configure(log4jConfigFile);
    JSONConverter.getCSVName();
  }

  /* Taking file name from user and Perform trim and lower case operation */
  public static void getCSVName(){

    Scanner sc = new Scanner(System.in);

    logger.info(" !...(*.*) Please Enter Your Csv File Name __:- ");

    String fileName = sc.nextLine().trim().toLowerCase();

    /* Creating object of NameValidator class to check all validation related  to file name */
    NameValidator csvObject=new NameValidator();
    boolean checkValidator=csvObject.nameValidator(fileName, "csv");

    // JSONConverter dObject=new JSONConverter();
    if(checkValidator){
      JSONConverter.getJSONName(fileName);
    }else{
      JSONConverter.getCSVName();
    }
  }

  /* Creating object of NameValidator class to check all validation related 
  to csv file */

  public static void getJSONName(String csvFileName) {   
	  CSVProcessor processObj=new CSVProcessor();

    Scanner sc = new Scanner(System.in);

    logger.info("!...(*.*) What Will Be Your Converted File Name, Please Enter__:- ");
    String jsonFilename = sc.nextLine().trim().toLowerCase();
    NameValidator csvObject=new NameValidator();

    boolean isValid = csvObject.nameValidator(jsonFilename, "json");
    if(isValid) {
      boolean processValidator=processObj.processCSV(csvFileName,jsonFilename);
      if(processValidator) {
        JSONConverter.systemCloseMethod();
      }else{
         JSONConverter.getJSONName(csvFileName);
      }
    } else{
      JSONConverter.getJSONName(csvFileName);
    }
  }
    /* Ask permission to close the program after successfully complete
    process */

    public static void systemCloseMethod()
    {   

      Logger logger = Logger.getLogger(JSONConverter.class);
      String log4jConfigFile = System.getProperty("user.dir")
      + File.separator + "configs/logger/logger.properties";
      BasicConfigurator.configure();
      PropertyConfigurator.configure(log4jConfigFile);
      Scanner sc = new Scanner(System.in);
      logger.info("Do You Want Exit . Please Write - exit ");
      logger.info("Or Write Any Key To Continue Program - ");
      Scanner eobject = new Scanner(System.in);
      String permissionExit = eobject.nextLine();
      if(exitValue.equalsIgnoreCase(permissionExit)){
          System.exit(0);
      }else{
          JSONConverter processAgain=new JSONConverter();
          processAgain.getCSVName();
      }
  } 
}