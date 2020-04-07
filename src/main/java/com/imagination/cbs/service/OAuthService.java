package com.imagination.cbs.service;

import com.imagination.cbs.dto.AdobeOAuthDto;

public interface OAuthService {

	boolean saveOrUpdateAccessToken(AdobeOAuthDto adobeOAuthDto);

}
