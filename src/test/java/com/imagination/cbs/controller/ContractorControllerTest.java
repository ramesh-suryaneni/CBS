package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.service.impl.ContractorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ContractorController.class)

public class ContractorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContractorServiceImpl contractorServiceImpl;
	
	@Test
	public void getContractorContainingName_NameExists() throws Exception {
		List<ContractorDto> contractorDtos = getContractorDtos();
		
		when(contractorServiceImpl.getContractorsContainingName("Im")).thenReturn(contractorDtos);
		
		mockMvc.perform(get("/contractors/Im").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].contractorName", comparesEqualTo("Imagination")));
		
		verify(contractorServiceImpl).getContractorsContainingName("Im");
	}
	
	@Test
	public void getContractorContainingName_NoNameExists() throws Exception {
		List<ContractorDto> contractorDtos = new ArrayList<>();
		
		when(contractorServiceImpl.getContractorsContainingName(Mockito.anyString())).thenReturn(contractorDtos);
		
		mockMvc.perform(get("/contractors/Test").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
		
		verify(contractorServiceImpl).getContractorsContainingName("Test");
	}
	public List<ContractorDto> getContractorDtos(){
		List<ContractorDto> contractorDtoList = new ArrayList<>();
		ContractorDto contractorDto1 = new ContractorDto();
		contractorDto1.setContractorId(101);
		contractorDto1.setContractorName("Imagination");
		contractorDtoList.add(contractorDto1);
		
		return contractorDtoList;
	}
}
