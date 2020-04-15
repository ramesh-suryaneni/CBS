package com.imagination.cbs.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
}
