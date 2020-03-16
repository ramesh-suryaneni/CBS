package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.dto.ReasonsForRecruitingDto;
import com.imagination.cbs.service.impl.ReasonsForRecruitingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReasonsForRecruitingController.class)
class ReasonsForRecruitingControllerTest {

	@Autowired
	private MockMvc mock;
	
	@MockBean
	private ReasonsForRecruitingServiceImpl reasonsForRecruitingServiceImpl;
	
	@Test
	public void shouldReturnListOfReasonsForRecruitingDto() throws Exception {
		
		List<ReasonsForRecruitingDto> listOfReasonsForRecruiting = new ArrayList<>();
		listOfReasonsForRecruiting.add(createRecruitingDto());
		
		when(reasonsForRecruitingServiceImpl.getReasonsForRecruiting()).thenReturn(listOfReasonsForRecruiting);
		
		mock.perform(get("/reasonsForRecruiting").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].reasonName", comparesEqualTo("Internal resource not available")));
		
		verify(reasonsForRecruitingServiceImpl).getReasonsForRecruiting();
					
	}
	
	public ReasonsForRecruitingDto createRecruitingDto()
	{
		ReasonsForRecruitingDto recruitingDto = new ReasonsForRecruitingDto();
		recruitingDto.setReasonId(1001);
		recruitingDto.setReasonName("Internal resource not available");
		
		return recruitingDto;
	}

}
