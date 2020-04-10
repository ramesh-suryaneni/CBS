package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;
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
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.CurrencyService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CurrenciesController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CurrenciesControllerTest { 
	
	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurrencyService currencyService;
	

	@WithMockUser("/developer")
	@Test
	public void shouldReturnAllCurrencies() throws Exception {
		
		List<CurrencyDto> currencyDtoList = new ArrayList<CurrencyDto>();
		currencyDtoList.add(createCurrencyDto());
		
		when(currencyService.getAllCurrencies()).thenReturn(currencyDtoList);
		
		this.mockMvc.perform(get("/currencies").accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].currencyName",comparesEqualTo("Euros")));

	}

	public CurrencyDto createCurrencyDto()
	{
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyName("Euros");
		currencyDto.setCurrencyId("103");
		currencyDto.setCurrencyCode("EUR");
		currencyDto.setChangedDate(null);
		currencyDto.setChangedBy(null);
		
		return currencyDto;
	}
}
