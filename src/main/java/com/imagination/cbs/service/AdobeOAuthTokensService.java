package com.imagination.cbs.service;

public interface AdobeOAuthTokensService {

	 String getOauthAccessToken();
	
	 String getBaseURIForRestAPI(String accessToken);
}
