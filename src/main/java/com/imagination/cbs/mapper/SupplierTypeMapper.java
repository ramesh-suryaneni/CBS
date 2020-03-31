package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.dto.SupplierTypeDto;

@Mapper(componentModel = "spring")
public interface SupplierTypeMapper {

	public List<SupplierTypeDto> toListOfSupplierDTO(List<SupplierTypeDm> listOfSupplierTypeDM);

	public SupplierTypeDto toSupplierTypeDtoFromSupplierTypeDomain(SupplierTypeDm supplierTypeDm);

}
