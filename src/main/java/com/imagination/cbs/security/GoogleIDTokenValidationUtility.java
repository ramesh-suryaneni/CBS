package com.imagination.cbs.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

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
import com.imagination.cbs.exception.CBSAuthenticationException;

@Component(value="googleIDTokenValidationUtility")
public class GoogleIDTokenValidationUtility {

	private final String ID_TOKEN="idToken";
	private final String CLIENT_ID="73478530580-60km8n2mheo2e0e5qmg57617qae6fqij.apps.googleusercontent.com";
	
	@Autowired
	private SecurityUserDetailsServiceImpl securityUserDetailsServiceImpl;
	
	public boolean validateAccessToken(HttpServletRequest request) throws GeneralSecurityException, IOException{
		
		String idTokenString=request.getHeader(ID_TOKEN);
		final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID)).build();
		
		final GoogleIdToken googleIdToken=verifier.verify(idTokenString);
		
		if(googleIdToken!=null){
			
			final Payload payload=googleIdToken.getPayload();
			
			final Boolean emailVerified = payload.getEmailVerified();
			
			if(emailVerified){
				
				int index=payload.getEmail().indexOf("@");
				String truncatedEmailId=payload.getEmail().substring(0,index);
				
				UserDetails userDetails=securityUserDetailsServiceImpl.loadUserByUsername(truncatedEmailId);

				if(null !=userDetails){
					
					Authentication authresult = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(authresult);
					
				}else{
					
					throw new CBSAuthenticationException("User is not present with Google Account:- "+payload.getEmail());
				}
				
			}
			

			return emailVerified;
		}
		
		return false;
		
		
	}
}
