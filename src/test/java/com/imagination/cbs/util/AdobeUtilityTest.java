package com.imagination.cbs.util;

import static com.imagination.cbs.util.AdobeConstant.ADOBE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.AdobeSignService;

@RunWith(MockitoJUnitRunner.class)
public class AdobeUtilityTest {

	
	@Mock
	private ConfigRepository configRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private AdobeSignService adobeSignService;
	
	@InjectMocks
	private AdobeUtility adobeUtility;
	
	JsonNode jsonNodeObj;
	@Before
	public void init() throws JsonMappingException, JsonProcessingException {
		String jsonString = "{\"access_token\":\"3AAABLblqZhCuY1hFaBq5aUHda\","
				+ "\"refresh_token\":\"3AAABLblqZhARy2FiOigrGrDghVK\","
				+ "\"token_type\":\"Bearer\","
				+ "\"expires_in\":\"3600\"}";
		 
	    ObjectMapper mapper = new ObjectMapper();
	    jsonNodeObj = mapper.readTree(jsonString);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetNewOauthAccessToken() {

		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList("Empty", true, false));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(jsonNodeObj);
		when(mockedResult.getStatusCode()).thenReturn(HttpStatus.OK);
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer 3AAABLblqZhCuY1hFaBq5aUHda", accessToken);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetOauthAccessTokenFromExpiredAccessToken() {

		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList(ADOBE, true, true));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(jsonNodeObj);
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer 3AAABLblqZhCuY1hFaBq5aUHda", accessToken);
	}

	@Test
	public void shouldGetExistingOauthAccessToken() {

		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList(ADOBE, true, false));
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer 3AAABLblqZhCuY1hFaBq5aUHda_kQrZBWfpVAXiSAo", accessToken);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetOauthAccessTokenFromRefreshToken() {

		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList(ADOBE, false, true));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(jsonNodeObj);
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer 3AAABLblqZhCuY1hFaBq5aUHda", accessToken);
	}
	
	@Test(expected = CBSApplicationException.class)
	public void expectCBSApplicationExceptionFromGetOauthAccessToken() {

		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenThrow(RuntimeException.class);
		adobeUtility.getOauthAccessToken();
	}
	
	@Test(expected = CBSApplicationException.class)
	public void expectCBSApplicationExceptionFromGetNewAccessToken() {
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList("Empty", true, false));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class)))
		.thenThrow(RuntimeException.class);
		adobeUtility.getOauthAccessToken();
	}
	
	@Test
	public void expectNullTokenWhileExceptionInGetOauthAccessTokenFromRefreshToken() {
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList(ADOBE, true, true));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class)))
		.thenThrow(RuntimeException.class);
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer null",accessToken);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void expectNullTokenWhileExceptionInConvertJsonToObj() {
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode jsonNode = Mockito.mock(JsonNode.class);
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getConfigList(ADOBE, true, true));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(jsonNode);
		when(jsonNode.path(Mockito.anyString())).thenThrow(RuntimeException.class);
		String accessToken = adobeUtility.getOauthAccessToken();
		assertEquals("Bearer null",accessToken);
	}
	
	@Test
	public void shouldGetExistingBaseURIForRestAPI() {
		
		when(configRepository.findBykeyNameStartingWith("ADOBE_SERVICES_BASE_URL")).thenReturn(getConfigList("ADOBE_SERVICES_BASE_URL", true, true));
		String baseUrl = adobeUtility.getBaseURIForRestAPI("3AAABLblqZhCuY1hFaBq5aUHda_kQrZBWfpVAXiSAo");
		assertEquals("localhost:8080/baseUrl", baseUrl);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetNewBaseURIForRestAPI() {
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode jsonNode = Mockito.mock(JsonNode.class);
		
		when(configRepository.findBykeyNameStartingWith("ADOBE_SERVICES_BASE_URL")).thenReturn(getConfigList("Empty", true, true));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(jsonNode);
		when(jsonNode.path(Mockito.anyString())).thenReturn(jsonNode);
		when(jsonNode.asText()).thenReturn("localhost:8080/baseUrl/");

		String baseUrl = adobeUtility.getBaseURIForRestAPI("3AAABLblqZhCuY1hFaBq5aUHda_kQrZBWfpVAXiSAo");
		assertEquals("localhost:8080/baseUrl/api/rest/v6", baseUrl);
	}


	private List<Config> getConfigList(String keyName, boolean includeAdobeAcessToken, boolean isExpiredTime) {
		List<Config> keysList = null;
		if (keyName == ADOBE) {
			keysList = new ArrayList<>();
			if(includeAdobeAcessToken) {
				Config c1 = new Config();
				c1.setKeyName("ADOBE_ACCESS_TOKEN");
				c1.setKeyValue(
						"3AAABLblqZhCuY1hFaBq5aUHda_kQrZBWfpVAXiSAo");
				keysList.add(c1);
			}

			Config c2 = new Config();
			c2.setKeyName("ADOBE_ACCESS_TOKEN_EXP_TIME");
			if (isExpiredTime) {
				c2.setKeyValue(Timestamp.valueOf(LocalDateTime.now().minusHours(1)).toString());
			} else {
				c2.setKeyValue(Timestamp.valueOf(LocalDateTime.now().plusHours(1)).toString());
			}
			keysList.add(c2);

			Config c3 = new Config();
			c3.setKeyName("ADOBE_REFRESH_TOKEN");
			c3.setKeyValue("3AAABLblqZhARy2FiOigrGrDghVK-ZyV822n6CKks*");
			keysList.add(c3);

			Config c4 = new Config();
			c4.setKeyName("ADOBE_AUTH_CODE");
			c4.setKeyValue("ADOBE_AUTH_CODE:123");
			keysList.add(c4);
			
			Config c5 = new Config();
			c5.setKeyName("ADOBE_CLIENT_ID");
			c5.setKeyValue("ADOBE_CLIENT_ID:123");
			keysList.add(c5);

			Config c6 = new Config();
			c6.setKeyName("ADOBE_CLIENT_SECRET");
			c6.setKeyValue("ADOBE_CLIENT_SECRET:123");
			keysList.add(c6);

			Config c7 = new Config();
			c7.setKeyName("ADOBE_REDIRECT_URL");
			c7.setKeyValue("localhost:8080/redirect");
			keysList.add(c7);
			
			Config c8 = new Config();
			c8.setKeyName("ADOBE_GRANT_TYPE");
			c8.setKeyValue("ADOBE_GRANT_TYPE");
			keysList.add(c8);
			
		}else if(keyName.equals("ADOBE_SERVICES_BASE_URL")) {
			keysList = new ArrayList<>();
			Config c = new Config();
			c.setKeyName("ADOBE_SERVICES_BASE_URL");
			c.setKeyValue("localhost:8080/baseUrl");
			keysList.add(c);

			
		}
			

		return keysList;
	}

}
