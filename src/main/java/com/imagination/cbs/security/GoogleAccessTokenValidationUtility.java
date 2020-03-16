package com.imagination.cbs.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Component(value="googleAccessTokenValidationUtility")
public class GoogleAccessTokenValidationUtility {

	private final String ID_TOKEN="idToken";
	private final String CLIENT_ID="73478530580-60km8n2mheo2e0e5qmg57617qae6fqij.apps.googleusercontent.com";
	
	public boolean validateAccessToken(HttpServletRequest request) throws GeneralSecurityException, IOException{
		
		String idTokenString=request.getHeader(ID_TOKEN);
		final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID)).build();
		
		final GoogleIdToken idToken=verifier.verify(idTokenString);
		
		if(idToken==null){
			
			final Payload payload=idToken.getPayload();
			
			final Boolean emailVerified = payload.getEmailVerified();
			if(emailVerified){
				Authentication authresult = new UsernamePasswordAuthenticationToken(payload.getEmail(), null,
						AuthorityUtils.NO_AUTHORITIES);
				SecurityContextHolder.getContext().setAuthentication(authresult);
			}
			

			return emailVerified;
		}
		
		return false;
		
		
	}
}
