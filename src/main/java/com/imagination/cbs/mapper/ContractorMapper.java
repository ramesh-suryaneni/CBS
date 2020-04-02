package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorRequest;

@Mapper(componentModel = "spring")
public interface ContractorMapper {

	public List<ContractorDto> toListOfContractorDto(List<Contractor> listOfContractors);

	public ContractorDto toContractorDtoFromContractorDomain(Contractor contractor);

	@Mapping(source = "serviceProvided", target = "companyType")
	public Contractor toContractorDomainFromContractorRequest(ContractorRequest contractorRequest);
}
