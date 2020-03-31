package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.ContractorEmployeeSearch;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.repository.ContractorEmployeeSearchRepository;
import com.imagination.cbs.service.ContractorService;

@Service("contractorService")
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorEmployeeSearchRepository contractorEmployeeSearchRepository;

	@Override
	public Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField,
			String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findByRoleId(roleId, pageable);

		return toContractorEmployeeDto(contractorEmployeePage);
	}

	@Override
	public Page<ContractorEmployeeSearchDto> geContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName,
			int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findByRoleIdAndContractorEmployeeNameContains(roleId, contractorName, pageable);

		return toContractorEmployeeDto(contractorEmployeePage);
	}
	
	private Pageable createPageable(int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Sort sort = null;
		if (sortingOrder.equals("ASC")) {
			sort = Sort.by(Direction.ASC, sortingField);
		}
		if (sortingOrder.equals("DESC")) {
			sort = Sort.by(Direction.DESC, sortingField);
		}
		
		return PageRequest.of(pageNo, pageSize, sort);
	}
		
	private Page<ContractorEmployeeSearchDto> toContractorEmployeeDto(Page<ContractorEmployeeSearch> contractorEmployeePage){
		return contractorEmployeePage.map((contractorEmployeeSearched)->{
			ContractorEmployeeSearchDto contractorEmployeeDto = new ContractorEmployeeSearchDto();
			contractorEmployeeDto.setContractorEmployeeId(contractorEmployeeSearched.getContractorEmployeeId());
			contractorEmployeeDto.setContractorEmployeeName(contractorEmployeeSearched.getContractorEmployeeName());
			contractorEmployeeDto.setDayRate(contractorEmployeeSearched.getDayRate());
			contractorEmployeeDto.setRoleId(contractorEmployeeSearched.getRoleId());
			contractorEmployeeDto.setRole(contractorEmployeeSearched.getRole());
			contractorEmployeeDto.setContractorId(contractorEmployeeSearched.getContractorId());
			contractorEmployeeDto.setCompany(contractorEmployeeSearched.getCompany());
			contractorEmployeeDto.setNoOfBookingsInPast(contractorEmployeeSearched.getNoOfBookingsInPast());

			return contractorEmployeeDto;
		});
	}
}
