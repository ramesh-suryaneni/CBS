package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.repository.EmployeeMappingRepository;
import com.imagination.cbs.service.EmployeeMappingService;

@Service("employeeMappingService")
public class EmployeeMappingServiceImpl implements EmployeeMappingService{

	@Autowired
	private EmployeeMappingRepository employeeMappingRepository;
	
	@Override
	public EmployeeMapping getEmployeeMappingByGoogleAccount(String googleAccount) {
		
		return employeeMappingRepository.getEmployeeMappingByGoogleAccount(googleAccount);
	}

}
