package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.imagination.cbs.domain.ContractorIndex;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.
  Dto;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.repository.ContractorIndexRepository;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.service.ContractorService;

@Service("contractorService")
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorRepository contractorRepository;
	
	@Autowired
	private ContractorIndexRepository contractorIndexRepository;
	
	@Autowired
	private ContractorMapper contractorMapper;

	@Override
	public List<ContractorDto> getContractorsByContractorName(String contractorName) {
		
		return contractorMapper.toListOfContractorDto(contractorRepository.findByContractorNameContains(contractorName));
	}


	@Override
	public Page<ContractorIndexDto> getContractorIndexDeatils(int pageNo, int pageSize,
			String sortingField, String sortingOrder) {
		
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorIndex> contractorIndexPage = contractorIndexRepository.findAll(pageable);
		Page<ContractorIndexDto> contractorIndexDtoPage = toContractorindexDto(contractorIndexPage);
		
		return contractorIndexDtoPage;
	}

	@Override
	public Page<ContractorIndexDto> getContractorIndexDeatilsByContractorName(String contractorName, int pageNo,
		int pageSize, String sortingField, String sortingOrder) {
		
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorIndex> contractorIndexPage = contractorIndexRepository.findByContractorNameContains(contractorName, pageable);
		Page<ContractorIndexDto> contractorIndexDtoPage = toContractorindexDto(contractorIndexPage);

		return contractorIndexDtoPage;
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
	
	private Page<ContractorIndexDto> toContractorindexDto(Page<ContractorIndex> contractorIndexPage){
		return contractorIndexPage.map((contractorIndex)->{
			ContractorIndexDto contractorIndexDto = new ContractorIndexDto();
			contractorIndexDto.setContractorName(contractorIndex.getContractorName());
			contractorIndexDto.setAlias(contractorIndex.getAlias());
			contractorIndexDto.setRole(contractorIndex.getRole());
			contractorIndexDto.setDiscipline(contractorIndex.getDiscipline());
			contractorIndexDto.setRate(contractorIndex.getRate());
			contractorIndexDto.setRating(contractorIndex.getRating());
			return contractorIndexDto;
		});
	}
}
