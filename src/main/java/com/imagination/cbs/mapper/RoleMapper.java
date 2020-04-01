package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	public List<RoleDto> toListContractorRoleDto(List<RoleDm> listOfRoles);

	public RoleDto toRoleDTO(RoleDm roledm);

}
