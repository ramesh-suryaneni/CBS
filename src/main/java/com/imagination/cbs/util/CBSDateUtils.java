package com.imagination.cbs.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pravin.budage
 *
 */
public class CBSDateUtils {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final Logger LOGGER = LoggerFactory.getLogger(CBSDateUtils.class);
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	private CBSDateUtils() {}
	
	public static Timestamp convertDateToTimeStamp(String date) {
		Timestamp timeStamp = null;
		SimpleDateFormat parseDate = new SimpleDateFormat(DATE_FORMAT);
		try {
			Date parsedDate = parseDate.parse(date);
			timeStamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			LOGGER.error("Not able to Parse the Date",e);
		}
		return timeStamp;
	}

	public static String convertTimeStampToString(Timestamp timestamp) {
		if (null != timestamp) {
			Date date = new Date(timestamp.getTime());
			return new SimpleDateFormat(DATE_FORMAT).format(date);
		}
		return null;
	}

	public static java.sql.Date convertStringToDate(String date) {
		Date parsedDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			parsedDate = formatter.parse(date);
		} catch (Exception e) {
			LOGGER.error("Not able to Parse the Date",e);
		}
		return new java.sql.Date(parsedDate.getTime());
	}

	public static String convertDateToString(java.sql.Date date) {
		if (null != date) {
			Date newDate = new Date(date.getTime());
			return new SimpleDateFormat(DATE_FORMAT).format(newDate);
		}
		return null;
	}
	
	public static boolean isExpired(String exp) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean isExpired = false;

		try {
			isExpired = (new Date().compareTo(simpleFormat.parse(exp)) >= 0);
		} catch (ParseException e) {
			LOGGER.info("Exception inside::: {}", e);
		}
		LOGGER.info("isExpired::: {}", isExpired);

		return !isExpired;
	}

	public static Date addHoursToDate(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static String getCurrentDateTime(int seconds) {

		LocalDateTime currentTime = LocalDateTime.now().plusHours(secondsToHours(seconds));

		String formatDateTime = currentTime.format(dateFormat);

		return formatDateTime;
	}

	public static int secondsToHours(int seconds) {

		int sec = seconds % 60;
		int hours = seconds / 60;
		int mintue = hours % 60;
		hours = hours / 60;
		LOGGER.info("HH:MM:SS={} seconds={}", hours + ":" + mintue + ":" + sec, seconds);

		return hours;
	}
}
