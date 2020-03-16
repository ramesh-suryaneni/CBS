package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;

public interface DisciplineService {
	
	public List<DisciplineDto> getAllDisciplines();

	List<ContractorRoleDto> findAllContractorRoles(Long disciplineId);

}
