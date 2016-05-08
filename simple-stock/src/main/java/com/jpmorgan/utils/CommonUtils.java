package com.jpmorgan.utils;

import java.math.BigDecimal;

/**
 * Common Utility class to be used within the application.
 * @author Apurva T
 *
 */
public class CommonUtils {

	public static boolean isNotNull(Object obj) {
		return null!= obj;
	}
	
	public static boolean isZeroVal(BigDecimal value) {
		return (isNotNull(value) &&  BigDecimal.ZERO.compareTo(value)==0);
	}
}
