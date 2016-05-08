package com.jpmorgan.simple.stock.entity;


/**
 * Enum to represent different stock types.
 * 
 * @author Apurva T
 *
 */
	public enum StockType {
		
		/**
		 * Common Stock type - the dividend yield is calculated with the last dividend.
		 */
		COMMON,
		
		/**
		 * Preferred Stock type- the dividend yield is calculated with the fixed dividend.
		 */
		PREFERRED
	}


