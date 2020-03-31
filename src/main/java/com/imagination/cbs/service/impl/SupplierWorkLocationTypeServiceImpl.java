/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.SupplierWorkLocationTypeDto;
import com.imagination.cbs.mapper.SupplierWorkLocationTypeMapper;
import com.imagination.cbs.repository.SupplierWorkLocationTypeRepository;
import com.imagination.cbs.service.SupplierWorkLocationTypeService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Service("supplierWorkLocationTypeService")
public class SupplierWorkLocationTypeServiceImpl implements SupplierWorkLocationTypeService {
	
	@Autowired
	private SupplierWorkLocationTypeMapper supplierWorkLocationTypeMapper;
	
	@Autowired
	private SupplierWorkLocationTypeRepository supplierWorkLocationTypeRepository;

	@Cacheable("supplier_work_locations")
	@Override
	public List<SupplierWorkLocationTypeDto> getAllSupplierWorkLocationTypes() {
		return supplierWorkLocationTypeMapper.convertToList(supplierWorkLocationTypeRepository.findAll());
	}

}
