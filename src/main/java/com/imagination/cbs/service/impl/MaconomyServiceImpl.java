/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.MaconomyRestClient;

/**
 * @author pappu.rout
 * @param <T>
 *
 */

@Service("maconomyService")
public class MaconomyServiceImpl implements MaconomyService {
	
	@Autowired
	private MaconomyRestClient maconomyRestClient;

	@Autowired
	private ConfigRepository configRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MaconomyServiceImpl.class);
	
	@Override
	public <T> T getMaconomyJobNumberAndDepartmentsDetails(String jobNumber, T maconomyDto, String isJobNumberOrDepartmentName, String departname){
		
		ResponseEntity<JsonNode> responseEntity = null;
		
		
		List<Config> macanomyConfigKey = configRepository.findBykeyNameStartingWith(MaconomyConstant.MACANOMY.getMacanomy());

		if (!CollectionUtils.isEmpty(macanomyConfigKey)) {

			Map<String, Config> maconomyConfigMap = macanomyConfigKey.stream().collect(Collectors.toMap(Config::getKeyName, config -> config));

			if (!CollectionUtils.isEmpty(maconomyConfigMap)) {

						
				String maconomyDepartmentUrl = maconomyConfigMap.get(MaconomyConstant.MACANOMY_URL.getMacanomy()).getKeyValue()
						+ MaconomyConstant.MACANOMY_REVENUS_DEPARTMENT.getMacanomy();
				
				String maconomyJobUrl = maconomyConfigMap.get(MaconomyConstant.MACANOMY_URL.getMacanomy()).getKeyValue()
						+ MaconomyConstant.MACONOMY_JOB_NUMBER.getMacanomy() + jobNumber;
				
				String username = maconomyConfigMap.get(MaconomyConstant.MACONOMY_USER_NAME.getMacanomy())
						.getKeyValue();
				String password = maconomyConfigMap.get(MaconomyConstant.MACONOMY_PASSWORD.getMacanomy()).getKeyValue();
				
				String maconomyUrl = isJobNumberOrDepartmentName.equalsIgnoreCase("jobNumber") ? maconomyJobUrl : maconomyDepartmentUrl;
				try{
					responseEntity = maconomyRestClient.callRestServiceForGet(maconomyUrl, username, password);
				}catch(RuntimeException runtimeException){
					throw new ResourceNotFoundException("Plase provide valid JobNumber or DepartmentName ");
				}
			}
			if(responseEntity == null) {
				throw new NullPointerException("Response Entity is null");
			}else {
			return extractResponse(responseEntity, (T) maconomyDto, isJobNumberOrDepartmentName, departname);
			}
		}
		return maconomyDto;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T extractResponse(ResponseEntity<JsonNode> responseEntity, T approverTeamDto, String isJobNumberOrDepartmentName,String departmentName) {

		if (responseEntity.getBody() == null) {

			throw new CBSApplicationException("Please Provide Valid Department Name ");
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if(isJobNumberOrDepartmentName.equalsIgnoreCase("Department")){
			JsonNode departmentRecords = responseEntity.getBody().get("panes").get("filter").get("records");
			List<ApproverTeamDto> approverTeamDtoList = objectMapper.readerFor(new TypeReference<List<ApproverTeamDto>>() {
					}).readValue(departmentRecords);

			approverTeamDtoList = approverTeamDtoList.stream().filter(approverTeam -> approverTeam.getData().getName().equals(departmentName))
					.collect(Collectors.toList());

			return (T) approverTeamDtoList.get(0);
			
			}else if(isJobNumberOrDepartmentName.equalsIgnoreCase("jobNumber")){
				
				JsonNode jobNumberRecord = responseEntity.getBody().get("panes").get("card").get("records");
				List<JobDataDto> jobDataDtoList = objectMapper.readerFor(new TypeReference<List<JobDataDto>>() {
				}).readValue(jobNumberRecord);
				
				return (T) jobDataDtoList.get(0);
				
				
			}
		} catch (IOException ioException) {

			LOGGER.error("Exception occured during extracting a response for MaconomyJobNumber and Department details", ioException);
		}
		return approverTeamDto;
		
	}


}
