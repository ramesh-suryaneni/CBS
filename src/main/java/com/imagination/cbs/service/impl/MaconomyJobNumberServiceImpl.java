/**
 * 
 *//*
package com.imagination.cbs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.MaconomyRestClient;

*//**
 * @author pappu.rout
 *
 *//*

@Service("maconomyService")
public class MaconomyJobNumberServiceImpl implements MaconomyService {

	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private MaconomyRestClient maconomyRestClient;
	
	@SuppressWarnings("unchecked")
	@Override
	public JobDataDto getJobDetails(String jobNumber) {
		
		ResponseEntity<JsonNode> responseEntity = null;
		JobDataDto jobDataDto = new JobDataDto();
		
		List<Config> macanomyConfigKey = configRepository.findBykeyNameStartingWith(MaconomyConstant.MACANOMY.getMacanomy());

		if (!CollectionUtils.isEmpty(macanomyConfigKey)) {

			Map<String, Config> maconomyConfigMap = macanomyConfigKey.stream().collect(Collectors.toMap(Config::getKeyName, config -> config));

			if (!CollectionUtils.isEmpty(maconomyConfigMap)) {

				String maconomyUrl = maconomyConfigMap.get(MaconomyConstant.MACANOMY_URL.getMacanomy()).getKeyValue()
						+ MaconomyConstant.MACONOMY_JOB_NUMBER.getMacanomy() + jobNumber;
				String username = maconomyConfigMap.get(MaconomyConstant.MACONOMY_USER_NAME.getMacanomy())
						.getKeyValue();
				String password = maconomyConfigMap.get(MaconomyConstant.MACONOMY_PASSWORD.getMacanomy()).getKeyValue();

					responseEntity = (ResponseEntity<JsonNode>) maconomyRestClient.callRestServiceForGet(maconomyUrl, username, password);
			}
			return extractResponse(responseEntity, jobDataDto);
		}
		return jobDataDto;
	}

	@SuppressWarnings("unchecked")
	private JobDataDto extractResponse(ResponseEntity<JsonNode> responseEntity, JobDataDto jobDataDto) {
		
		List<JobDataDto> jobDataDtoList = new ArrayList<>();
		
		if(null == responseEntity.getBody()){
			
			 throw new CBSValidationException("Please Provide Valid Job Number ");
		}
		
		JsonNode records = responseEntity.getBody().get("panes").get("card").get("records");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jobDataDtoList = (List<JobDataDto>) objectMapper.readerFor(new TypeReference<List<JobDataDto>>() {
			}).readValue(records);
			
			jobDataDto = jobDataDtoList.get(0);
			
			return jobDataDto;

		} catch (IOException ioException) {

			ioException.printStackTrace();
		}
		return jobDataDto;

	}

}
*/