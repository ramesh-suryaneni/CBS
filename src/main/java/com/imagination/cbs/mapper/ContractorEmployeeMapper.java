package com.imagination.cbs.mapper;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;

@Mapper(componentModel = "spring")
public interface ContractorEmployeeMapper {

	public ContractorEmployeeSearchDto toContractorEmployeeDtoFromContractorEmployee(ContractorEmployee ContractorEmployee);
}
