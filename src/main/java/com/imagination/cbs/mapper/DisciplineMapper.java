package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.DisciplineDto;

@Mapper(componentModel = "spring")
public interface DisciplineMapper {
	
	public List<DisciplineDto> toListOfDisciplineDTO(List<Discipline> listOfDiscipline);

}
