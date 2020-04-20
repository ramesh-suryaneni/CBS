package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.mapper.SupplierTypeMapper;
import com.imagination.cbs.repository.SupplierTypeRepository;
import com.imagination.cbs.service.SupplierTypeService;

@Service("supplierService")
public class SupplierTypeServiceImpl implements SupplierTypeService {

	@Autowired
	private SupplierTypeMapper supplierMapper;

	@Autowired
	private SupplierTypeRepository supplierRepository;

	@Override
	public List<SupplierTypeDto> getAllSupplierTypes() {

		return supplierMapper.toListOfSupplierDTO(supplierRepository.findAll());

	}

}
