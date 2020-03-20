package com.imagination.cbs.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.exception.CBSAuthenticationException;
import com.imagination.cbs.service.impl.ConfigServiceImpl;

@Component(value = "googleIDTokenValidationUtility")
public class GoogleIDTokenValidationUtility {

	private Logger logger = LoggerFactory.getLogger(GoogleIDTokenValidationUtility.class);

	private final String ID_TOKEN = "Authorization";
	private final String GOOGLE_CLIENTID_KEY = "GOOGLE_ID";
	
	@Autowired
	private SecurityUserDetailsServiceImpl securityUserDetailsServiceImpl;
	
	@Autowired
	private ConfigServiceImpl configServiceImpl;

	public boolean validateAccessToken(HttpServletRequest request) throws GeneralSecurityException, IOException {

		String idTokenString = request.getHeader(ID_TOKEN);

		if (null == idTokenString) {
			throw new CBSAuthenticationException("Bearer/ID Token is not present");
		}

		logger.info("Authorization Header: " + idTokenString);
		// removes bearer from tokenstring
		idTokenString = idTokenString.substring(7);
		logger.info("ID Token: " + idTokenString);

		String clientId=getGoogleClientID();
		
		final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(clientId)).build();

		final GoogleIdToken googleIdToken = verifier.verify(idTokenString);

		if (googleIdToken != null) {

			final Payload payload = googleIdToken.getPayload();

			final Boolean emailVerified = payload.getEmailVerified();
			
			if (emailVerified && payload.getAudience().toString().equalsIgnoreCase(clientId)) {

				int index = payload.getEmail().indexOf("@");
				String truncatedEmailId = payload.getEmail().substring(0, index);

				UserDetails userDetails = securityUserDetailsServiceImpl.loadUserByUsername(truncatedEmailId);

				if (null != userDetails) {

					Authentication authresult = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authresult);

				} else {

					throw new CBSAuthenticationException(
							"User is not present with Google Account:- " + payload.getEmail());
				}

				return true;
			}

			
		}

		return false;

	}

	private String getGoogleClientID() {
		
		Config config=configServiceImpl.getConfigDetailsByKeyName(GOOGLE_CLIENTID_KEY);
		
		return config.getKeyValue();
	}
}
