package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
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

import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.service.RecruitingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecruitingControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@MockBean
	private RecruitingService recruitingService;

	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
    }
	
	@Test
	public void shouldReturnListOfReasonsForRecruiting() throws Exception {

		when(recruitingService.getAllReasonForRecruiting()).thenReturn(getRecruitingDto());

		mockMvc.perform(get("/recruiting").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].reasonName", comparesEqualTo("Specific skills required")));

		verify(recruitingService).getAllReasonForRecruiting();
	}

	private List<RecruitingDto> getRecruitingDto() {

		List<RecruitingDto> listOfRecruitingDto = new ArrayList<RecruitingDto>();

		RecruitingDto recruitingDto = new RecruitingDto();
		recruitingDto.setReasonId(1);
		recruitingDto.setReasonName("Specific skills required");
		recruitingDto.setReasonDescription("Internal resource not available");
		listOfRecruitingDto.add(recruitingDto);

		return listOfRecruitingDto;
	}

}
