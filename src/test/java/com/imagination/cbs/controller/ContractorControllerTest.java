package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorEmployeeRequest;
import com.imagination.cbs.dto.ContractorRequest;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.ContractorService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ContractorController.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ContractorControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;

	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;

	@MockBean
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContractorService contractorService;

	@Autowired
	ObjectMapper objectMapper;

	@WithMockUser("/developer")
	@Test
	public void shouldReturnContractorByContractorId() throws Exception {

		when(contractorService.getContractorByContractorId(6214l)).thenReturn(createContractorDto());

		this.mockMvc.perform(get("/contractors/6214").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.country", comparesEqualTo("United Kingdom")));

		verify(contractorService).getContractorByContractorId(6214l);
	}

	@WithMockUser("/developer")
	@Test
	public void shouldReturnContractorEmployeeByContrctorIdAndEmployeeId() throws Exception {
		when(contractorService.getContractorEmployeeByContractorIdAndEmployeeId(6000l, 5000l))
				.thenReturn(createContractorEmployeeDto());

		this.mockMvc.perform(get("/contractors/6000/employees/5000").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.contractorEmployeeName", comparesEqualTo("Alex")));

		verify(contractorService).getContractorEmployeeByContractorIdAndEmployeeId(6000l, 5000l);
	}

	@WithMockUser("developer")
	@Test
	public void shouldAddNewContractorEmployee() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(createContractorEmployeeRequest());

		when(contractorService.addContractorEmployee(Mockito.anyLong(), Mockito.any(ContractorEmployeeRequest.class)))
				.thenReturn(createContractorEmployeeDto());

		MvcResult mvcResult = this.mockMvc.perform(
				post("/contractors/6000/employees").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		assertEquals(HttpStatus.SC_CREATED, mvcResult.getResponse().getStatus());

	}

	@WithMockUser("devloper")
	@Test
	public void shouldAddNewContractor() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(createContractorRequest());

		when(contractorService.addContractorDetails(Mockito.any(ContractorRequest.class)))
				.thenReturn(createContractorDto());

		MvcResult mvcResult = this.mockMvc
				.perform(post("/contractors").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		assertEquals(HttpStatus.SC_CREATED, mvcResult.getResponse().getStatus());
	}

	@WithMockUser("developer")
	@Test
	public void shouldReturnPageResponseOfContractorDtoByContractorName() throws Exception {

		when(contractorService.getContractorDeatilsContainingName("Imagination", 0, 10, "contractorName", "ASC"))
				.thenReturn(createPageData());

		this.mockMvc.perform(get("/contractors?name=Imagination").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorId").value(101));

		verify(contractorService).getContractorDeatilsContainingName("Imagination", 0, 10, "contractorName", "ASC");
	}

	@WithMockUser("developer")
	@Test
	public void shouldReturnPageResponseOfContractorDtoIfContractorNameIsNotPresentInDB() throws Exception {

		when(contractorService.getContractorDeatils(0, 10, "contractorName", "ASC")).thenReturn(createPageData());

		this.mockMvc.perform(get("/contractors").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].contractorId").value(101))
				.andExpect(jsonPath("$.content[1].contractorId").value(201));

		verify(contractorService).getContractorDeatils(0, 10, "contractorName", "ASC");
	}

	public List<ContractorDto> getContractorDtos() {

		List<ContractorDto> contractorDtoList = new ArrayList<>();

		ContractorDto contractorDto1 = new ContractorDto();
		contractorDto1.setContractorId(101);
		contractorDto1.setContractorName("Imagination");
		ContractorDto contractorDto2 = new ContractorDto();
		contractorDto2.setContractorId(201);
		contractorDto2.setContractorName("Stable Films Pty. LTD. (Marni Baker)");

		contractorDtoList.add(contractorDto1);
		contractorDtoList.add(contractorDto2);
		return contractorDtoList;
	}

	private ContractorDto createContractorDto() {
		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setCountry("United Kingdom");
		contractorDto.setContractorId(6214l);
		contractorDto.setContractorName("Stable Films Pty. LTD. (Marni Baker)");

		return contractorDto;
	}

	private ContractorEmployeeDto createContractorEmployeeDto() {
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setContractorEmployeeName("Alex");
		contractorEmployeeDto.setEmployeeId("5000");
		contractorEmployeeDto.setKnownAs("Aliase1");

		return contractorEmployeeDto;
	}

	public ContractorEmployeeRequest createContractorEmployeeRequest() {
		ContractorEmployeeRequest contractorEmpRequest = new ContractorEmployeeRequest();
		contractorEmpRequest.setContractorEmployeeName("Akshay");
		contractorEmpRequest.setCurrencyId(203l);
		contractorEmpRequest.setDayRate(new BigDecimal(342));
		contractorEmpRequest.setKnownAs("Aliace3");
		contractorEmpRequest.setRoleId(123l);

		return contractorEmpRequest;
	}

	public ContractorRequest createContractorRequest() {
		ContractorRequest contractorRequest = new ContractorRequest();
		contractorRequest.setContactDetails("Details5");
		contractorRequest.setContractorName("CBS");
		contractorRequest.setServiceProvided("Yash");

		return contractorRequest;
	}

	public Page<ContractorDto> createPageData() {
		return new PageImpl<>(getContractorDtos(), PageRequest.of(0, 10), 2);
	}
}
