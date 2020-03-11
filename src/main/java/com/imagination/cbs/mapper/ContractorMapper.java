package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.ContractorDto;

@Mapper(componentModel = "spring")
public interface ContractorMapper {
	ContractorMapper INSTANCE = Mappers.getMapper( ContractorMapper.class );
	
	public List<ContractorDto> toListContractorDto(List<Contractor> listOfContractors);

}

