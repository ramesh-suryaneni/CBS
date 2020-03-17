package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.ContractorDto;

@Mapper(componentModel = "spring")
public interface ContractorMapper {
	
	public List<ContractorDto> toListOfContractorDto(List<Contractor> listOfContractors);

}

