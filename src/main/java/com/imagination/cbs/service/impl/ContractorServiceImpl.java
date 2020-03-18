package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.service.ContractorService;

@Service("contractorService")
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorRepository contractorRepository;

	@Autowired
	private ContractorMapper contractorMapper;

	@Override
	public List<ContractorDto> getContractorsByContractorName(String contractorName) {

		return contractorMapper
				.toListOfContractorDto(contractorRepository.findByContractorNameContains(contractorName));

	}

}
