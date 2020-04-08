package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.ADOBE;
import static com.imagination.cbs.util.AdobeConstant.CLIENT_ID;
import static com.imagination.cbs.util.AdobeConstant.CLIENT_SECRET;
import static com.imagination.cbs.util.AdobeConstant.GRANT_TYPE;
import static com.imagination.cbs.util.AdobeConstant.REFRESH_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.AdobeOAuthDto;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.OAuthService;

@RunWith(MockitoJUnitRunner.class)
public class AdobeOAuthTokensServiceImplTest {

	@Mock
	private ConfigRepository configRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Environment env;

	@Mock
	private OAuthService oAuthService;

	@Mock
	private ResponseEntity<JsonNode> res;
	
	@InjectMocks
	private AdobeOAuthTokensServiceImpl adobeOAuthTokensServiceImpl;

	AdobeOAuthTokensServiceImpl mockedAdobeOAuthTokensServiceImpl;

	@Before
	public void init() {
		mockedAdobeOAuthTokensServiceImpl = Mockito.mock(AdobeOAuthTokensServiceImpl.class);
		
	}
	
	@Test
	public void shouldReturnOauthAccessTokenWhenConfigIsNotNull() {
		
		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails(ADOBE)).thenReturn(getAdobeKeyDetails(ADOBE, false));
		when(mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken()).thenCallRealMethod();
		
		String actual=mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken();
		
		assertEquals("Bearer XYZ", actual);
	}

	@Test
	public void shouldReturnNewAccessTokenWhenCurrentTokenExpires() {
		
		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails(ADOBE)).thenReturn(getAdobeKeyDetails(ADOBE, true));
		AdobeOAuthDto adobeOAuthDto = Mockito.mock(AdobeOAuthDto.class);
		when(mockedAdobeOAuthTokensServiceImpl.getNewAccessToken(Mockito.anyString())).thenReturn(adobeOAuthDto);
		when(adobeOAuthDto.getAccessToken()).thenReturn("XYZ_NEW");
		
		when(mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken()).thenCallRealMethod();
		
		String actual=mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken();
		
		assertEquals("Bearer XYZ_NEW", actual);
		
	}
	
	/*@Test
	public void shouldReturnNewAccessTokenWhenAcessTokenIsNull() {
		AdobeOAuthTokensServiceImpl mockedAdobeOAuthTokensServiceImpl = Mockito.spy(AdobeOAuthTokensServiceImpl.class);
		Map<String, Config> keys = getAdobeKeyDetails(ADOBE, true);
		doReturn(keys).when(mockedAdobeOAuthTokensServiceImpl).getAdobeKeyDetails(ADOBE);//).thenReturn(keys);
		doReturn(true).when(mockedAdobeOAuthTokensServiceImpl).isMapKeyValueEmptyOrNull(keys, ADOBE_ACCESS_TOKEN);//);
		//when(mockedAdobeOAuthTokensServiceImpl.isMapKeyValueEmptyOrNull(keys, ADOBE_ACCESS_TOKEN)).thenReturn(true);
		AdobeOAuth adobeOAuth = Mockito.mock(AdobeOAuth.class);
		doReturn(adobeOAuth).when(mockedAdobeOAuthTokensServiceImpl).getNewAccessToken(Mockito.anyString());//).thenReturn(adobeOAuth);
		when(adobeOAuth.getAccessToken()).thenReturn("XYZ_NEW");
		
		//when(mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken()).thenCallRealMethod();
		
		String actual=mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken();
		
		assertEquals("Bearer XYZ_NEW", actual);
	
	}*/
	
	@Test(expected = CBSApplicationException.class)
	public void shouldReturnOauthAccessTokenWhenConfigIsNull() {

		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails(Mockito.anyString())).thenReturn(null);
		when(mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken()).thenCallRealMethod();
		
		mockedAdobeOAuthTokensServiceImpl.getOauthAccessToken();
	}
	
	/*@Test
	public void shouldReturnOauthAccessTokenFromRefreshToken() {

		ResponseEntity<JsonNode> responseEntity = Mockito.mock(ResponseEntity.class);
		JsonNode response = Mockito.mock(JsonNode.class);
		AdobeOAuth oAuth = new AdobeOAuth();
		oAuth.setAccessToken("AccessToken");
		oAuth.setExpiresIn(1000);
		oAuth.setTokenType("NewAccessToken");
		
		when(mockedAdobeOAuthTokensServiceImpl.getRequestBody(Mockito.anyString())).thenCallRealMethod();
		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails(ADOBE)).thenReturn(getAdobeKeyDetails(ADOBE, false));
		
		/*String uri = OAUTH_BASE_URL + OAUTH_REFRESH_TOKEN_ENDPOINT;
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		MultiValueMap<String, String> requestBody = getRequestBody("RefreshToken");
		HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, headers);

		when(restTemplate.<JsonNode>exchange(uri, HttpMethod.POST, httpEntity, JsonNode.class)).thenReturn(responseEntity);
		//*
		when(restTemplate.<JsonNode>exchange(Mockito.isA(String.class), Mockito.eq(HttpMethod.POST), Mockito.isA(HttpEntity.class), Mockito.eq(JsonNode.class))).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(response);
		when(mockedAdobeOAuthTokensServiceImpl.convertJsonToObj(response)).thenReturn(oAuth);
		when(mockedAdobeOAuthTokensServiceImpl.getOauthAccessTokenFromRefreshToken(Mockito.anyString())).thenCallRealMethod();

		AdobeOAuth oAuthResponse = mockedAdobeOAuthTokensServiceImpl.getOauthAccessTokenFromRefreshToken("RefreshToken");
	}*/
	
	@Test
	public void shouldReturnBaseURIForRestAPI() {
		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails("ADOBE_SERVICES_BASE_URL")).thenReturn(getAdobeKeyDetails(ADOBE, true));
		when(mockedAdobeOAuthTokensServiceImpl.getBaseURIForRestAPI(Mockito.anyString())).thenCallRealMethod();		
		
		String url = mockedAdobeOAuthTokensServiceImpl.getBaseURIForRestAPI("AccessToken");
		assertEquals("localhost:8080/tempUrl", url);
	}
	
	/*@Test
	public void shouldReturnNewBaseURIForRestAPI() {
		when(mockedAdobeOAuthTokensServiceImpl.getAdobeKeyDetails("ADOBE_SERVICES_BASE_URL")).thenReturn(new HashMap<String, Config>());
		when(restTemplate.<JsonNode>exchange(Mockito.isA(String.class), Mockito.eq(HttpMethod.GET), Mockito.isA(HttpEntity.class), Mockito.eq(JsonNode.class))).thenReturn(res);
		
		when(mockedAdobeOAuthTokensServiceImpl.getBaseURIForRestAPI(Mockito.anyString())).thenCallRealMethod();		
		
		String url = mockedAdobeOAuthTokensServiceImpl.getBaseURIForRestAPI("AccessToken");
	}*/
	
	
	private Map<String, Config> getAdobeKeyDetails(String keyName, boolean isExpiredTime) {
		List<Config> keysList = getConfigList(keyName, isExpiredTime);

		if (keysList == null || keysList.isEmpty())
			return null;

		Map<String, Config> map = keysList.stream().filter(key -> key.getKeyValue() != null || !key.getKeyValue().isEmpty())
				.collect(Collectors.toMap(Config::getKeyName, config -> config));
		
		return map;
	}

	private List<Config> getConfigList(String keyName, boolean isExpiredTime) {
		List<Config> keysList = null;
		if (keyName == "ADOBE") {
			keysList = new ArrayList<>();// DB
			Config c1 = new Config();
			c1.setKeyName("ADOBE_ACCESS_TOKEN");
			c1.setKeyValue("XYZ");
			keysList.add(c1);

			Config c2 = new Config();
			c2.setKeyName("ADOBE_ACCESS_TOKEN_EXP_TIME");
			if(isExpiredTime) {
				c2.setKeyValue(Timestamp.valueOf(LocalDateTime.now().minusHours(1)).toString());
			}else {
				c2.setKeyValue(Timestamp.valueOf(LocalDateTime.now().plusHours(1)).toString());
			}
			keysList.add(c2);

			Config c3 = new Config();
			c3.setKeyName("ADOBE_REFRESH_TOKEN");
			c3.setKeyValue("XYZ_REFRESH*");
			keysList.add(c3);
			
			Config c4 = new Config();
			c4.setKeyName("ADOBE_CLIENT_ID");
			c4.setKeyValue("ADOBE_CLIENT_ID:123");
			keysList.add(c4);
			
			Config c5 = new Config();
			c5.setKeyName("ADOBE_CLIENT_SECRET");
			c5.setKeyValue("ADOBE_CLIENT_SECRET:123");
			keysList.add(c5);
			
			Config c6 = new Config();
			c6.setKeyName("ADOBE_SERVICES_BASE_URL");
			c6.setKeyValue("localhost:8080/tempUrl");
			keysList.add(c6);
			
		}

		return keysList;
	}

	
	public MultiValueMap<String, String> getRequestBody(String refreshToken) {

		//Map<String, Config> adobeKeys = getAdobeKeyDetails(ADOBE);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add(REFRESH_TOKEN, refreshToken);
		body.add(CLIENT_ID, "ADOBE_CLIENT_ID:123");
		body.add(CLIENT_SECRET, "ADOBE_CLIENT_SECRET:123");
		body.add(GRANT_TYPE, REFRESH_TOKEN);

		//log.info("Refresh Token Body ::: {} ", body);
		return body;
	}
}
