package com.imagination.cbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.repository.DisciplineRepository;
import com.imagination.cbs.service.DisciplineService;

@Service("disciplineService")
public class DisciplineServiceImpl implements DisciplineService {

	@Autowired
	private DisciplineRepository disciplineRepository;

	@Autowired
	private DisciplineMapper disciplineMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<DisciplineDto> getAllDisciplines() {

		return disciplineMapper.toListOfDisciplineDTO(disciplineRepository.findAll());

	}

	@Override
	public List<ContractorRoleDto> findAllContractorRoles(Long disciplineId) {
		
		Optional<Discipline> findById = disciplineRepository.findById(disciplineId);
		
		Discipline discipline = findById.get();
		
		
		return roleMapper.toListContractorRoleDto(discipline.getRoles());
	}

}
