/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.service.impl.AdobeOAuthTokensServiceImpl;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/contractors")
public class ContractorController {

	@Autowired
	AdobeOAuthTokensServiceImpl accessToken;
	
	@GetMapping("/getAdobeOauthAccessToken")
	public ResponseEntity<Object> getAdobeOauthAccessToken() {
		String adobeOAuthToken = null;
		Map<String,Object> map=new HashMap<>();

		try {
			adobeOAuthToken = accessToken.getOauthAccessToken();
			map.put("accessToken", adobeOAuthToken);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
}
