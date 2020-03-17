package com.imagination.cbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdobeUtils {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private AdobeUtils() {
		// default constructor ignored
	}

	public static boolean isExpired(String exp) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean isExpired = false;

		try {
			isExpired = (new Date().compareTo(simpleFormat.parse(exp)) >= 0);
		} catch (ParseException e) {
			log.info("Exception inside::: {}", e);
		}
		log.info("isExpired::: {}", isExpired);

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

		log.info("FormatDateTime::: {}", formatDateTime);

		return formatDateTime;
	}

	public static int secondsToHours(int seconds) {

		int sec = seconds % 60;
		int hours = seconds / 60;
		int mintue = hours % 60;
		hours = hours / 60;
		log.info("HH:MM:SS={} seconds={}", hours + ":" + mintue + ":" + sec, seconds);

		return hours;
	}

	public enum HttpHeaderField {
		CONTENT_TYPE("Content-Type"), AUTHORIZATION("Authorization"), FILE_NAME("File-Name"), FILE("File"),
		MIME_TYPE("Mime-Type"), USER_EMAIL("X-User-Email"), ACCEPT("ACCEPT");

		private final String fieldName;

		HttpHeaderField(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public String toString() {
			return fieldName;
		}
	}

	public enum DocumentIdentifierName {
		TRANSIENT_DOCUMENT_ID("transientDocumentId"), LIBRARY_DOCUMENT_ID("libraryDocumentId"),
		DOCUMENT_URL("documentURL");

		private final String actualName;

		DocumentIdentifierName(String actualName) {
			this.actualName = actualName;
		}

		@Override
		public String toString() {
			return actualName;
		}
	}
}
