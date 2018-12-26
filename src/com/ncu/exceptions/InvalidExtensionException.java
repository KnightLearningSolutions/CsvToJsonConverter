/**
* The InvalidExtensionException class which generate    
* exception if user given file name extension is not csv
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.exceptions;

public class InvalidExtensionException extends Exception{  
 public InvalidExtensionException(String s){  
  super(s);  
 }  
}  