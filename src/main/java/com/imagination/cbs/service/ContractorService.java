package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.ContractorDto;


public interface ContractorService {

	List<ContractorDto> getContractorsContainingName(String contractorName);
}
