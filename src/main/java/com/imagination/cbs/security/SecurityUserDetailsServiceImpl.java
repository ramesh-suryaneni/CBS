/*package com.imagination.cbs.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.repository.EmployeeMappingRepository;

@Service("securityUserDetailsServiceImpl")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeMappingRepository employeeMappingRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		EmployeeMapping employeeMapping = employeeMappingRepository.getEmployeeMappingByGoogleAccount(email);
		if (null != employeeMapping) {

			return new User(employeeMapping.getGoogleAccount(),employeeMapping.getEmployeeNumberMaconomy(),getAuthority(employeeMapping));
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
*/