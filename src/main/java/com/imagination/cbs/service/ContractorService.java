package com.imagination.cbs.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorEmployeeRequest;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.dto.ContractorRequest;


public interface ContractorService {
	
	Page<ContractorDto> getContractorDeatils(int pageNo, int pageSize, String sortingField, String sortingOrder);

	Page<ContractorDto> getContractorDeatilsContainingName(String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);

	ContractorDto getContractorByContractorId(Long id);
	
	ContractorEmployeeDto getContractorEmployeeByContractorIdAndEmployeeId(Long contractorId,Long employeeId);
	
	Map<String, Object> addContractorDetails(ContractorRequest request );
	
	ContractorEmployeeDto addContractorEmployee(Long contractorId, ContractorEmployeeRequest request);
}
