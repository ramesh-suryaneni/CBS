package com.imagination.cbs.service;

import com.imagination.cbs.domain.EmployeeMapping;

public interface EmployeeMappingService {
 
	EmployeeMapping getEmployeeMappingByGoogleAccount(String googleAccount);
}
