package com.imagination.cbs.service;

import java.io.InputStream;

import org.springframework.core.io.FileSystemResource;

public interface AdobeSignService {

	String getOauthAccessToken();

	String getBaseURIForRestAPI(String accessToken);

	String uploadDocUsingTransientDocument(FileSystemResource file);

	InputStream downloadAgreementsById(String agreementId);
}