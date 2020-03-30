package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.service.DisciplineService;
import com.imagination.cbs.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@WebMvcTest(DisciplineController.class)
public class DisciplineControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mvc;

	@MockBean
	private DisciplineService disciplineService;

	
	@Before
    public void setup() {
        this.mvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
    }

	@Test
	public void shouldReturnListOfDiscipline() throws Exception {
		
		 List<DisciplineDto> listOfDisciplineDto = new
		 ArrayList<DisciplineDto>();
		 listOfDisciplineDto.add(getDisciplineDto());
		
		 when(disciplineService.getAllDisciplines()).thenReturn(listOfDisciplineDto);
		
		 mvc.perform(get("/disciplines").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		 .andExpect(jsonPath("$[0].disciplineName",
		 comparesEqualTo("Creative")));
		
		// verify(disciplineService).getAllDisciplines();
	}

	@Test
	public void shouldReturnListOfRoleBasedOnDisiplineIs() throws Exception {

		 List<ContractorRoleDto> contractorRoleDtoList = new ArrayList<>();
		
		 ContractorRoleDto contractorRoleDto = new ContractorRoleDto();
		contractorRoleDto.setDisciplineId(7000);
		contractorRoleDto.setRoleId(8000);
		 contractorRoleDto.setRoleName("Show Caller");
		
		 contractorRoleDtoList.add(contractorRoleDto);
		
		 when(disciplineService.findAllContractorRoles(7000L)).thenReturn(contractorRoleDtoList);
		
		 mvc.perform(get("/disciplines/7000/roles").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).
		 andExpect(jsonPath("$[0].roleName", comparesEqualTo("Show Caller")));
		
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
