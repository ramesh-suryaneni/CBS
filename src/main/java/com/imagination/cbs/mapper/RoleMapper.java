package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.ContractorRoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );
	
	public List<ContractorRoleDto> toListContractorRoleDto(List<RoleDm> listOfRoles);

}
