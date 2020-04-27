package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RoleController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class RoleControllerTest {
	
	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;

	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RoleService roleservice;
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnRoleDtoBasedOnRoleIdForGetRoleCESToutcome() throws Exception{
		
		when(roleservice.getCESToutcome(3214L)).thenReturn(createRoleDto());
		
		this.mockMvc.perform(get("/roles/3214/cestoutcome").contentType(MediaType.APPLICATION_JSON)) 
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.roleDescription", comparesEqualTo("2D")));
		
		verify(roleservice,times(2)).getCESToutcome(3214L);
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldThrowResponseStatusExceptionWhenRoleDtoIsNullForGetRoleCESToutcome() throws Exception{
		
		when(roleservice.getCESToutcome(3214L)).thenReturn(null); 
		
		this.mockMvc.perform(get("/roles/3214/cestoutcome").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound());
		
		verify(roleservice).getCESToutcome(3214L);
	}
	
	public RoleDto createRoleDto(){
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName("2D");
		roleDto.setRoleId("3214");
		roleDto.setRoleDescription("2D");
		roleDto.setInsideIr35("false");
		return roleDto;
	}
}
