package com.imagination.cbs.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.ContractorService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ContractorEmployeeSearchController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ContractorEmployeeSearchControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContractorService contractorServiceImpl;

	@WithMockUser("/developer")
	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDto() throws Exception {

		Page<ContractorEmployeeSearchDto> contractorEmployeeSearchDto = createPagedData();
		
		when(contractorServiceImpl.getContractorEmployeeDetails(0, 10, "roleId", "ASC")).thenReturn(contractorEmployeeSearchDto);

		mockMvc.perform(get("/contractor-employees").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"))
				.andExpect(jsonPath("$.content[1].contractorEmployeeName").value("Albert"));

		verify(contractorServiceImpl).getContractorEmployeeDetails(0, 10, "roleId", "ASC");
	}

	@WithMockUser("/developer")
	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDtoByRoleName() throws Exception {

		Page<ContractorEmployeeSearchDto> contractorEmployeeSearchDto = createPagedData();
		
		when(contractorServiceImpl.getContractorEmployeeDetailsByRoleName("2D", 0, 10, "roleId", "ASC")).thenReturn(contractorEmployeeSearchDto);

		mockMvc.perform(get("/contractor-employees").param("role", "2D").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"))
				.andExpect(jsonPath("$.content[1].contractorEmployeeName").value("Albert"));

		verify(contractorServiceImpl).getContractorEmployeeDetailsByRoleName("2D", 0, 10, "roleId", "ASC");
	}

	@WithMockUser("/developer")
	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDtoByName() throws Exception {

		Page<ContractorEmployeeSearchDto> contractorEmployeeSearchDto = createPagedData();
		
		when(contractorServiceImpl.getContractorEmployeeDetailsByName("Al", 0, 10, "roleId", "ASC")).thenReturn(contractorEmployeeSearchDto);

		mockMvc.perform(get("/contractor-employees").param("name", "Al").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"))
				.andExpect(jsonPath("$.content[1].contractorEmployeeName").value("Albert"));

		verify(contractorServiceImpl).getContractorEmployeeDetailsByName("Al", 0, 10, "roleId", "ASC");
	}

	@WithMockUser("/developer")
	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDtoByNameAndRoleName() throws Exception {

		Page<ContractorEmployeeSearchDto> contractorEmployeeSearchDto = createPagedData();
		
		when(contractorServiceImpl.getContractorEmployeeDetailsByNameAndRoleName("Al", "2D", 0, 10, "roleId", "ASC"))
						.thenReturn(contractorEmployeeSearchDto);

		mockMvc.perform(get("/contractor-employees").param("name", "Al").param("role", "2D")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"));

		verify(contractorServiceImpl).getContractorEmployeeDetailsByNameAndRoleName("Al", "2D", 0, 10, "roleId", "ASC");
	}

	
	private Page<ContractorEmployeeSearchDto> createPagedData() {
		
		List<ContractorEmployeeSearchDto> contractorEmployeeSearchDtoList = new ArrayList<>();

		ContractorEmployeeSearchDto ce1 = new ContractorEmployeeSearchDto();
		ce1.setContractorEmployeeName("Alex");
		ce1.setDayRate(new BigDecimal(100));
		ce1.setRole("2D");
		ce1.setCompany("Imagination");
		ce1.setNoOfBookingsInPast(3);
		contractorEmployeeSearchDtoList.add(ce1);

		ContractorEmployeeSearchDto ce2 = new ContractorEmployeeSearchDto();
		ce2.setContractorEmployeeName("Albert");
		ce2.setDayRate(new BigDecimal(200));
		ce2.setRole("2D Senior");
		ce2.setCompany("Imagination");
		ce2.setNoOfBookingsInPast(2);
		contractorEmployeeSearchDtoList.add(ce2);

		return new PageImpl<>(contractorEmployeeSearchDtoList, PageRequest.of(0, 5), 2);
	}

}