/*package com.imagination.cbs.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.service.ContractorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractorEmployeeSearchControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private ContractorService contractorServiceImpl;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDtoByRoleId() throws Exception {

		when(contractorServiceImpl.geContractorEmployeeDetailsByRoleId(Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(createPagedData());

		mockMvc.perform(get("/contractor-employees").param("role", "1000").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"))
				.andExpect(jsonPath("$.content[1].contractorEmployeeName").value("Albert"));

		verify(contractorServiceImpl, times(1)).geContractorEmployeeDetailsByRoleId(1000L, 0, 10, "roleId", "ASC");
	}

	@Test
	public void shouldReturnPageResponseOfContractorEmployeeDtoByRoleIdAndName() throws Exception {

		when(contractorServiceImpl.geContractorEmployeeDetailsByRoleIdAndName(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
						.thenReturn(createPagedData());

		mockMvc.perform(get("/contractor-employees").param("name", "Al").param("role", "1000")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].contractorEmployeeName").value("Alex"));

		verify(contractorServiceImpl, times(1)).geContractorEmployeeDetailsByRoleIdAndName(1000L, "Al", 0, 10, "roleId",
				"ASC");
	}

	@Test
	public void shouldReturnEmptyPageResponseOfContractorEmployeeDtoIfContractorNameIsNotPresentInDB()
			throws Exception {

		when(contractorServiceImpl.geContractorEmployeeDetailsByRoleIdAndName(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(
						new PageImpl<>(new ArrayList<ContractorEmployeeSearchDto>(), PageRequest.of(0, 5), 0));

		mockMvc.perform(get("/contractor-employees").param("name", "Test").param("role", "1000")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isEmpty());

		verify(contractorServiceImpl, times(1)).geContractorEmployeeDetailsByRoleIdAndName(1000L, "Test", 0, 10,
				"roleId", "ASC");
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

}*/