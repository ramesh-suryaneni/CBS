package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;


public interface ContractorService {
	
	Page<ContractorDto> getContractorDeatils(int pageNo, int pageSize, String sortingField, String sortingOrder);

	Page<ContractorDto> getContractorDeatilsContainingName(String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);

}
