package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.SupplierDto;
import com.imagination.cbs.mapper.SupplierMapper;
import com.imagination.cbs.repository.SupplierRepository;
import com.imagination.cbs.service.SupplierService;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<SupplierDto> getAllSupplierTypeDM() {

		return supplierMapper.toListOfSupplierDTO(supplierRepository.findAll());

	}

	/*@Override
	public List<SupplierDto> getSuppliersBySupplierName(String name) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
