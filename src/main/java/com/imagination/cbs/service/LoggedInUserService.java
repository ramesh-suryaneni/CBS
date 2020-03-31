/**
 * 
 */
package com.imagination.cbs.service;

import com.imagination.cbs.security.CBSUser;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface LoggedInUserService {
	
	public CBSUser getLoggedInUserDetails();
	
	public boolean isCurrentUserInHRRole();

}
