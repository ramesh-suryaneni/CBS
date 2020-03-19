/**
 * 
 */
package com.imagination.cbs.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.imagination.cbs.constant.MaconomyConstant;

/**
 * @author pappu.rout
 *
 */
public class MaconomyUtils {
	
	 public static HttpHeaders getHttpHeaders(String mediaType, String userName, String password) {
	        List<MediaType> acceptHeaders = new ArrayList<MediaType>();
	        acceptHeaders.add(org.springframework.http.MediaType.valueOf(mediaType));
	        HttpHeaders headers = createHeaders(userName, password);
	        headers.setAccept(acceptHeaders);
	        headers.setContentType(acceptHeaders.get(0));
	        headers.set(MaconomyConstant.MACONOMY_HEADER_KEY.getMacanomy(), MaconomyConstant.MACONOMY_HEADER_VALUE.getMacanomy());
	        return headers;
	    }
	 
	 private static  HttpHeaders createHeaders(String username, String password) {
	        HttpHeaders headers = new HttpHeaders();
	        String auth = username + ":" + password;
	        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
	        String authHeader = "Basic " + new String(encodedAuth);
	        headers.set("Authorization", authHeader);
	        return headers;
	    }

}
