package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.dto.SupplierDto;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

	public List<SupplierDto> toListOfSupplierDTO(List<SupplierTypeDm> listOfSupplierTypeDM);

}
