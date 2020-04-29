package com.imagination.cbs.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.BookingService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(WebhookController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class WebhookControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;

	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;

	@MockBean
	private BookingService bookingService;
	
	@MockBean
	private AdobeSignService adobeSignService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@WithMockUser("developer")
	@Test
	public void shouldUpdateContractBasedOnAgreementAndEventDate() throws Exception {
		Map<String,Long> contract = new HashMap<>();
		contract.put("id", 3333L);
		Map<String, Object> payload = new HashMap<>();
		payload.put("agreement", contract);
		payload.put("eventDate", "29/4/2020");
		JsonNode payloadNode = new ObjectMapper().valueToTree(payload);
	    String jsonRequest = new ObjectMapper().writeValueAsString(payloadNode);
	    
		this.mockMvc.perform(post("/webhooks/adobesign-callback").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		verify(bookingService).updateContract("3333", "29/4/2020");
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnResponseForWebhookVerification() throws Exception {
		
		this.mockMvc.perform(get("/webhooks/adobesign-callback").header("code", "123edswd"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnSuccessForSaveUpdateAuthCode() throws Exception {
		
		this.mockMvc.perform(get("/webhooks/code-callback")
				.param("code", "1002").param("client_id", "1003")
				.param("api_access_point", "imagination.com").param("web_access_point", "cbs.com")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(adobeSignService).saveOrUpdateAuthCode("1002", "imagination.com", "cbs.com");
	}

}
