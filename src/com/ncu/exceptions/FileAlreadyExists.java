/**
* The FileAlreadyExists class which generate    
* exception if file with user given file name, is not available into directory
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.exceptions;

public class FileAlreadyExists extends Exception{  
 public FileAlreadyExists(String s){  
  super(s);  
 }  
}  