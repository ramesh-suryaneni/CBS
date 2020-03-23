package com.imagination.cbs.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.SupplierDto;
import com.imagination.cbs.mapper.SupplierMapper;
import com.imagination.cbs.repository.SupplierRepository;
import com.imagination.cbs.service.SupplierService;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	@Transactional
	public List<SupplierDto> getSuppliersBySupplierName(String supplierName) {

		return supplierMapper.toListOfSupplierDto(supplierRepository.findByNameContains(supplierName));

	}

}
