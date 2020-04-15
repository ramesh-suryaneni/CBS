package com.imagination.cbs.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.service.ConfigService;

@Component(value = "googleIDTokenValidationUtility")
public class GoogleIDTokenValidationUtility {

	private static final String GOOGLE_CLIENTID_KEY = "GOOGLE_ID";
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	public UsernamePasswordAuthenticationToken validateTokenAndLoadUser(String token) throws GeneralSecurityException, IOException {
		
		String clientId = getGoogleClientID();
		final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
    	final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
    				.setAudience(Collections.singletonList(clientId)).build();

    	final GoogleIdToken googleIdToken = verifier.verify(token);

    	if (googleIdToken != null) {

    		final Payload payload = googleIdToken.getPayload();

    		final Boolean emailVerified = payload.getEmailVerified();
    			
    		if (emailVerified && payload.getAudience().toString().equalsIgnoreCase(clientId)) {
    			
    			int index = payload.getEmail().indexOf('@');
    			String truncatedEmailId = payload.getEmail().substring(0, index);
    			
    			CBSUser user = (CBSUser) userDetailsService.loadUserByUsername(truncatedEmailId);
    			
    			return new UsernamePasswordAuthenticationToken(user, null,
    					user.getAuthorities());
    				
    		}
        }
    	
    	return null;
       			

	}

	private String getGoogleClientID() {
		
		Config config = configService.getConfigDetailsByKeyName(GOOGLE_CLIENTID_KEY);
		
		return config.getKeyValue();
	}
}
