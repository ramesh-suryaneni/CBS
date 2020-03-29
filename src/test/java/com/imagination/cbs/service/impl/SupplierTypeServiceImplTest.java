package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.mapper.SupplierTypeMapper;
import com.imagination.cbs.repository.SupplierTypeRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupplierTypeServiceImplTest {

	@InjectMocks
	private SupplierTypeServiceImpl supplierServiceImpl;

	@Mock
	private SupplierTypeRepository supplierRepository;

	@Mock
	private SupplierTypeMapper supplierMapper;

	@Test
	public void shouldReturnListOfReasonsForRecruiting() {

		List<SupplierTypeDm> listOfSupplierTypeDM = getListOfSupplierTypeDM();
		when(supplierRepository.findAll()).thenReturn(listOfSupplierTypeDM);
		when(supplierMapper.toListOfSupplierDTO(listOfSupplierTypeDM)).thenReturn(getListOfSupplierDto());

		List<SupplierTypeDto> actualListOfSupplierDto = supplierServiceImpl.getAllSupplierTypes();
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

	private List<SupplierTypeDto> getListOfSupplierDto() {

		SupplierTypeDto supplierDto = new SupplierTypeDto();
		List<SupplierTypeDto> listOfRecruitingDto = new ArrayList<>();

		supplierDto.setId(2L);
		supplierDto.setName("Yash");
		supplierDto.setDescription("Test Data");

		listOfRecruitingDto.add(supplierDto);

		return listOfRecruitingDto;

	}

}