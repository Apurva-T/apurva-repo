package com.jpmorgan.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Utility class to work upon date/time related functions.
 * @author Apurva T
 *
 */
public class DateUtils {

	public static LocalTime getCurrentTime() {
		LocalTime currentTime = LocalTime.now();
		return currentTime;
		
	}
	
	public static LocalDateTime getCurrentDateTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		return currentTime;
		
	}
	
	public static LocalTime addMinsToCurrentTime(int mins) {
		LocalTime currentTime = LocalTime.now();
		LocalTime updatedTime = currentTime.plusMinutes(mins);
		return updatedTime;
		
	}

	public static boolean isDurationInRange(LocalDateTime startTime, LocalDateTime endTime, int durationInMins) {
		boolean inRange = false;
		long diffInMins = calculateDiffInMins(startTime,endTime.plusMinutes(1));
		if(diffInMins <=durationInMins) {
			inRange = true;
		}
		return inRange;
	}

	public static long calculateDiffInMins(LocalDateTime startTime, LocalDateTime endTimeExclusive) {
		long diff = Duration.between(startTime, endTimeExclusive).toMinutes();
		return diff;
	}
}
