package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.service.impl.DisciplineServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DisciplineController.class)
public class DisciplineControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DisciplineServiceImpl disciplineServiceImpl;

	@Test
	public void shouldReturnListOfDiscipline() throws Exception {
		List<DisciplineDto> listOfDisciplineDto = new ArrayList<DisciplineDto>();
		listOfDisciplineDto.add(getDisciplineDto());

		when(disciplineServiceImpl.getAllDisciplines()).thenReturn(listOfDisciplineDto);

		mvc.perform(get("/disciplines").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].disciplineName", comparesEqualTo("Creative")));

		verify(disciplineServiceImpl).getAllDisciplines();
	}

	private DisciplineDto getDisciplineDto() {
		DisciplineDto disciplineDto = new DisciplineDto();

		disciplineDto.setDisciplineId(8000);
		disciplineDto.setDisciplineName("Creative");
		disciplineDto.setDisciplineDescription("This is Creative");

		return disciplineDto;
	}
}
