package com.imagination.cbs.security;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class GoogleAccessTokenValidationFilter extends BasicAuthenticationFilter {

	
	private GoogleAccessTokenValidationUtility googleAccessTokenValidationUtility;

	private static Logger logger = LoggerFactory.getLogger(GoogleAccessTokenValidationFilter.class);

	public GoogleAccessTokenValidationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
		googleAccessTokenValidationUtility=new GoogleAccessTokenValidationUtility();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("inside filter");
		
	
			try {
				if (googleAccessTokenValidationUtility.validateAccessToken(request)) {
					
					RequestDispatcher dispatcher = request.getRequestDispatcher(request.getServletPath());

					dispatcher.forward(request, response);
					
				}else{
					logger.info("Id token is invalid");
				}
			} catch (GeneralSecurityException e) {
				
				e.printStackTrace();
			}
		

	}

}
