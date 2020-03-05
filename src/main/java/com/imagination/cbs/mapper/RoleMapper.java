package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.model.Role;

@Mapper
public interface RoleMapper {
	Role toRoleDTO(RoleDm roleDm);
	
	List<Role> toRoleDTOs(List<RoleDm> roleDms);
}
