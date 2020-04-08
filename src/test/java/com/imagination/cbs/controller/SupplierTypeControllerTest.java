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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.service.SupplierTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SupplierTypeControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@MockBean
	private SupplierTypeService supplierService;

	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
	
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
