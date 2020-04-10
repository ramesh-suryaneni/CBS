package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.JobDetailDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.MaconomyService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(MaconomyController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MaconomyControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MaconomyService maconomyService;
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnJobDataDtoBasedOnJobNumber() throws Exception
	{
		String jobNo="6000";
		when(maconomyService.getMaconomyJobNumberAndDepartmentsDetails(jobNo, createJobDataDto(), "jobNumber", "")).thenReturn(createJobDataDto());
		
		this.mockMvc.perform(get("/macanomy?jobNumber=6000").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data.jobName", comparesEqualTo("SE")));
			
	}
	
	private JobDataDto createJobDataDto()
	{
		JobDataDto jobDataDto = new JobDataDto();
		jobDataDto.setData(createJobDetailDto());
		return jobDataDto;
	}
	
	private JobDetailDto createJobDetailDto()
	{
		JobDetailDto jobDetailDto = new JobDetailDto();
		jobDetailDto.setJobname("SE");
		jobDetailDto.setJobnumber("6000");
		jobDetailDto.setText3("se");
		
		return jobDetailDto;
	}

}
