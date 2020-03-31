package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.imagination.cbs.domain.ContractorEmployeeSearch;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.repository.ContractorEmployeeRepository;


@RunWith(MockitoJUnitRunner.class)
public class ContractorServiceImplTest {

	@Mock
	private ContractorEmployeeRepository contractorEmployeeRepository;
	
	@InjectMocks
	private ContractorServiceImpl contractorServiceImpl;
	
	@Test
	public void geContractorEmployeeDetailsByRoleIdIfRoleIdExistsInDB() {
			
		when(contractorEmployeeRepository.findByRoleId(Mockito.anyLong(), Mockito.any())).thenReturn(createPagedData());
		
		Page<ContractorEmployeeDto> contractorEmployeeDto = contractorServiceImpl.geContractorEmployeeDetailsByRoleId(1000L,0, 5,"roleId","ASC");
		
		verify(contractorEmployeeRepository, times(1)).findByRoleId(1000L, PageRequest.of(0, 5, Sort.by(Direction.ASC,"roleId")));
		String role = contractorEmployeeDto.getContent().get(0).getRole();
		assertEquals("2D", role);
	}
	
	@Test
	public void geContractorEmployeeDetailsByRoleIdIfRoleIdNotExistsInDB() {
		
		Page<ContractorEmployeeSearch> contractorEmployeeSearch = new PageImpl<>(new ArrayList<ContractorEmployeeSearch>(),PageRequest.of(0,5),0); 
		
		when(contractorEmployeeRepository.findByRoleId(Mockito.anyLong(), Mockito.any())).thenReturn(contractorEmployeeSearch);
		
		Page<ContractorEmployeeDto> contractorEmployeeDto = contractorServiceImpl.geContractorEmployeeDetailsByRoleId(1001L,0, 5,"roleId","DESC");
		
		verify(contractorEmployeeRepository, times(1)).findByRoleId(1001L, PageRequest.of(0, 5, Sort.by(Direction.DESC,"roleId")));
		assertTrue(contractorEmployeeDto.getContent().isEmpty());
	}
	
	@Test
	public void geContractorEmployeeDetailsByRoleIdAndNameIfNameExistsInDB() {
			
		when(contractorEmployeeRepository.findByRoleIdAndContractorEmployeeNameContains(Mockito.anyLong(), Mockito.anyString(), Mockito.any()))
		.thenReturn(createPagedDataForName());
		
		Page<ContractorEmployeeDto> contractorEmployeeDto = contractorServiceImpl.geContractorEmployeeDetailsByRoleIdAndName(1000L,"Alex",0, 5,"dayRate","ASC");
		
		verify(contractorEmployeeRepository, times(1)).findByRoleIdAndContractorEmployeeNameContains(1000L, "Alex", PageRequest.of(0, 5, Sort.by(Direction.ASC,"dayRate")));
		String contractorEmployeeName = contractorEmployeeDto.getContent().get(0).getContractorEmployeeName();
		assertEquals("Alex", contractorEmployeeName);
	}
	
	@Test
	public void geContractorEmployeeDetailsByRoleIdAndNameIfNameNotExistsInDB() {
		
		Page<ContractorEmployeeSearch> contractorEmployeeSearch = new PageImpl<>(new ArrayList<ContractorEmployeeSearch>(),PageRequest.of(0,5),0); 	
		when(contractorEmployeeRepository.findByRoleIdAndContractorEmployeeNameContains(Mockito.anyLong(), Mockito.anyString(), Mockito.any()))
		.thenReturn(contractorEmployeeSearch);
		
		Page<ContractorEmployeeDto> contractorEmployeeDto = contractorServiceImpl.geContractorEmployeeDetailsByRoleIdAndName(1000L,"Test",0, 5,"dayRate","ASC");
		
		verify(contractorEmployeeRepository, times(1)).findByRoleIdAndContractorEmployeeNameContains(1000L,"Test", PageRequest.of(0, 5, Sort.by(Direction.ASC,"dayRate")));
		assertTrue(contractorEmployeeDto.getContent().isEmpty());
		
	}
	
	private Page<ContractorEmployeeSearch>createPagedData() {
		List<ContractorEmployeeSearch> contractorEmployeeSearchedList = new ArrayList<>();
		
		ContractorEmployeeSearch ce1 = new ContractorEmployeeSearch();
		ce1.setContractorEmployeeName("Alex");
		ce1.setDayRate(new BigDecimal(100));
		ce1.setRole("2D");
		ce1.setCompany("Imagination");
		ce1.setNoOfBookingsInPast(3);
		contractorEmployeeSearchedList.add(ce1);

		ContractorEmployeeSearch ce2 = new ContractorEmployeeSearch();
		ce2.setContractorEmployeeName("Albert");
		ce2.setDayRate(new BigDecimal(200));
		ce2.setRole("2D Senior");
		ce2.setCompany("Imagination");
		ce2.setNoOfBookingsInPast(2);
		contractorEmployeeSearchedList.add(ce2);
		
		return new PageImpl<>(contractorEmployeeSearchedList, PageRequest.of(0, 5), 2);
	}
	
	private Page<ContractorEmployeeSearch>createPagedDataForName() {
		List<ContractorEmployeeSearch> contractorEmployeeSearchedList = new ArrayList<>();
		
		ContractorEmployeeSearch ce1 = new ContractorEmployeeSearch();
		ce1.setContractorEmployeeName("Alex");
		ce1.setDayRate(new BigDecimal(100));
		ce1.setRole("2D");
		ce1.setCompany("Imagination");
		ce1.setNoOfBookingsInPast(3);
		contractorEmployeeSearchedList.add(ce1);
		
		return new PageImpl<>(contractorEmployeeSearchedList, PageRequest.of(0, 5), 1);
	}
}
