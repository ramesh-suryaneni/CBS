/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.MaconomyUtils;

/**
 * @author pappu.rout
 *
 */

@Service("maconomyJobNumberServiceImpl")
public class MaconomyJobNumberServiceImpl implements MaconomyService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigRepository configRepository;

	@Override
	public List<JobDataDto> getJobDetails(String jobNumber) {

		List<JobDataDto> recordList = new ArrayList<>();
		ResponseEntity<JsonNode> responseEntity = null;

		List<Config> macanomyConfigKey = configRepository.findBykeyNameStartingWith(MaconomyConstant.MACANOMY.getMacanomy());

		if (!CollectionUtils.isEmpty(macanomyConfigKey)) {

			Map<String, Config> maconomyConfigMap = macanomyConfigKey.stream()
					.collect(Collectors.toMap(Config::getKeyName, config -> config));

			if (!CollectionUtils.isEmpty(maconomyConfigMap)) {

				String maconomyUrl = maconomyConfigMap.get(MaconomyConstant.MACANOMY_URL.getMacanomy()).getKeyValue()
						+ MaconomyConstant.MACONOMY_JOB_NUMBER.getMacanomy() + jobNumber;
				String userName = maconomyConfigMap.get(MaconomyConstant.MACONOMY_USER_NAME.getMacanomy())
						.getKeyValue();
				String password = maconomyConfigMap.get(MaconomyConstant.MACONOMY_PASSWORD.getMacanomy()).getKeyValue();

				try {
					responseEntity = (ResponseEntity<JsonNode>) restTemplate.exchange(maconomyUrl, HttpMethod.GET,
							new HttpEntity<byte[]>(	MaconomyUtils.getHttpHeaders(MaconomyConstant.MEDIA_TYPE.getMacanomy(), userName, password)), JsonNode.class);

				} catch (RuntimeException runtimeException) {
					throw new ResourceNotFoundException(runtimeException.getMessage());
				}

			}
			return extractResponse(responseEntity, recordList);
		}
		return recordList;
	}

	@SuppressWarnings("unchecked")
	private List<JobDataDto> extractResponse(ResponseEntity<JsonNode> responseEntity, List<JobDataDto> jobDataDtoList) {

		JsonNode records = responseEntity.getBody().get("panes").get("card").get("records");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jobDataDtoList = (List<JobDataDto>) objectMapper.readerFor(new TypeReference<List<JobDataDto>>() {
			}).readValue(records);

			return jobDataDtoList;

		} catch (IOException ioException) {

			ioException.printStackTrace();
		}
		return jobDataDtoList;

	}

}