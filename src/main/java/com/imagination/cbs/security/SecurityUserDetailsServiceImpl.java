package com.imagination.cbs.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.service.EmployeeMappingService;
import com.imagination.cbs.util.SecurityConstants;

@Service("securityUserDetailsService")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeMappingService employeeMappingService;

	@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		EmployeeMapping employeeMapping = employeeMappingService.getEmployeeMappingByGoogleAccount(email);
		 if (employeeMapping == null) {
	            throw new UsernameNotFoundException(email);
	     }
		 else {

			return new CBSUser(email, employeeMapping.getEmployeeId(),employeeMapping.getEmployeeNumberMaconomy(), 
					employeeMapping.getGoogleAccount(), null, email, getAuthority(employeeMapping));
		}
	}

	 private Set<SimpleGrantedAuthority> getAuthority(EmployeeMapping employeeMapping) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        
	        employeeMapping.getEmployeePermissions().forEach(employeePermissions ->{
	        	String role = SecurityConstants.ROLE_+String.valueOf(employeePermissions.getPermission().getPermissionId());
	        	authorities.add(new SimpleGrantedAuthority(role));
	        });
			
			return authorities;
		}

}
