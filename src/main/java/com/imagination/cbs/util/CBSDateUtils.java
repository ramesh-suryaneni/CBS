package com.imagination.cbs.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pravin.budage
 *
 */
public class CBSDateUtils {

	public static Timestamp convertDateToTimeStamp(String date) {
		Timestamp timeStamp = null;
		SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date parsedDate = parseDate.parse(date);
			timeStamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStamp;
	}
	
	public static String conevrtTimeStampIntoStringFormat(Timestamp timestamp){
       if(null != timestamp){
		Date date = new Date(timestamp.getTime());
        return new SimpleDateFormat("dd/MM/yyy").format(date);
       }
       return null;
	}
}
