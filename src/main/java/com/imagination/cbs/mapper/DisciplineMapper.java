package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.DisciplineDto;

@Mapper(componentModel = "spring")
public interface DisciplineMapper {

	DisciplineMapper INSTANCE = Mappers.getMapper( DisciplineMapper.class );
	public DisciplineDto toDisciplineDTO(Discipline discipline);

	public List<DisciplineDto> toListDisciplineDTO(List<Discipline> listOfDiscipline);
}
