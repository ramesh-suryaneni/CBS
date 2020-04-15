package com.imagination.cbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.exception.ResourceNotFoundException;
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
	@Cacheable("disciplines")
	public List<DisciplineDto> getAllDisciplines() {

		return disciplineMapper.toListOfDisciplineDTO(disciplineRepository.findAll());

	}

	@Override
	@Cacheable("roles")
	public List<RoleDto> findAllContractorRoles(Long disciplineId) {

		Optional<Discipline> discpline = disciplineRepository.findById(disciplineId);

		if (!discpline.isPresent()) {
			throw new ResourceNotFoundException("Discpline Not Found with Id:- " + disciplineId);
		}
		
		Discipline discipline = discpline.get();

		return roleMapper.toListContractorRoleDto(discipline.getRoles());
	}

}
