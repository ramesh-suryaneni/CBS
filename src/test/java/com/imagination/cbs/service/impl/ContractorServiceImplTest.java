package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.repository.ContractorRepository;

@RunWith(MockitoJUnitRunner.class)
public class ContractorServiceImplTest {

	@Mock
	private ContractorRepository repository;

	@Mock
	private ContractorMapper mapper;

	@InjectMocks
	private ContractorServiceImpl serviceImpl;

	@Test
	public void getContractorsByContractorNameIfContractorNameExistsInDB() {
		List<Contractor> contractorList = new ArrayList<>();

		Contractor cntr1 = new Contractor();
		cntr1.setContractorId(101L);
		cntr1.setContractorName("Yash");
		Contractor cntr2 = new Contractor();
		cntr2.setContractorId(102L);
		cntr2.setContractorName("Yash Technologies");
		contractorList.add(cntr1);
		contractorList.add(cntr2);

		List<ContractorDto> contractorDtoList = new ArrayList<>();

		ContractorDto cntrDto1 = new ContractorDto();
		cntrDto1.setContractorId(101);
		cntrDto1.setContractorName("Yash");
		ContractorDto cntrDto2 = new ContractorDto();
		cntrDto2.setContractorId(102);
		cntrDto2.setContractorName("Yash Technologies");

		contractorDtoList.add(cntrDto1);
		contractorDtoList.add(cntrDto2);
		
		when(repository.findByContractorNameContains("Yash")).thenReturn(contractorList);
		when(mapper.toListOfContractorDto(contractorList)).thenReturn(contractorDtoList);
		
		List<ContractorDto> resultContractorDtoList = serviceImpl.getContractorsByContractorName("Yash");

		verify(repository).findByContractorNameContains("Yash");
		verify(mapper).toListOfContractorDto(contractorList);
		
		assertEquals(resultContractorDtoList.get(0).getContractorName(), "Yash");
		assertEquals(resultContractorDtoList.get(1).getContractorName(), "Yash Technologies");
	}

	@Test
	public void getContractorsByContractorNameIfContractorNameNotExistsInDB() {
		
		List<Contractor> contractorList = new ArrayList<>();
		List<ContractorDto> contractorDtoList = new ArrayList<>();
		
		when(repository.findByContractorNameContains("Yash")).thenReturn(contractorList);
		when(mapper.toListOfContractorDto(contractorList)).thenReturn(contractorDtoList);

		List<ContractorDto> resultContractorDtoList = serviceImpl.getContractorsByContractorName("Yash");
		
		verify(repository).findByContractorNameContains("Yash");
		verify(mapper).toListOfContractorDto(contractorList);

		assertEquals(contractorDtoList, resultContractorDtoList);
	}
}
