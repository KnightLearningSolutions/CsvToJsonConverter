/**
* The MissingExtensionException class is used to generate    
* exception if file extension is other than .csv
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.exceptions;

public class MissingExtensionException extends Exception{  
 public MissingExtensionException(String s){  
  super(s);  
 }  
}  