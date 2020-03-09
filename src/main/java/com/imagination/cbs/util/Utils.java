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
public class Utils {

	public static boolean isExpired(String exp) throws ParseException {
		boolean isExpired = false;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date expireTime = addHoursToDate(format.parse(exp), 1);

		isExpired = (new Date().compareTo(expireTime) >= 0);
		log.info("isExpired::: {}", isExpired);

		return !isExpired;
	}

	public static Date addHoursToDate(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static String getCurrentDateTime() {

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = now.format(formatter);

		log.info("FormatDateTime::: {}", formatDateTime);

		return formatDateTime;
	}

	public static enum HttpHeaderField {
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

	public static enum DocumentIdentifierName {
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
