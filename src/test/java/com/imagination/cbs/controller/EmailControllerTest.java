/*package com.imagination.cbs.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.http.HttpStatus;
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
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(EmailController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EmailControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmailService emailService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@WithMockUser("/developer")
	@Test
	public void shouldSentEmail() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(createMailRequest());
		MvcResult mvcResult = this.mockMvc.perform(post("/email/send")
					.content(jsonRequest)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
		assertEquals(HttpStatus.SC_OK, mvcResult.getResponse().getStatus());
	}

	private MailRequest createMailRequest(){
		
		String mail[] = {"alex@yardnine.co.uk","info@alexsteadart.com"};
		MailRequest mailRequest = new MailRequest();
		mailRequest.setMailFrom("ramesh.suryaneni");
		mailRequest.setMailTo(mail);
		mailRequest.setSubject("CBS");		
		return mailRequest;
	}
}
*/