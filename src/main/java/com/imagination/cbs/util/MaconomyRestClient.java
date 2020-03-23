package com.imagination.cbs.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.exception.ResourceNotFoundException;

@Component("maconomyRestClient")
public class MaconomyRestClient {
	
	 	@Autowired
	    private RestTemplate restTemplate;
	 	
	 	@SuppressWarnings("unchecked")
	    public <T> T callRestServiceForGet(String url, String username, String password) {
	       
	 		ResponseEntity<T> responseEntity = null;
	 		
	        try {
	        	responseEntity = (ResponseEntity<T>) restTemplate.exchange(url, HttpMethod.GET,
						new HttpEntity<byte[]>(getHttpHeaders(MaconomyConstant.MEDIA_TYPE.getMacanomy(), username, password)), JsonNode.class);

	            } catch (RuntimeException runtimeException) {
					throw new ResourceNotFoundException(runtimeException.getMessage());
	            }
	        return (T) responseEntity;

}
	 	

	 	public  HttpHeaders getHttpHeaders(String mediaType, String username, String password) {
	 		
	        List<MediaType> acceptHeaders = new ArrayList<MediaType>();
	        acceptHeaders.add(org.springframework.http.MediaType.valueOf(mediaType));
	        HttpHeaders headers = createHeaders(username, password);
	        headers.setAccept(acceptHeaders);
	        headers.setContentType(acceptHeaders.get(0));
	        headers.set(MaconomyConstant.MACONOMY_HEADER_KEY.getMacanomy(), MaconomyConstant.MACONOMY_HEADER_VALUE.getMacanomy());
	        return headers;
	    }
	 
	 	private  HttpHeaders createHeaders(String username, String password) {
	        HttpHeaders headers = new HttpHeaders();
	        String auth = username + ":" + password;
	        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
	        String authHeader = "Basic " + new String(encodedAuth);
	        headers.set("Authorization", authHeader);
	        return headers;
	    }


}
