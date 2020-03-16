package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.repository.DisciplineRepository;

@RunWith(MockitoJUnitRunner.class)
public class DisciplineServiceImplTest {

	@InjectMocks
	private DisciplineServiceImpl disciplineServiceImpl;

	@Mock
	private DisciplineRepository disciplineRepository;

	@Mock
	private DisciplineMapper disciplineMapper;

	@Test
	public void shouldReturnListOfDisciplines() {

		List<Discipline> listOfDscipline = getListOfDiscipline();
		when(disciplineRepository.findAll()).thenReturn(listOfDscipline);
		when(disciplineMapper.toListOfDisciplineDTO(listOfDscipline)).thenReturn(getListOfDisciplineDto());

		List<DisciplineDto> actualListOfDisciplineDto = disciplineServiceImpl.getAllDisciplines();

		assertEquals(8000,actualListOfDisciplineDto.get(0).getDisciplineId());
		assertEquals("Creative",actualListOfDisciplineDto.get(0).getDisciplineName());
		assertEquals("Client Services",actualListOfDisciplineDto.get(0).getDisciplineDescription());

	}

	private List<Discipline> getListOfDiscipline() {

		List<Discipline> listOfDiscipline = new ArrayList<>();

		Discipline discipline = new Discipline();
		discipline.setDisciplineId(8000);
		discipline.setDisciplineDescription("Client Services");
		discipline.setDisciplineName("Creative");

		listOfDiscipline.add(discipline);

		return listOfDiscipline;
	}

	private List<DisciplineDto> getListOfDisciplineDto() {

		DisciplineDto disciplineDto = new DisciplineDto();
		List<DisciplineDto> listOfDisciplineDto = new ArrayList<>();

		disciplineDto.setDisciplineId(8000);
		disciplineDto.setDisciplineName("Creative");
		disciplineDto.setDisciplineDescription("Client Services");

		listOfDisciplineDto.add(disciplineDto);

		return listOfDisciplineDto;

	}

}
