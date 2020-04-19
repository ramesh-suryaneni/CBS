package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.repository.DisciplineRepository;

@RunWith(MockitoJUnitRunner.class)
public class DisciplineServiceImplTest {

	@InjectMocks
	private DisciplineServiceImpl disciplineServiceImpl;

	@Mock
	private DisciplineRepository disciplineRepository;

	@Mock
	private DisciplineMapper disciplineMapper;
	
	@Mock
	private RoleMapper roleMapper;

	@Test
	public void shouldReturnListOfDisciplines() {

		List<Discipline> listOfDscipline = getListOfDiscipline();
		when(disciplineRepository.findAll()).thenReturn(listOfDscipline);
		when(disciplineMapper.toListOfDisciplineDTO(listOfDscipline)).thenReturn(getListOfDisciplineDto());

		List<DisciplineDto> actualListOfDisciplineDto = disciplineServiceImpl.getAllDisciplines();

		assertEquals(8000, actualListOfDisciplineDto.get(0).getDisciplineId());
		assertEquals("Creative", actualListOfDisciplineDto.get(0).getDisciplineName());
		assertEquals("Client Services", actualListOfDisciplineDto.get(0).getDisciplineDescription());

	}
	
	@Test
	public void shouldReturnListOfContractorRolesWhenDisciplinesIdIsPresentInDB() {
		
		List<Discipline> listOfDscipline = getListOfDiscipline();
		
		List<RoleDto> roleDtoList = new ArrayList<>();
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId("1");
		roleDto.setRoleName("2D");
		roleDtoList.add(roleDto);
		
		when(disciplineRepository.findById(8000L)).thenReturn(Optional.of(listOfDscipline.get(0)));
		when(roleMapper.toListContractorRoleDto(listOfDscipline.get(0).getRoles())).thenReturn(roleDtoList);
		
		List<RoleDto> actual = disciplineServiceImpl.findAllContractorRoles(8000L);
		
		assertEquals("1", actual.get(0).getRoleId());
		assertEquals("2D", actual.get(0).getRoleName());
		
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenDisciplinesIdIsNotPresentInDB() {
		
		when(disciplineRepository.findById(8000L)).thenReturn(Optional.empty());
		
		disciplineServiceImpl.findAllContractorRoles(8000L);
		
	}
	


	private List<Discipline> getListOfDiscipline() {

		List<Discipline> listOfDiscipline = new ArrayList<>();

		Discipline discipline = new Discipline();
		discipline.setDisciplineId(8000L);
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
