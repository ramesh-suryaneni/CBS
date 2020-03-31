package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.sql.Timestamp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoleControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private RoleService roleservice;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
	
	@Test
	public void shouldReturnContractorRoleDto() throws Exception{
		long roleId = 3214;
		
		when(roleservice.getCESToutcome(roleId)).thenReturn(createContractorRoleDto());
		
		this.mockMvc.perform(get("/roles/3214/cestoutcome").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.roleDescription", comparesEqualTo("2D")));
		
		verify(roleservice,times(2)).getCESToutcome(roleId);
	}

	@Test
	public void shouldThrowExceptionRoleId_Null() throws Exception{
		
		Long roleId= null;
		
		when(roleservice.getCESToutcome(roleId)).thenThrow(new ResponseStatusException(
				HttpStatus.NOT_FOUND, "role not found :"+roleId
				));
		
		this.mockMvc.perform(get("/roles/null/cestoutcome").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	public ContractorRoleDto createContractorRoleDto()
	{
		ContractorRoleDto contractorRoleDto = new ContractorRoleDto();
		contractorRoleDto.setRoleName("2D");
		contractorRoleDto.setRoleId(3214);
		contractorRoleDto.setRoleDescription("2D");
		contractorRoleDto.setInsideIr35(false);
		contractorRoleDto.setDisciplineId(0);
		contractorRoleDto.setChangedDate(new Timestamp(2020, 03, 17, 02, 32, 03, 767));
		contractorRoleDto.setChangedBy("Akshay");
		contractorRoleDto.setCestDownloadLink("https://imaginationcbs.blob.core.windows.net/cbs/IR35 Example PDF outside.pdf");
		
		return contractorRoleDto;
	}
}
