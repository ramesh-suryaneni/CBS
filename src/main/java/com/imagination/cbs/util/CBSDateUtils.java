package com.imagination.cbs.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pravin.budage
 *
 */
public class CBSDateUtils {

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	public static Timestamp convertDateToTimeStamp(String date) {
		Timestamp timeStamp = null;
		SimpleDateFormat parseDate = new SimpleDateFormat(DATE_FORMAT);
		try {
			Date parsedDate = parseDate.parse(date);
			timeStamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStamp;
	}

	public static String conevrtTimeStampIntoStringFormat(Timestamp timestamp) {
		if (null != timestamp) {
			Date date = new Date(timestamp.getTime());
			return new SimpleDateFormat(DATE_FORMAT).format(date);
		}
		return null;
	}

	public static java.sql.Date convertStringToDate(String date) {
		Date parsedDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			parsedDate = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new java.sql.Date(parsedDate.getTime());
	}
}
