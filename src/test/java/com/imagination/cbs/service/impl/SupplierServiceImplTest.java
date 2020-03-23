package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.dto.SupplierDto;
import com.imagination.cbs.mapper.SupplierMapper;
import com.imagination.cbs.repository.SupplierRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupplierServiceImplTest {

	@InjectMocks
	private SupplierServiceImpl supplierServiceImpl;

	@Mock
	private SupplierRepository supplierRepository;

	@Mock
	private SupplierMapper supplierMapper;

	@Test
	@Ignore
	public void shouldReturnListOfReasonsForRecruiting() {

		when(supplierRepository.findAll()).thenReturn(getListOfSupplierTypeDM());
		when(supplierMapper.toListOfSupplierDTO(getListOfSupplierTypeDM())).thenReturn(getListOfSupplierDto());

		List<SupplierDto> actualListOfSupplierDto = supplierServiceImpl.getAllSupplierTypeDM();
		assertEquals(2L, actualListOfSupplierDto.get(0).getId());
		assertEquals("Yash", actualListOfSupplierDto.get(0).getName());
		assertEquals("Test Data", actualListOfSupplierDto.get(0).getDescription());

	}

	private List<SupplierTypeDm> getListOfSupplierTypeDM() {

		List<SupplierTypeDm> list = new ArrayList<>();

		SupplierTypeDm supplier = new SupplierTypeDm();
		supplier.setId(2L);
		supplier.setName("Yash");
		supplier.setDescription("Test Data");

		list.add(supplier);

		return list;
	}

	private List<SupplierDto> getListOfSupplierDto() {

		SupplierDto supplierDto = new SupplierDto();
		List<SupplierDto> listOfRecruitingDto = new ArrayList<>();

		supplierDto.setId(2L);
		supplierDto.setName("Specific skills required");
		supplierDto.setDescription("Specific skills required");

		listOfRecruitingDto.add(supplierDto);

		return listOfRecruitingDto;

	}

}
