/**
 * 
 */
package com.imagination.cbs.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.EmployeePermissions;
import com.imagination.cbs.domain.Permission;
import com.imagination.cbs.security.SecurityUserDetailsServiceImpl;
import com.imagination.cbs.service.EmployeeMappingService;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class SecurityUserDetailsServiceImplTest {
	
	@InjectMocks
	private SecurityUserDetailsServiceImpl securityUserDetailsServiceImpl;
	
	@Mock
	private EmployeeMappingService employeeMappingService;
	
	
	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowUsernameNotFoundExceptionWhenUserEmailIsNotPresentInDB(){
		
		when(employeeMappingService.getEmployeeMappingByGoogleAccount("pappu.rout")).thenReturn(null);
		
		securityUserDetailsServiceImpl.loadUserByUsername("pappu.rout");
	}
	
	@Test
	public void shouldReturnUserDetailsWhenUserEmailIsPresentInDB(){
		
		EmployeeMapping employeeMapping = new EmployeeMapping();
		employeeMapping.setEmployeeId(1036L);
		employeeMapping.setEmployeeNumberMaconomy("-42");
		employeeMapping.setGoogleAccount("pappu.rout");
		
		Set<EmployeePermissions> employeePermissionsSet = new HashSet<>();
		EmployeePermissions employeePermissions = new EmployeePermissions();
		employeePermissions.setEmpPermissionId(7L);
		
		Permission permission = new Permission();
		permission.setPermissionId(6L);
		
		employeePermissions.setPermission(permission);
		
		employeePermissionsSet.add(employeePermissions);
		
		employeeMapping.setEmployeePermissions(employeePermissionsSet);
		
		when(employeeMappingService.getEmployeeMappingByGoogleAccount("pappu.rout")).thenReturn(employeeMapping);
		
		UserDetails actual = securityUserDetailsServiceImpl.loadUserByUsername("pappu.rout");
		
		verify(employeeMappingService).getEmployeeMappingByGoogleAccount("pappu.rout");
		
		assertEquals("pappu.rout", actual.getUsername());
		assertEquals("[ROLE_6]", actual.getAuthorities().toString());
		
	}
	
	
	
	
	
	

}
