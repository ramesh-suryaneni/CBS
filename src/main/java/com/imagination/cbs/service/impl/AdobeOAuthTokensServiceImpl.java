package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.model.AdobeOAuth;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.AdobeOAuthTokensService;
import com.imagination.cbs.service.OAuthService;
import com.imagination.cbs.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdobeOAuthTokensServiceImpl implements AdobeOAuthTokensService {

	public static String OAUTH_REFRESH_TOKEN = "";
	public static String OAUTH_ACCESS_TOKEN = "";

	@Autowired
	private Environment env;

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OAuthService oAuthService;

	boolean isvalidToken = false;

	public String getOauthAccessToken() throws Exception {

		Map<String, Config> keys = getAdobeKeyDetails("ADOBE");
		log.info("keys:::: {}",keys);
		
		if (!isNullOrEmptyMap(keys)) {
			
			if (Utils.isExpired(keys.get("ADOBE_ACCESS_TOKEN_EXP_TIME").getKeyValue())) {
				OAUTH_ACCESS_TOKEN = keys.get("ADOBE_ACCESS_TOKEN").getKeyValue();
			
			} else {
				OAUTH_REFRESH_TOKEN = keys.get("ADOBE_REFRESH_TOKEN").getKeyValue();
				generateNewAccessToken();
				return BEARER + OAUTH_ACCESS_TOKEN;
			}

		} else {
			generateNewAccessToken();
		}

		log.debug("BEARER TOKEN:::: {}", BEARER + OAUTH_ACCESS_TOKEN);

		return BEARER + OAUTH_ACCESS_TOKEN;
	}

	public AdobeOAuth getOauthAccessTokenFromRefreshToken(String oAuthRefreshToken) throws Exception {
		System.out.println("oAuthToken::Refresh:" + oAuthRefreshToken);
		AdobeOAuth response = null;

		String uri = OAUTH_BASE_URL + OAUTH_REFRESH_TOKEN_ENDPOINT;
		HttpHeaders headers = new HttpHeaders();

		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		String requestBody = getRequestBody(oAuthRefreshToken);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(requestBody, headers);

		response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AdobeOAuth.class).getBody();

		log.info("Response refresh token:::{}", response);
		response.setRefresh_token(oAuthRefreshToken);
		OAUTH_ACCESS_TOKEN = response.getAccess_token();

		return response;
	}

	public String getBaseURIForRestAPI(String accessToken) throws Exception {

		if (!SERVICES_BASE_URL.isEmpty())
			return SERVICES_BASE_URL;

		String baseUri = "https://api.na1.echosign.com:443/api/rest/v6/baseUris";

		HttpHeaders head = new HttpHeaders();
		head.add("Authorization", accessToken);

		HttpEntity<String> httpEntity = new HttpEntity<String>(head);

		ResponseEntity<JsonNode> res = restTemplate.exchange(baseUri, HttpMethod.GET, httpEntity, JsonNode.class);

		String baseUris = res.getBody().path("apiAccessPoint").asText() + "api/rest/v6";
		log.info("ApiAccessPoint BaseUris:::{}", baseUris);

		return baseUris;
	}

	public void generateNewAccessToken() throws Exception {
		
		ResponseEntity<AdobeOAuth> results = null;
		AdobeOAuth oAuths = new AdobeOAuth();
		
		String url = OAUTH_BASE_URL + OAUTH_ACCESS_TOKEN_ENDPOINT;
		MultiValueMap<String, String> headers = new HttpHeaders();

		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(getRequestBody(), headers);

		try {
			results = restTemplate.exchange(url, HttpMethod.POST, httpEntity, AdobeOAuth.class);
			oAuths = results.getBody();
			log.info("AdobeOAuth::::{}", results.getStatusCode());
		
		} catch (Exception e) {
			if (results.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
				oAuths = getOauthAccessTokenFromRefreshToken(OAUTH_REFRESH_TOKEN);
			log.info("Exception insdie the:::{}", oAuths);
		}

		OAUTH_ACCESS_TOKEN = oAuths.getAccess_token();
		saveOrUpdateAdobeKeys(oAuths);

	}

	private void saveOrUpdateAdobeKeys(AdobeOAuth oAuth) {
		
		Map<String, Config> keys = getAdobeKeyDetails("ADOBE");

		boolean result = oAuthService.saveOrUpdateAccessToken(keys, oAuth);
		log.info("OAuthServices ::::{}" + result);
	}

	public String getRequestBody(String refreshToken) {
		return "refresh_token=" + refreshToken 
				+ "&client_id=" + env.getProperty(ADOBE_CLIENT_ID)
				+ "&client_secret="+ env.getProperty(ADOBE_CLIENT_SECRET) 
				+ "&grant_type=refresh_token";
	}

	public MultiValueMap<String, String> getRequestBody() {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add(CODE, env.getProperty(ADOBE_CODE));
		body.add(CLIENT_ID, env.getProperty(ADOBE_CLIENT_ID));
		body.add(CLIENT_SECRET, env.getProperty(ADOBE_CLIENT_SECRET));
		body.add(REDIRECT_URI, env.getProperty(ADOBE_REDIRECT_URI));
		body.add(GRANT_TYPE, env.getProperty(ADOBE_GRANT_TYPE));

		return body;
	}

	public Map<String, Config> getAdobeKeyDetails(String keyName) {
		log.info("keyName:::{}", keyName);

		Map<String, Config> map = null;
		List<Config> keysList = null;

		keysList = configRepository.findBykeyNameStartingWith(keyName);

		if (keysList.isEmpty() || keysList == null)
			return null;

		map = keysList.stream().collect(Collectors.toMap(Config::getKeyName, config -> config));

		if (isNullOrEmptyMap(map))
			return null;

		return map;
	}

	public static boolean isNullOrEmptyMap(Map<?, ?> map) {

		return (map == null || map.isEmpty());
	}

}
