/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.constant.SecurityConstants;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Service("loggedInUserService")
public class LoggedInUserServiceImpl implements LoggedInUserService {

	@Override
	public CBSUser getLoggedInUserDetails() {
		
		return (CBSUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	}

	@Override
	public boolean isCurrentUserInHRRole() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> String.valueOf(SecurityConstants.ROLE.getSecurityConstant() + SecurityConstants.ROLE_CONTRACT_MGT_ID.getRoleDetails()).equalsIgnoreCase(grantedAuthority.getAuthority())))
            .orElse(false);
    }

}
