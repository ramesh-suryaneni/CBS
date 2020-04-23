package com.imagination.cbs.service;

import java.io.InputStream;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.AdobeOAuthDto;

public interface AdobeSignService {

	String uploadAndCreateAgreement(InputStream inputStream, String fileName);

	InputStream downloadAgreement(String agreementId);

	String sendAgreement(String transientDocId, BookingRevision latestRevision);

	void saveOrUpdateAdobeKeys(AdobeOAuthDto adobeOAuthDto);

	void saveOrUpdateAuthCode(String authcode, String apiAccessPoint, String webAccessPoint);
}