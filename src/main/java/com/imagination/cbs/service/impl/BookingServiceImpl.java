/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.DisciplineRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.service.BookingService;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private DisciplineRepository disciplineRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookingMapper bookingMapper;

	@Override
	public List<DisciplineDto> getAllDisciplines() {

		return bookingMapper.toListDisciplineDTO(disciplineRepository.findAll());

	}

	@Override
	public List<ContractorRoleDto> getAllContractorRoles(Long disciplineId) {
		
		return bookingMapper.toListContractorRoleDto(roleRepository.findRoleByDisciplineId(disciplineId));
		
		
	}

}
