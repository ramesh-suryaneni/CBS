package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.repository.EmployeeMappingRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeMappingServiceImplTest {

	@Mock
	private EmployeeMappingRepository employeeMappingRepository;

	@InjectMocks
	private EmployeeMappingServiceImpl employeeMappingService;

	@Test
	public void getEmployeeMappingByGoogleAccountIfGoogleAccountExists() {

		String googleAccount = "mark.rothwell";
		EmployeeMapping emp = employeeObject();

		when(employeeMappingRepository.getEmployeeMappingByGoogleAccount(googleAccount)).thenReturn(emp);

		EmployeeMapping actualEmployeeObject = employeeMappingService.getEmployeeMappingByGoogleAccount(googleAccount);
		assertEquals(1017L, actualEmployeeObject.getEmployeeId());
		assertEquals("mark.rothwell", actualEmployeeObject.getGoogleAccount());
		assertEquals("-42", actualEmployeeObject.getEmployeeNumberMaconomy());
	}

	private EmployeeMapping employeeObject() {

		EmployeeMapping empObj = new EmployeeMapping();
		empObj.setEmployeeId(1017L);
		empObj.setGoogleAccount("mark.rothwell");
		empObj.setEmployeeNumberMaconomy("-42");
		return empObj;
	}

}
