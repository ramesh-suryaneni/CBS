package com.imagination.cbs.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.ContractorRoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	public List<ContractorRoleDto> toListContractorRoleDto(List<RoleDm> listOfRoles);
	
	public ContractorRoleDto toRoleDTO(RoleDm roledm);

}
