package com.imagination.cbs.controller;


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.InternalResourceEmailDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(InternalResourceEmailController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class InternalResourceEmailControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;

	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;

	@MockBean
	private RestTemplateBuilder restTemplateBuilder;

	@MockBean
	private EmailService emailServiceImpl;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@WithMockUser("developer")
	@Test
	public void shouldSendEmailToInternalResource() throws Exception {
		
		String jsonRequest = objectMapper.writeValueAsString(createInternalResourceEmailDto());
		
		this.mockMvc
		.perform(post("/internal-resource-email").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();

		verify(emailServiceImpl).sendInternalResourceEmail(createInternalResourceEmailDto());
	}
	
	private InternalResourceEmailDto createInternalResourceEmailDto() {
		InternalResourceEmailDto internalResourceEmailDto = new InternalResourceEmailDto();
		internalResourceEmailDto.setJobName("11111");
		internalResourceEmailDto.setJobName("Creator");
		internalResourceEmailDto.setRole("2D");
		return internalResourceEmailDto;
	}

}
