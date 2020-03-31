package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.ContractorEmployeeDto;


public interface ContractorService {

	Page<ContractorEmployeeDto> geContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeDto> geContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);

}
