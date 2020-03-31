package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.ContractorEmployeeSearch;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.repository.ContractorEmployeeRepository;
import com.imagination.cbs.service.ContractorService;

@Service("contractorService")
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorEmployeeRepository contractorEmployeeRepository;
	
	@Override
	public Page<ContractorEmployeeDto> geContractorEmployeeDetailsByRoleId(Long roleId, int pageNo, int pageSize, String sortingField,
			String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeRepository.findByRoleId(roleId, pageable);//contractorEmployeeRepository.findAll();
		
		/*for(ContractorEmployeeSearch ce : contractorEmployeePage.getContent()) {
			System.out.println("ContractorEmpId : "+ce.getContractorEmployeeId());
			System.out.println("ContractorName : "+ce.getCompany());
			System.out.println("Rate : "+ce.getDayRate());
			System.out.println("Role : "+ce.getRole());
			System.out.println("ContractorEmployee : "+ce.getContractorEmployeeName());
			System.out.println("No Of bookings in past : "+ce.getNoOfBookingsInPast());
			System.out.println("########################################################################");
			System.out.println();
		}*/
		return toContractorEmployeeDto(contractorEmployeePage);
	}

	@Override
	public Page<ContractorEmployeeDto> geContractorEmployeeDetailsByRoleIdAndName(Long roleId, String contractorName, 
			int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeRepository.findByRoleIdAndContractorEmployeeNameContains(roleId, contractorName, pageable);//contractorEmployeeRepository.findAll();
		
		/*for(ContractorEmployeeSearch ce : contractorEmployeePage.getContent()) {
			System.out.println("ContractorEmpId : "+ce.getContractorEmployeeId());
			System.out.println("ContractorName : "+ce.getCompany());
			System.out.println("Rate : "+ce.getDayRate());
			System.out.println("Role : "+ce.getRole());
			System.out.println("ContractorEmployee : "+ce.getContractorEmployeeName());
			System.out.println("No Of bookings in past : "+ce.getNoOfBookingsInPast());
			System.out.println("########################################################################");
			System.out.println();
		}*/
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
	
	private Page<ContractorEmployeeDto> toContractorEmployeeDto(Page<ContractorEmployeeSearch> contractorEmployeePage){
		return contractorEmployeePage.map((contractorEmployeeSearched)->{
			ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
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
