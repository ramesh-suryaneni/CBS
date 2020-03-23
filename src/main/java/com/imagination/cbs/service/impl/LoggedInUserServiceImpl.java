/**
 * 
 */
package com.imagination.cbs.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Service("loggedInUserService")
public class LoggedInUserServiceImpl implements LoggedInUserService {

	@Override
	public CBSUser getLoggedInUserDetails() {
		
		CBSUser user = (CBSUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return user;
	}

}
