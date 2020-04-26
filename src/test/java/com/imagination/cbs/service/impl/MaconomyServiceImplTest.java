package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.ApproverTeamDetailDto;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.JobDetailDto;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.util.MaconomyRestClient;

@RunWith(MockitoJUnitRunner.class)
public class MaconomyServiceImplTest {

	@InjectMocks
	private MaconomyServiceImpl maconomyServiceImpl;
	
	@Mock
	private MaconomyRestClient maconomyRestClient;

	@Mock
	private ConfigRepository configRepository;

	private ResponseEntity<JsonNode> responseEntity;
	private List<Config> configList;
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		responseEntity = (ResponseEntity<JsonNode>) mock(ResponseEntity.class);
		configList = getConfigList();
	}
	
	@Test
	public void shouldReturnDtoSentAsParameter() {

		JobDataDto jobDataDto = createJobDataDto();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(null);
		
		JobDataDto actual = maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "jobNumber", "testDepartment");
		
		verify(configRepository).findBykeyNameStartingWith("MACONOMY");
		
		assertNotNull(actual);
		assertEquals("SE", actual.getData().getJobName());
	}

	@Test
	public void shouldReturnMaconomyJobNumberDetails() throws JsonMappingException, JsonProcessingException {
		
		JobDataDto jobDataDto = createJobDataDto();
		JsonNode jsonNode = createJsonNode("jobNumber");
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		when(maconomyRestClient.callRestServiceForGet("https://maconomy.base.urljobs/data;jobnumber=1000", "username", "password")).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(jsonNode);

		JobDataDto actual = maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "jobNumber", "RevenueDepartment");
		
		verify(configRepository).findBykeyNameStartingWith("MACONOMY");
		verify(maconomyRestClient).callRestServiceForGet("https://maconomy.base.urljobs/data;jobnumber=1000", "username", "password");
		verify(responseEntity, times(2)).getBody();
		
		assertNotNull(actual);
		assertEquals("Billable Template", actual.getData().getJobName());
		assertEquals("Billable Template:123", actual.getData().getJobNumber());
	}

	@Test
	public void shouldReturnDepartmentDetails() throws JsonMappingException, JsonProcessingException {

		ApproverTeamDto approverTeamDto = createApproverTeamDto();
		JsonNode jsonNode = createJsonNode("Department");
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		String maconomyUrl = "https://maconomy.base.url/find_theoption/filter?fields=name,remark3&restriction=optionlistnumber = "
				+ "\"JobRevenueDepartment\"&limit=1000";
		when(maconomyRestClient.callRestServiceForGet(maconomyUrl, "username", "password")).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(jsonNode);

		ApproverTeamDto actual = maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", approverTeamDto, "Department", "JobRevenueDepartment");
		
		verify(configRepository).findBykeyNameStartingWith("MACONOMY");
		verify(maconomyRestClient).callRestServiceForGet(maconomyUrl, "username", "password");
		verify(responseEntity, times(2)).getBody();
		
		assertNotNull(actual);
		assertEquals("JobRevenueDepartment", actual.getData().getName());
		assertEquals("TestRemark", actual.getData().getRemark3());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThorwResourceNotFoundExceptionWhenRuntimeExceptionDuringMaconomyRestClientCall() {
		
		JobDataDto jobDataDto = createJobDataDto();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		when(maconomyRestClient.callRestServiceForGet("https://maconomy.base.urljobs/data;jobnumber=1000", 
				"username", "password")).thenThrow(RuntimeException.class);
		
		maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "jobNumber", "testDepartment");
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPoniterExceptionWhenResponseFromMaconomyRestClientIsNull() {
		
		JobDataDto jobDataDto = createJobDataDto();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		when(maconomyRestClient.callRestServiceForGet("https://maconomy.base.urljobs/data;jobnumber=1000", 
				"username", "password")).thenReturn(null);
		
		maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "jobNumber", "testDepartment");
	}

	@Test
	public void shouldReturnDtoSentAsParameterAfterExtractingResponseCall() throws JsonMappingException, JsonProcessingException {
		
		JobDataDto jobDataDto = createJobDataDto();
		JsonNode jsonNode = createDummyJsonNode();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		
		String maconomyUrl = "https://maconomy.base.url/find_theoption/filter?fields=name,remark3&restriction=optionlistnumber "
				+ "= \"JobRevenueDepartment\"&limit=1000";
		when(maconomyRestClient.callRestServiceForGet(maconomyUrl,"username", "password")).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(jsonNode);
		
		JobDataDto actual = maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "test", "testDepartment");
		
		verify(configRepository).findBykeyNameStartingWith("MACONOMY");
		verify(maconomyRestClient).callRestServiceForGet(maconomyUrl,"username", "password");
		verify(responseEntity).getBody();
		
		assertNotNull(actual);
		assertEquals("SE", actual.getData().getJobName());
	}
	
	@Test(expected = CBSApplicationException.class)
	public void shouldThrowCBSApplicationExceptionWhenResponseBodyIdNull_extractResponse() {
		
		JobDataDto jobDataDto = createJobDataDto();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		
		String maconomyUrl = "https://maconomy.base.url/find_theoption/filter?fields=name,remark3&restriction=optionlistnumber "
				+ "= \"JobRevenueDepartment\"&limit=1000";
		when(maconomyRestClient.callRestServiceForGet(maconomyUrl,"username", "password")).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(null);
		
		maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", jobDataDto, "test", "testDepartment");
	}
	
	@Test
	public void shouldReturnSentDtoIOExceptionOccuredWhileReadingValueFromObjectMapper_extractResponse() throws Exception{
		
		ApproverTeamDto approverTeamDto = createApproverTeamDto();
		JsonNode jsonNode = createDummyJsonNode();
		
		when(configRepository.findBykeyNameStartingWith("MACONOMY")).thenReturn(configList);
		
		String maconomyUrl = "https://maconomy.base.url/find_theoption/filter?fields=name,remark3&restriction=optionlistnumber "
				+ "= \"JobRevenueDepartment\"&limit=1000";
		when(maconomyRestClient.callRestServiceForGet(maconomyUrl,"username", "password")).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(jsonNode);
		
		ApproverTeamDto actual = maconomyServiceImpl.getMaconomyJobNumberAndDepartmentsDetails("1000", approverTeamDto, "Department", "testDepartment");
		
		verify(configRepository).findBykeyNameStartingWith("MACONOMY");
		verify(maconomyRestClient).callRestServiceForGet(maconomyUrl,"username", "password");
		verify(responseEntity, times(2)).getBody();
		
		assertEquals("JobRevenueDepartment", actual.getData().getName());
		assertEquals("TestRemark", actual.getData().getRemark3());
	}
	
	private List<Config> getConfigList() {
		List<Config> configList = new ArrayList<>();

		Config c1 = new Config();
		c1.setKeyName("MACONOMY_BASE_URL");
		c1.setKeyValue("https://maconomy.base.url");
		configList.add(c1);

		Config c2 = new Config();
		c2.setKeyName("MACONOMY_USERNAME");
		c2.setKeyValue("username");
		configList.add(c2);
		
		Config c3 = new Config();
		c3.setKeyName("MACONOMY_PASSWORD");
		c3.setKeyValue("password");
		configList.add(c3);
		
		return configList;
	}
	
	private JobDataDto createJobDataDto()
	{
		JobDetailDto jobDetailDto = new JobDetailDto();
		jobDetailDto.setJobname("SE");
		jobDetailDto.setJobnumber("6000");
		jobDetailDto.setText3("se");
		
		JobDataDto jobDataDto = new JobDataDto();
		jobDataDto.setData(jobDetailDto);
		return jobDataDto;
	}
	

	private ApproverTeamDto createApproverTeamDto() {
		ApproverTeamDetailDto approverTeamDetailDto = new ApproverTeamDetailDto();
		approverTeamDetailDto.setName("JobRevenueDepartment");
		approverTeamDetailDto.setRemark3("TestRemark");

		ApproverTeamDto approverTeamDto = new ApproverTeamDto();
		approverTeamDto.setData(approverTeamDetailDto);
		return approverTeamDto;
	}

	private JsonNode createJsonNode(String jsonNodeFor) throws JsonMappingException, JsonProcessingException {

		String jsonStr = "{\"panes\": {";
		if(jsonNodeFor.equals("Department")) {
			jsonStr += " \"filter\": { \"records\": [";
			jsonStr +=  " { \"data\": { \"name\": \"JobRevenueDepartment\", \"remark3\": \"TestRemark\"";
		}
		else if(jsonNodeFor.equals("jobNumber")) {
			jsonStr += " \"card\": { \"records\": [";
			jsonStr += " { \"data\": { \"jobname\" : \"Billable Template\", \"jobnumber\" : \"Billable Template:123\", \"text3\" : \"\"";
		}
		
		jsonStr+="} } ] } } }";
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonStr);
	}
	
	private JsonNode createDummyJsonNode() throws JsonMappingException, JsonProcessingException {

		String jsonStr = "{\"panes\": {";
		jsonStr += " \"filter\": { \"records\": [ { \"data\" : \"Test\"";
		jsonStr+="} ] } } }";

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonStr);
	}

}
