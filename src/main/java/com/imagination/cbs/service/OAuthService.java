package com.imagination.cbs.service;

import com.imagination.cbs.model.AdobeOAuth;

public interface OAuthService {

	boolean saveOrUpdateAccessToken(AdobeOAuth oAuth);

}
