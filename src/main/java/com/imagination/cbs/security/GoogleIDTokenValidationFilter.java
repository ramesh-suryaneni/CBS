package com.imagination.cbs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imagination.cbs.constant.SecurityConstants;


@Component(value="googleIDTokenValidationFilter")
public class GoogleIDTokenValidationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleIDTokenValidationFilter.class);
	
	@Autowired
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("inside filter");
		
		String header = request.getHeader(SecurityConstants.HEADER_STRING.getSecurityConstants());
        String username = null;
        String authToken = null;
        try {
        	if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX.getSecurityConstants())) {
                authToken = header.replace(SecurityConstants.TOKEN_PREFIX.getSecurityConstants(),"");
                UsernamePasswordAuthenticationToken authentication = googleIDTokenValidationUtility.validateTokenAndLoadUser(authToken);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                LOGGER.info("Authenticated user {}, setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
        	}
            
        }catch(Exception e) {
        	LOGGER.error("Exception occured during token validation and loading an user: ",e);
        }
        
        chain.doFilter(request, response);

	}
	
}
