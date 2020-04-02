/*package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.JobDetailDto;
import com.imagination.cbs.service.MaconomyService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MaconomyControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private MaconomyService maconomyService;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
	@Test
	public void shouldReturnJobDataDtoBasedOnJobNumber() throws Exception
	{
		String jobNo="";
		when(maconomyService.getJobDetails(jobNo)).thenReturn(createJobDataDto());
		
		this.mockMvc.perform(get("/macanomy").accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.jobName", comparesEqualTo("")));
			
	}
	
	public JobDataDto createJobDataDto()
	{
		JobDataDto jobDataDto = new JobDataDto();
		jobDataDto.setData(createJobDetailDto());
		return jobDataDto;
	}
	
	public JobDetailDto createJobDetailDto()
	{
		JobDetailDto jobDetailDto = new JobDetailDto();
		jobDetailDto.setJobname("");
		jobDetailDto.setJobnumber("");
		jobDetailDto.setText3("");
		
		return jobDetailDto;
	}

}
*/