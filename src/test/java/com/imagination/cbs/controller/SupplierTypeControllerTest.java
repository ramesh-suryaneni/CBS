package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.SupplierTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SupplierTypeController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SupplierTypeControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SupplierTypeService supplierService;

	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnListOfSupplierTypeDto() throws Exception {

		when(supplierService.getAllSupplierTypes()).thenReturn(getSupplierTypeDto());

		mockMvc.perform(get("/supplier-types").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", comparesEqualTo("Yash")));

		verify(supplierService).getAllSupplierTypes();
	}

	private List<SupplierTypeDto> getSupplierTypeDto() {

		List<SupplierTypeDto> supplierDtoList = new ArrayList<SupplierTypeDto>();

		SupplierTypeDto supplierType = new SupplierTypeDto();
		supplierType.setId(1);
		supplierType.setName("Yash");
		supplierType.setDescription("test data ");
		supplierDtoList.add(supplierType);

		return supplierDtoList;
	}

}
