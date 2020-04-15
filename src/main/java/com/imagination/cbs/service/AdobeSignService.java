package com.imagination.cbs.service;

import java.io.InputStream;

public interface AdobeSignService {

	/*
	 * String getOauthAccessToken();
	 * 
	 * String getBaseURIForRestAPI(String accessToken);
	 */

	String uploadAndCreateAgreement(InputStream inputStream, String fileName);

	InputStream downloadAgreement(String agreementId);

	String sendAgreement(String transientDocId);

	public boolean saveOrUpdateAuthCode(String authcode);
}