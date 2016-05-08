package com.jpmorgan.exception;

/**
 * 
 * The class represents Customized DataException to be thrown from  database access layer.
 * The exceptions from database layer will be wrapped up into 
 * JPMDataException and will be thrown.  
 * @author Apurva T
 *
 */
public class JPMDataException extends Exception {

	private static final long serialVersionUID = 1L;

	 
    public JPMDataException() {}

  	 /**
  	  * Constructor with error message.
  	  * @param message the error message
  	  */
    public JPMDataException(String message) {
        super(message);
    }
    
    /**
	  * Constructor with error message and exception .
	  * @param message the error message
	  */
    public JPMDataException(String message,Exception e) {
      super(message,e);
  }
}
