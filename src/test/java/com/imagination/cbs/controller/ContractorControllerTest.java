package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.service.ContractorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ContractorControllerTest {


	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@MockBean
	private ContractorService contractorService;

	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnContractorByContractorId() throws Exception {
		
		when(contractorService.getContractorByContractorId(6214l)).thenReturn(createContractorDto());
		
		this.mockMvc.perform(get("/contractors/6214").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.country", comparesEqualTo("United Kingdom")));
		
		verify(contractorService).getContractorByContractorId(6214l);
	}
	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnContractorEmployeeByContrctorIdAndEmployeeId() throws Exception
	{
		when(contractorService.getContractorEmployeeByContractorIdAndEmployeeId(6000l, 5000l)).thenReturn(createContractorEmployeeDto());
		
		this.mockMvc.perform(get("/contractors/6000/employees/5000").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.employeeName", comparesEqualTo("Alex")));
		
		verify(contractorService).getContractorEmployeeByContractorIdAndEmployeeId(6000l, 5000l);
	}
	
/*	@Test
	public void shouldReturnListOfContractorsByContractorName() throws Exception {
		
		List<ContractorDto> contractorDtos = getContractorDtos();

		when(contractorService.getContractorDeatilsContainingName("Im")).thenReturn(contractorDtos);

		mockMvc.perform(get("/contractors/Im").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].contractorName", comparesEqualTo("Imagination")));

		verify(contractorService).getContractorsByContractorName("Im");
	}

	@Test
	public void shouldReturnEmptyContractListIfContractorNameIsNotPresentInDB() throws Exception {
		List<ContractorDto> contractorDtos = new ArrayList<>();

		when(contractorService.getContractorsByContractorName(Mockito.anyString())).thenReturn(contractorDtos);

		mockMvc.perform(get("/contractors/Test").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());

		verify(contractorService).getContractorsByContractorName("Test");
	}
*/
	public List<ContractorDto> getContractorDtos() {
		List<ContractorDto> contractorDtoList = new ArrayList<>();
		ContractorDto contractorDto1 = new ContractorDto();
		contractorDto1.setContractorId(101);
		contractorDto1.setContractorName("Imagination");
		contractorDtoList.add(contractorDto1);

		return contractorDtoList;
	}
	
	public ContractorDto createContractorDto()
	{
		ContractorDto contractorDto  = new ContractorDto();
		contractorDto.setCountry("United Kingdom");
		contractorDto.setContractorId(6214l);
		contractorDto.setContractorName("Stable Films Pty. LTD. (Marni Baker)");
		
		return contractorDto;
	}
	
	public ContractorEmployeeDto createContractorEmployeeDto()
	{
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setEmployeeName("Alex");
		contractorEmployeeDto.setEmployeeId("5000");
		contractorEmployeeDto.setKnownAs("Aliase1");
		
		return contractorEmployeeDto;
	}
}
