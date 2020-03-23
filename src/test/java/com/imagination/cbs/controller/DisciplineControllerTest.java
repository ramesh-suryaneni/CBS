/*package com.imagination.cbs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.service.DisciplineService;
import com.imagination.cbs.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DisciplineController.class)
public class DisciplineControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DisciplineService disciplineService;

	@MockBean
	private RoleService roleService;

	@Test
	public void shouldReturnListOfDiscipline() throws Exception {
		//
		// List<DisciplineDto> listOfDisciplineDto = new
		// ArrayList<DisciplineDto>();
		// listOfDisciplineDto.add(getDisciplineDto());
		//
		// when(disciplineService.getAllDisciplines()).thenReturn(listOfDisciplineDto);
		//
		// mvc.perform(get("/disciplines").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		// .andExpect(jsonPath("$[0].disciplineName",
		// comparesEqualTo("Creative")));
		//
		// verify(disciplineService).getAllDisciplines();
	}

	@Test
	public void shouldReturnListOfRoleBasedOnDisiplineIs() throws Exception {

		// List<ContractorRoleDto> contractorRoleDtoList = new ArrayList<>();
		//
		// ContractorRoleDto contractorRoleDto = new ContractorRoleDto();
		// contractorRoleDto.setDisciplineId(7000);
		// contractorRoleDto.setRoleId(8000);
		// contractorRoleDto.setRoleName("Show Caller");
		//
		// contractorRoleDtoList.add(contractorRoleDto);
		//
		// when(roleService.findAllContractorRoles(7000L)).thenReturn(contractorRoleDtoList);
		//
		// mvc.perform(get("/disciplines/7000/roles").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).
		// andExpect(jsonPath("$[0].roleName", comparesEqualTo("Show Caller")));
		//
		// verify(roleService).findAllContractorRoles(7000L);
	}

	private DisciplineDto getDisciplineDto() {
		DisciplineDto disciplineDto = new DisciplineDto();

		disciplineDto.setDisciplineId(8000);
		disciplineDto.setDisciplineName("Creative");
		disciplineDto.setDisciplineDescription("This is Creative");

		return disciplineDto;
	}
}
*/