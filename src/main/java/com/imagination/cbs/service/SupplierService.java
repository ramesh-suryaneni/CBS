package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.SupplierDto;

public interface SupplierService {

	public List<SupplierDto> getSuppliersBySupplierName(String supplierName);

}
