package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.repository.DisciplineRepository;
import com.imagination.cbs.service.DisciplineService;

@Service("disciplineService")
public class DisciplineServiceImpl implements DisciplineService{
	
	@Autowired
	private DisciplineRepository disciplineRepository;
	
	@Autowired
	private DisciplineMapper disciplineMapper;
	

    @Override
    public List<DisciplineDto> getAllDisciplines() {

        return disciplineMapper.toListOfDisciplineDTO(disciplineRepository.findAll());
        
        

    }

}
