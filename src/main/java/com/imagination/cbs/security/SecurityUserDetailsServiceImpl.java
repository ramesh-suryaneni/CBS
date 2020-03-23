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

@Service("securityUserDetailsService")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeMappingService employeeMappingService;

	@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		EmployeeMapping employeeMapping = employeeMappingService.getEmployeeMappingByGoogleAccount(email);
		if (null != employeeMapping) {

			//return new CBSUser(employeeMapping.getGoogleAccount(),employeeMapping.getEmployeeNumberMaconomy(),getAuthority(employeeMapping));
			return new CBSUser(email, employeeMapping.getEmployeeId(),employeeMapping.getEmployeeNumberMaconomy(), employeeMapping.getGoogleAccount(), 
					null, email, getAuthority(employeeMapping));
		}
		return null;
	}

	 private Set<SimpleGrantedAuthority> getAuthority(EmployeeMapping employeeMapping) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        
	        employeeMapping.getEmployeePermissions().forEach(employeePermissions ->{
	        	authorities.add(new SimpleGrantedAuthority(employeePermissions.getPermission().getPermissionName()));
	        });
			
			return authorities;
		}

}
