package com.imagination.cbs.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorIndexDto;


public interface ContractorService {

	List<ContractorDto> getContractorsByContractorName(String contractorName);
	
	Page<ContractorIndexDto> getContractorIndexDeatils(int pageNo, int pageSize, String sortingField, String sortingOrder);
	
	Page<ContractorIndexDto> getContractorIndexDeatilsByContractorName(String contractorName, int pageNo, int pageSize, String sortingField, String sortingOrder);
}
