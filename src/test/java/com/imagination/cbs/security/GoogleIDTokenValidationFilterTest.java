/**
 * 
 */
package com.imagination.cbs.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class GoogleIDTokenValidationFilterTest {
	
	@InjectMocks
	private GoogleIDTokenValidationFilter googleIDTokenValidationFilter;
	
	@Mock
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private FilterChain chain;
	
	@Mock
	private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	
	
	@Test
	public void shouldValidateGoogleIdTokenByDoFilterInternal() throws IOException, ServletException, GeneralSecurityException{
		
		when(request.getHeader("Authorization")).thenReturn("Bearer abxvcfscrsuscnndhdh");
		when(googleIDTokenValidationUtility.validateTokenAndLoadUser("abxvcfscrsuscnndhdh")).thenReturn(usernamePasswordAuthenticationToken);
		
		
		googleIDTokenValidationFilter.doFilterInternal(request, response, chain);
		
		verify(request).getHeader("Authorization");
		verify(googleIDTokenValidationUtility).validateTokenAndLoadUser("abxvcfscrsuscnndhdh");
		
		
		
	}
	
	@Test
	public void shouldNotValidateGoogleIdTokenByDoFilterInternalWhenHeaderIsNull() throws IOException, ServletException {
	
		when(request.getHeader("Authorization")).thenReturn(null);
		
		googleIDTokenValidationFilter.doFilterInternal(request, response, chain);
		
		verify(request).getHeader("Authorization");
		
		
	}
	
	@Test
	public void shouldNotValidateGoogleIdTokenByDoFilterInternalWhenHeaderIsNotStartWirhBearer() throws IOException, ServletException {
		
		when(request.getHeader("Authorization")).thenReturn("Bear abxvcfscrsuscnndhdh");
		
		googleIDTokenValidationFilter.doFilterInternal(request, response, chain);
		
		verify(request).getHeader("Authorization");
		
		
	}
	
	@Test
	public void shouldThrowExceptionWhenTokenIsNotValid() throws IOException, ServletException, GeneralSecurityException {
		
		when(request.getHeader("Authorization")).thenReturn("Bearer abxvcfscrsuscnndhdh");
		when(googleIDTokenValidationUtility.validateTokenAndLoadUser("abxvcfscrsuscnndhdh")).thenThrow(GeneralSecurityException.class);
		
		googleIDTokenValidationFilter.doFilterInternal(request, response, chain);
		
		verify(request).getHeader("Authorization");
		verify(googleIDTokenValidationUtility).validateTokenAndLoadUser("abxvcfscrsuscnndhdh");
		
		
	}
		
		
		
		
		
}
