package com.imagination.cbs.service;

import java.io.InputStream;

import org.springframework.core.io.FileSystemResource;

public interface AdobeSignService {

	/*
	 * String getOauthAccessToken();
	 * 
	 * String getBaseURIForRestAPI(String accessToken);
	 */

	String uploadAndCreateAgreement(FileSystemResource file);

	InputStream downloadAgreement(String agreementId);
	
	public boolean saveOrUpdateAuthCode(String authcode);
}