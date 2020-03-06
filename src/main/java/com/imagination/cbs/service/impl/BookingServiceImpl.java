/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.repository.DisciplineRepository;
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
    DisciplineMapper disciplineMapper;

	@Override
	public List<DisciplineDto> getAllDisciplines() {
		
		List<DisciplineDto> listOfDisciplineDto = disciplineMapper.toListDisciplineDTO(disciplineRepository.findAll());

		return listOfDisciplineDto;
	}

	
}
