package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorIndex;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorIndexDto;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.repository.ContractorIndexRepository;
import com.imagination.cbs.repository.ContractorRepository;

@RunWith(MockitoJUnitRunner.class)
public class ContractorServiceImplTest {

	@Mock
	private ContractorRepository contractorRepository;

	@Mock
	private ContractorIndexRepository contractorIndexRepository;
	
	@Mock
	private ContractorMapper contractorMapper;

	@InjectMocks
	private ContractorServiceImpl contractorServiceImpl;

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
		
		when(contractorRepository.findByContractorNameContains("Yash")).thenReturn(contractorList);
		when(contractorMapper.toListOfContractorDto(contractorList)).thenReturn(contractorDtoList);
		
		List<ContractorDto> resultContractorDtoList = contractorServiceImpl.getContractorsByContractorName("Yash");

		verify(contractorRepository).findByContractorNameContains("Yash");
		verify(contractorMapper).toListOfContractorDto(contractorList);
		
		assertEquals(resultContractorDtoList.get(0).getContractorName(), "Yash");
		assertEquals(resultContractorDtoList.get(1).getContractorName(), "Yash Technologies");
	}

	@Test
	public void getContractorsByContractorNameIfContractorNameNotExistsInDB() {
		
		List<Contractor> contractorList = new ArrayList<>();
		List<ContractorDto> contractorDtoList = new ArrayList<>();
		
		when(contractorRepository.findByContractorNameContains("Yash")).thenReturn(contractorList);
		when(contractorMapper.toListOfContractorDto(contractorList)).thenReturn(contractorDtoList);

		List<ContractorDto> resultContractorDtoList = contractorServiceImpl.getContractorsByContractorName("Yash");
		
		verify(contractorRepository).findByContractorNameContains("Yash");
		verify(contractorMapper).toListOfContractorDto(contractorList);

		assertEquals(contractorDtoList, resultContractorDtoList);
	}
	
	@Test
	public void getContractorIndexDeatilsIfDetailsExists() {
		Sort sort = Sort.by(Direction.ASC, "contractorName");
		Pageable mockedPageable = PageRequest.of(0, 10, sort);
		when(contractorIndexRepository.findAll(mockedPageable)).thenReturn(createPagedData());
		Page<ContractorIndexDto> contracorIndexDtoPage = contractorServiceImpl.getContractorIndexDeatils(0, 10, "contractorName", "ASC");
		
		verify(contractorIndexRepository, times(1)).findAll(mockedPageable);
		
		String contractorName = contracorIndexDtoPage.getContent().get(0).getContractorName();
		assertEquals("Imagination", contractorName);
	}
	
	@Test
	public void getContractorIndexDeatilsIfDetailsNotExists() {
		Sort sort = Sort.by(Direction.DESC, "contractorName");
		Pageable mockedPageable = PageRequest.of(0, 10, sort);
		when(contractorIndexRepository.findAll(mockedPageable)).thenReturn(new PageImpl<>(new ArrayList<ContractorIndex>()));
		Page<ContractorIndexDto> contracorIndexDtoPage = contractorServiceImpl.getContractorIndexDeatils(0, 10, "contractorName", "DESC");
		
		verify(contractorIndexRepository, times(1)).findAll(mockedPageable);
		
		assertEquals(true, contracorIndexDtoPage.getContent().isEmpty());
	}
		
	@Test
	public void getContractorIndexDeatilsByContractorNameIfNameExists() {

		when(contractorIndexRepository.findByContractorNameContains(Mockito.anyString(), Mockito.any())).thenReturn(createPagedData());
		Page<ContractorIndexDto> contracorIndexDtoPage = contractorServiceImpl.
				getContractorIndexDeatilsByContractorName("Imagination",0, 10, "contractorName", "DESC");
		
		verify(contractorIndexRepository, times(1)).findByContractorNameContains("Imagination", PageRequest.of(0, 10, Sort.by(Direction.DESC, "contractorName")));
		String role = contracorIndexDtoPage.getContent().get(0).getRole();
		assertEquals("2D", role);
		
	}
	
	@Test
	public void getContractorIndexDeatilsByContractorNameIfNameNotExists() {
		List<ContractorIndex> contractorIndexList = new ArrayList<>();
		Page<ContractorIndex> contractorIndexPage = new PageImpl<>(contractorIndexList);

		when(contractorIndexRepository.findByContractorNameContains(Mockito.anyString(), Mockito.any())).thenReturn(contractorIndexPage);
		Page<ContractorIndexDto> contracorIndexDtoPage = contractorServiceImpl.
				getContractorIndexDeatilsByContractorName("Test",0, 10, "contractorName", "ASC");
		
		verify(contractorIndexRepository, times(1)).findByContractorNameContains("Test", PageRequest.of(0, 10, Sort.by(Direction.ASC, "contractorName")));
		
		assertEquals(true,contracorIndexDtoPage.getContent().isEmpty());
		
	}
	
	private Page<ContractorIndex> createPagedData(){
		List<ContractorIndex> contractorIndexList = new ArrayList<>();
		
		ContractorIndex contractorIndex1 = new ContractorIndex();
		contractorIndex1.setContractorName("Imagination");
		contractorIndex1.setAlias("Aliase 1");
		contractorIndex1.setRole("2D");
		contractorIndex1.setRate(new BigDecimal(1000));
		contractorIndex1.setDiscipline("Creative");
		contractorIndex1.setRating(new BigDecimal(4.5));
		contractorIndexList.add(contractorIndex1);
		
		ContractorIndex contractorIndex2 = new ContractorIndex();
		contractorIndex2.setContractorName("Imagination-CBS");
		contractorIndex2.setAlias("Aliase 2");
		contractorIndex2.setRole("2D Senior");
		contractorIndex2.setRate(new BigDecimal(2000));
		contractorIndex2.setDiscipline("Production");
		contractorIndex2.setRating(new BigDecimal(3.5));
		contractorIndexList.add(contractorIndex2);
		contractorIndexList.add(contractorIndex2);

		return  new PageImpl<>(contractorIndexList);
	}
}
