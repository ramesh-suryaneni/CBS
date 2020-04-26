package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorEmployeeRequest;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.dto.ContractorRequest;


public interface ContractorService {
	
	Page<ContractorDto> getContractorDeatils(int pageNo, int pageSize, String sortingField, String sortingOrder);

	Page<ContractorDto> getContractorDeatilsContainingName(String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> getContractorEmployeeDetails(int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> getContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> getContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);

	ContractorDto getContractorByContractorId(Long id);
	
	ContractorEmployeeDto getContractorEmployeeByContractorIdAndEmployeeId(Long contractorId,Long employeeId);
	
	ContractorDto addContractorDetails(ContractorRequest request );
	
	ContractorEmployeeDto addContractorEmployee(Long contractorId, ContractorEmployeeRequest request);
}
