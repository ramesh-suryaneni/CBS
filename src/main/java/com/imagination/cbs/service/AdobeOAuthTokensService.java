package com.imagination.cbs.service;

public interface AdobeOAuthTokensService {

	 String getOauthAccessToken() throws Exception;
	
	 String getBaseURIForRestAPI(String accessToken) throws Exception;
}
