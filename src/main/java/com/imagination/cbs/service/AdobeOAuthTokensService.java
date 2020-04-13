package com.imagination.cbs.service;

import java.io.InputStream;

public interface AdobeOAuthTokensService {

	String getOauthAccessToken();

	String getBaseURIForRestAPI(String accessToken);

	InputStream downloadAgreementsById(String agreementId);
}