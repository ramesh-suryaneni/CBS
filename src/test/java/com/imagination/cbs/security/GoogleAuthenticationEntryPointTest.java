/**
 * 
 */
package com.imagination.cbs.security;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;

@RunWith(MockitoJUnitRunner.class)
public class GoogleAuthenticationEntryPointTest {
	
	
	@InjectMocks
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private AuthenticationException authException;
	
	@Test
	public void shouldValidateUserWhenLoginInApplication() throws IOException, ServletException{
		
		googleAuthenticationEntryPoint.commence(request, response,  authException);
		
		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		
	}

}
