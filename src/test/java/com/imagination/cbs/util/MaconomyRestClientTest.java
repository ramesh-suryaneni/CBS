package com.imagination.cbs.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
 	public void shouldCallRestServiceForGet() {
 		
 		when(restTemplate.exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
 		
 		ResponseEntity<JsonNode> actual = maconomyRestClient.callRestServiceForGet("https:baseurl/maconomy/rest", "username", "password");
 		
 		verify(restTemplate).exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class));

 		assertNotNull(actual);
 		assertEquals(HttpStatus.OK, actual.getStatusCode());
 	}

 	@Test(expected = ResourceNotFoundException.class)
 	public void shouldThrowResourceNotFoundExceptionWhenRuntimeExceptionOccured() {

 		when(restTemplate.exchange(eq("https:baseurl/maconomy/rest"), eq(HttpMethod.GET), any(), eq(JsonNode.class))).thenThrow(RuntimeException.class);
 		
 		maconomyRestClient.callRestServiceForGet("https:baseurl/maconomy/rest", "username", "password");
 	}
}
