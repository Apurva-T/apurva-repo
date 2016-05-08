package com.jpmorgan.exception;

/**
 * 
 * The class represents Customized Service Exception to be thrown from service layer.
 * The exceptions from database layer or from service layer will be wrapped up into 
 * JPMServiceException and will be thrown. 
 * @author Apurva T
 *
 */
public class JPMServiceException extends Exception {

	
	private static final long serialVersionUID = 1L;

	 public JPMServiceException() {}

  	 /**
  	  * Constructor with error message.
  	  * @param message the error message
  	  */
    public JPMServiceException(String message) {
        super(message);
    }
    
    /**
 	  * Constructor with error message and exception .
 	  * @param message the error message
 	  */
   public JPMServiceException(String message,Exception e) {
       super(message,e);
   }
}
