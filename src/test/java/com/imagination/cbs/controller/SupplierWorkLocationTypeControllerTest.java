package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.dto.SupplierWorkLocationTypeDto;
import com.imagination.cbs.service.SupplierWorkLocationTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SupplierWorkLocationTypeControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private SupplierWorkLocationTypeService supplierWorkLocationTypeService;
	
	@Before
	public void setUp()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
							.apply(springSecurity())
							.build();
	}
	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnSupplierWorkLocationTypeDtoList() throws Exception {
		
		List<SupplierWorkLocationTypeDto> supplierWorkLocationDtoList = new ArrayList<SupplierWorkLocationTypeDto>();
		supplierWorkLocationDtoList.add(createSupplierWorkLocationDto());
		
		when(supplierWorkLocationTypeService.getAllSupplierWorkLocationTypes())
			.thenReturn(supplierWorkLocationDtoList);
		this.mockMvc.perform(get("/supplier-location-types").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", comparesEqualTo("Yash") ));
	}

	public SupplierWorkLocationTypeDto createSupplierWorkLocationDto()
	{
		SupplierWorkLocationTypeDto supplierWorkLocationTypeDto = new SupplierWorkLocationTypeDto();
		supplierWorkLocationTypeDto.setName("Yash");
		supplierWorkLocationTypeDto.setId("1");   
		supplierWorkLocationTypeDto.setChangedDateTime("2020-03-30T16:16:55.000+05:30");
		supplierWorkLocationTypeDto.setChangedBy("Amit");
		
		return supplierWorkLocationTypeDto;
	}
}
