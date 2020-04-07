/*package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnContractorRoleDto() throws Exception{
		long roleId = 3214;
		
		when(roleservice.getCESToutcome(roleId)).thenReturn(createRoleDto());
		
		this.mockMvc.perform(get("/roles/3214/cestoutcome").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.roleDescription", comparesEqualTo("2D")));
		
		verify(roleservice,times(2)).getCESToutcome(roleId);
	}

	@WithMockUser("/developer")
	@Test
	public void shouldThrowExceptionRoleId_Null() throws Exception{
		
		Long roleId= null;
		
		when(roleservice.getCESToutcome(roleId)).thenThrow(new ResponseStatusException(
				HttpStatus.NOT_FOUND, "role not found :"+roleId
				));
		
		this.mockMvc.perform(get("/roles/null/cestoutcome").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	public RoleDto createRoleDto()
	{
		RoleDto roleDto = new RoleDto();
		
		roleDto.setRoleName("2D");
		roleDto.setRoleId("3214");
		roleDto.setRoleDescription("2D");
		roleDto.setInsideIr35("false");
		roleDto.setDisciplineId("0");
		roleDto.setChangedDate("2020-03-30T16:16:55.000+05:30");
		roleDto.setChangedBy("Akshay");
		roleDto.setCestDownloadLink("https://imaginationcbs.blob.core.windows.net/cbs/IR35 Example PDF outside.pdf");
		
		return roleDto;
	}
}
*/