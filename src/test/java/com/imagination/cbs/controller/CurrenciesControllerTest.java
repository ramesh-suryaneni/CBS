package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.service.CurrencyService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CurrenciesControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private CurrencyService currencyService;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
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
