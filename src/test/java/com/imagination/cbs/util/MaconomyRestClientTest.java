package com.imagination.cbs.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.exception.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class MaconomyRestClientTest {

	@InjectMocks
	MaconomyRestClient maconomyRestClient;
	
 	@Mock
    private RestTemplate restTemplate;

 	@Test
 	public void callRestServiceForGet_shouldReturnSuccess_forGetRequest() {
 		
 		when(restTemplate.exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
 		
 		final ResponseEntity<JsonNode> actual = maconomyRestClient.callRestServiceForGet("https:baseurl/maconomy/rest", "username", "password");
 		
 		verify(restTemplate).exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class));

 		assertEquals(HttpStatus.OK, actual.getStatusCode());
 	}

 	@Test
 	public void callRestServiceForGet_shouldReturnFailure_forGetRequest() {
 		
 		when(restTemplate.exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
 		
 		final ResponseEntity<JsonNode> actual = maconomyRestClient.callRestServiceForGet("https:baseurl/maconomy/rest", "username", "password");
 		
 		verify(restTemplate).exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class));

 		assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
 	}

 	@Test(expected = ResourceNotFoundException.class)
 	public void callRestServiceForGet_shouldThrowResourceNotFoundException_whenRuntimeExceptionOccured() {

 		when(restTemplate.exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class))).thenThrow(RuntimeException.class);
 		maconomyRestClient.callRestServiceForGet("https:baseurl/maconomy/rest", "username", "password");
 	}
}
