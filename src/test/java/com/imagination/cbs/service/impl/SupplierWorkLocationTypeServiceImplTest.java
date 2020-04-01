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

import com.imagination.cbs.domain.SupplierWorkLocationTypeDm;
import com.imagination.cbs.dto.SupplierWorkLocationTypeDto;
import com.imagination.cbs.mapper.SupplierWorkLocationTypeMapper;
import com.imagination.cbs.repository.SupplierWorkLocationTypeRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupplierWorkLocationTypeServiceImplTest {

	@InjectMocks
	private SupplierWorkLocationTypeServiceImpl supplierWorkLocationTypeService;

	@Mock
	private SupplierWorkLocationTypeMapper supplierWorkLocationTypeMapper;

	@Mock
	private SupplierWorkLocationTypeRepository supplierWorkLocationTypeRepository;

	@Test
	public void shouldReturnListOfAllSupplierWorkLocationTypes() {

		List<SupplierWorkLocationTypeDm> listOfSupplierWorkLocationTypes = getListOfSupplierWorkLocationType();
		when(supplierWorkLocationTypeRepository.findAll()).thenReturn(listOfSupplierWorkLocationTypes);
		when(supplierWorkLocationTypeMapper.convertToList(listOfSupplierWorkLocationTypes))
				.thenReturn(getListOfSupplierWorkLocationTypeDto());

		List<SupplierWorkLocationTypeDto> actualListOfSupplierWorkLocationTypeDto = supplierWorkLocationTypeService
				.getAllSupplierWorkLocationTypes();
		assertEquals("100", actualListOfSupplierWorkLocationTypeDto.get(0).getId());
		assertEquals("Yash", actualListOfSupplierWorkLocationTypeDto.get(0).getName());

	}

	private List<SupplierWorkLocationTypeDm> getListOfSupplierWorkLocationType() {

		List<SupplierWorkLocationTypeDm> list = new ArrayList<>();

		SupplierWorkLocationTypeDm supplier = new SupplierWorkLocationTypeDm();
		supplier.setId(100L);
		supplier.setName("Yash");

		list.add(supplier);

		return list;
	}

	private List<SupplierWorkLocationTypeDto> getListOfSupplierWorkLocationTypeDto() {

		SupplierWorkLocationTypeDto supplierDto = new SupplierWorkLocationTypeDto();
		List<SupplierWorkLocationTypeDto> listSupplierWorkLocationTypeDto = new ArrayList<>();

		supplierDto.setId("100");
		supplierDto.setName("Yash");

		listSupplierWorkLocationTypeDto.add(supplierDto);

		return listSupplierWorkLocationTypeDto;

	}

}
