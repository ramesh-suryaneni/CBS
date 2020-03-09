package com.imagination.cbs.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.model.AdobeOAuth;

public interface OAuthService {

	boolean saveOrUpdateAccessToken(Map<String,Config> keys,AdobeOAuth oAuth);
}
