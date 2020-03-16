package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {

	public List<DisciplineDto> toListDisciplineDTO(List<Discipline> listOfDiscipline);

	public List<ContractorRoleDto> toListContractorRoleDto(List<RoleDm> listOfRoles);

}
