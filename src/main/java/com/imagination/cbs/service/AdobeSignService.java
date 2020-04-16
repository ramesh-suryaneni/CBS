package com.imagination.cbs.service;

import java.io.InputStream;

import com.imagination.cbs.domain.BookingRevision;

public interface AdobeSignService {

	String uploadAndCreateAgreement(InputStream inputStream, String fileName);

	InputStream downloadAgreement(String agreementId);

	String sendAgreement(String transientDocId, BookingRevision latestRevision);

	public boolean saveOrUpdateAuthCode(String authcode);
}