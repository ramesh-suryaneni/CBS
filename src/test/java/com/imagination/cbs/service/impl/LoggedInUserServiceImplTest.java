/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.imagination.cbs.security.CBSUser;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class LoggedInUserServiceImplTest {
	
	@InjectMocks
	private LoggedInUserServiceImpl loggedInUserServiceImpl;
	
	
	
	@Test
	public void shouldReturnLoggedInUserDetails(){
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		CBSUser actual = loggedInUserServiceImpl.getLoggedInUserDetails();
		
		assertEquals("Pappu", actual.getDisplayName());
		
	}
	
	Authentication authentication = new Authentication() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String getName() {
			return null;
		}
		
		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			
		}
		
		@Override
		public boolean isAuthenticated() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Object getPrincipal() {
			return new CBSUser("Pappu");
		}
		
		@Override
		public Object getDetails() {
			return null;
		}
		
		@Override
		public Object getCredentials() {
			return null;
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			
			return null;
		}
	};
	
	
	

}
