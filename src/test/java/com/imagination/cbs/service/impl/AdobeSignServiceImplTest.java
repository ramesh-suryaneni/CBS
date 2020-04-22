package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.ADOBE_AUTH_CODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.AdobeOAuthDto;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.util.AdobeUtility;

@RunWith(MockitoJUnitRunner.class)
public class AdobeSignServiceImplTest {
	@Mock
	private ConfigRepository configRepository;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private Environment environment;
	@Mock
	private AdobeUtility adobeUtility;
	@Mock
	private ResponseEntity<JsonNode> res;
	@InjectMocks
	private AdobeSignServiceImpl adobeSignServiceImpl;
	
	@Before
	public void init() throws Exception {
		
		when(adobeUtility.getOauthAccessToken()).thenReturn("AcessToken");
		when(adobeUtility.getBaseURIForRestAPI(Mockito.anyString())).thenReturn("baseurl");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnInputStreamOnDownloadAgreement(){

		ResponseEntity<byte[]> mockedResult = Mockito.mock(ResponseEntity.class);
 
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(byte[].class))).thenReturn(mockedResult);
		byte b[] = {20,10,30,5};
		when(mockedResult.getBody()).thenReturn(b);
		InputStream actualInputStream = adobeSignServiceImpl.downloadAgreement("A-101");
		assertNotNull(actualInputStream);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void expectedResourceNotFoundExceptionOnDownloadAgreement() throws Exception {
		when(adobeUtility.getOauthAccessToken()).thenThrow(RuntimeException.class);
		adobeSignServiceImpl.downloadAgreement("A-101");
	}
	
	@Test
	public void shouldUpdateAdobeKeys() throws Exception {
		
		AdobeOAuthDto adobeOAuth = new AdobeOAuthDto();
		adobeOAuth.setAccessToken("AccessToken-1234");
		adobeOAuth.setRefreshToken("RefreshToken-1234");
		adobeOAuth.setTokenType("Access");
		adobeOAuth.setExpiresIn(Integer.MAX_VALUE);
		
		beforesaveOrUpdateAdobeKeysTestCases(false);

		AdobeSignServiceImpl adobeSignServiceImplSpy = Mockito.spy(adobeSignServiceImpl);
		adobeSignServiceImplSpy.saveOrUpdateAdobeKeys(adobeOAuth);
		
		verify(adobeSignServiceImplSpy, times(1)).saveOrUpdateAdobeKeys(adobeOAuth);
	}
	
	@Test
	public void shouldSaveAdobeKeys() throws Exception {
		
		AdobeOAuthDto adobeOAuth = new AdobeOAuthDto();
		adobeOAuth.setAccessToken("AccessToken-1234");
		adobeOAuth.setRefreshToken("RefreshToken-1234");
		adobeOAuth.setTokenType("Access");
		adobeOAuth.setExpiresIn(Integer.MAX_VALUE);

		beforesaveOrUpdateAdobeKeysTestCases(true);

		AdobeSignServiceImpl adobeSignServiceImplSpy = Mockito.spy(adobeSignServiceImpl);
		adobeSignServiceImplSpy.saveOrUpdateAdobeKeys(adobeOAuth);
		
		verify(adobeSignServiceImplSpy, times(1)).saveOrUpdateAdobeKeys(adobeOAuth);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void expectResourceNotFoundOnsaveOrUpdateAdobeKeys() throws Exception {
		AdobeOAuthDto adobeOAuth = new AdobeOAuthDto();
		adobeOAuth.setAccessToken("AccessToken-1234");
		adobeOAuth.setRefreshToken("RefreshToken-1234");
		adobeOAuth.setTokenType("Access");
		adobeOAuth.setExpiresIn(Integer.MAX_VALUE);
		
		when(configRepository.findByKeyName(Mockito.anyString())).thenThrow(RuntimeException.class);
		
		AdobeSignServiceImpl adobeSignServiceImplSpy = Mockito.spy(adobeSignServiceImpl);
		adobeSignServiceImplSpy.saveOrUpdateAdobeKeys(adobeOAuth);
		
		verify(adobeSignServiceImplSpy, times(1)).saveOrUpdateAdobeKeys(adobeOAuth);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldUploadAndCreateAgreement(){
		String testStr = "This is a string to create inputstream";
		InputStream inputStream = new ByteArrayInputStream(testStr.getBytes());
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode mockedJsonNode = Mockito.mock(JsonNode.class);
		
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(mockedJsonNode);
		when(mockedJsonNode.path(Mockito.anyString())).thenReturn(mockedJsonNode);
		when(mockedJsonNode.asText()).thenReturn("TransientId:111222");
		
		String transientId = adobeSignServiceImpl.uploadAndCreateAgreement(inputStream, "Test.pdf");
		
		assertEquals("TransientId:111222", transientId);
	}
	
	@Test
	public void shouldReturnNullWhileExceptionInUploadAndCreateAgreement() {
		String testStr = "This is a string to create inputstream";
		InputStream inputStream = new ByteArrayInputStream( testStr.getBytes() );
		
		when(adobeUtility.getOauthAccessToken()).thenThrow(RuntimeException.class);
		
		String transientId = adobeSignServiceImpl.uploadAndCreateAgreement(inputStream, "Test.pdf");
		
		assertNull(transientId);
	}
		
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendAgreement() throws Exception {
		String transientDocId = "TransientDocId";
		BookingRevision bookingRevision = getBookingRevision();
		
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode mockedJsonNode = Mockito.mock(JsonNode.class);

		String envArr[] = {"dev", "local", "qual"};
		when(environment.getActiveProfiles()).thenReturn(envArr);
		
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(mockedJsonNode);
		when(mockedJsonNode.path(Mockito.anyString())).thenReturn(mockedJsonNode);
		when(mockedJsonNode.asText()).thenReturn("Id:12345");
		
		String id = adobeSignServiceImpl.sendAgreement(transientDocId, bookingRevision);
		
		assertEquals("Id:12345", id);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendAgreementForLocalEnv() throws Exception {
		String transientDocId = "TransientDocId";
		BookingRevision bookingRevision = getBookingRevision();
		
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode mockedJsonNode = Mockito.mock(JsonNode.class);

		String envArr[] = {"local"};
		when(environment.getActiveProfiles()).thenReturn(envArr);
		
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(mockedJsonNode);
		when(mockedJsonNode.path(Mockito.anyString())).thenReturn(mockedJsonNode);
		when(mockedJsonNode.asText()).thenReturn("Id:12345");
		
		String id = adobeSignServiceImpl.sendAgreement(transientDocId, bookingRevision);
		
		assertEquals("Id:12345", id);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendAgreementForQualEnv() throws Exception {
		String transientDocId = "TransientDocId";
		BookingRevision bookingRevision = getBookingRevision();
		
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		JsonNode mockedJsonNode = Mockito.mock(JsonNode.class);

		String envArr[] = {"qual"};
		when(environment.getActiveProfiles()).thenReturn(envArr);
		
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenReturn(mockedJsonNode);
		when(mockedJsonNode.path(Mockito.anyString())).thenReturn(mockedJsonNode);
		when(mockedJsonNode.asText()).thenReturn("Id:12345");
		
		String id = adobeSignServiceImpl.sendAgreement(transientDocId, bookingRevision);
		
		assertEquals("Id:12345", id);
	}

	
	@SuppressWarnings("unchecked")
	@Test(expected = CBSApplicationException.class)
	public void expectCBSApplicationExceptionOnSendAgreement() throws Exception {
		
		String transientDocId = "TransientDocId";
		BookingRevision bookingRevision = getBookingRevision();
		
		ResponseEntity<JsonNode> mockedResult = Mockito.mock(ResponseEntity.class);
		String envArr[] = {"test"};
		when(environment.getActiveProfiles()).thenReturn(envArr);
		
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(), eq(JsonNode.class))).thenReturn(mockedResult);
		when(mockedResult.getBody()).thenThrow(RuntimeException.class);
		
		
		adobeSignServiceImpl.sendAgreement(transientDocId, bookingRevision);
	}
	
	
	@Test
	public void shouldSaveAuthCode() {

		AdobeSignServiceImpl adobeSignServiceImplSpy = Mockito.spy(adobeSignServiceImpl);
		Config config = new Config();
		Optional<Config> configOptional = Optional.empty();
		
		when(configRepository.findByKeyName(ADOBE_AUTH_CODE)).thenReturn(configOptional);
		when(configRepository.save(Mockito.any())).thenReturn(config);
		
		adobeSignServiceImplSpy.saveOrUpdateAuthCode("AuthCode");
		verify(adobeSignServiceImplSpy, times(1)).saveOrUpdateAuthCode("AuthCode");
	}

	@Test
	public void shouldUpdateAuthCode() {

		AdobeSignServiceImpl adobeSignServiceImplSpy = Mockito.spy(adobeSignServiceImpl);
		Config config = new Config();
		Optional<Config> configOptional = Optional.of(config);
		
		when(configRepository.findByKeyName(ADOBE_AUTH_CODE)).thenReturn(configOptional);
		when(configRepository.save(Mockito.any())).thenReturn(config);
		
		adobeSignServiceImplSpy.saveOrUpdateAuthCode("AuthCode");
		verify(adobeSignServiceImplSpy, times(1)).saveOrUpdateAuthCode("AuthCode");
	}

	private void beforesaveOrUpdateAdobeKeysTestCases(boolean isEmptyOptional) throws Exception {
		Config config = new Config();
		config.setKeyName("TestKey");
		config.setKeyValue("TestValue");
		config.setKeyDescription("TestKeyDescription");

		Optional<Config> configOptional = null;
		if(isEmptyOptional) {
			configOptional = Optional.empty();
		}else {
			configOptional = Optional.of(config);
		}
		when(configRepository.findByKeyName(Mockito.anyString())).thenReturn(configOptional);
		when(configRepository.save(Mockito.any())).thenReturn(config);

	}
	
	private BookingRevision getBookingRevision() {
		
		BookingRevision bookingRevision = new BookingRevision();
		Booking booking = new Booking();
		booking.setBookingId(5001L);
		bookingRevision.setBooking(booking);
		bookingRevision.setJobNumber("2010");
		bookingRevision.setJobname("TestJob");
		Contractor contractor = new Contractor();
		contractor.setEmail("abc@test.com");
		bookingRevision.setContractor(contractor);

		return bookingRevision;
	}
	
} 