/*package com.imagination.cbs.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.dto.SupplierDto;
import com.imagination.cbs.service.SupplierService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SupplierService supplierService;

	@Test
	public void shouldReturnListOfSupplierTypeDM() throws Exception {

		when(supplierService.getAllSupplierTypeDM()).thenReturn(getSupplierDto());

		mockMvc.perform(get("/supplier").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", comparesEqualTo("Yash")));

		verify(supplierService).getAllSupplierTypeDM();
	}

	private List<SupplierDto> getSupplierDto() {

		List<SupplierDto> supplierDtoList = new ArrayList<SupplierDto>();

		SupplierDto supplier = new SupplierDto();
		supplier.setId(1);
		supplier.setName("Yash");
		supplier.setDescription("test data ");
		supplierDtoList.add(supplier);

		return supplierDtoList;
	}

}
*/