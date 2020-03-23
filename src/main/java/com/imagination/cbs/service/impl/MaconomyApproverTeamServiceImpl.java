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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.util.MaconomyRestClient;

/**
 * @author pappu.rout
 *
 */

@Service("maconomyApproverTeamService")
public class MaconomyApproverTeamServiceImpl {

	@Autowired
	private MaconomyRestClient maconomyRestClient;

	@Autowired
	private ConfigRepository configRepository;

	@SuppressWarnings("unchecked")
	public ApproverTeamDto getApproverTeamDetails(String departmentName) {

		ApproverTeamDto approverTeamDto = new ApproverTeamDto();
		ResponseEntity<JsonNode> responseEntity = null;

		List<Config> macanomyConfigKey = configRepository.findBykeyNameStartingWith(MaconomyConstant.MACANOMY.getMacanomy());

		if (!CollectionUtils.isEmpty(macanomyConfigKey)) {

			Map<String, Config> maconomyConfigMap = macanomyConfigKey.stream()
					.collect(Collectors.toMap(Config::getKeyName, config -> config));

			if (!CollectionUtils.isEmpty(maconomyConfigMap)) {

				String maconomyUrl = maconomyConfigMap.get(MaconomyConstant.MACANOMY_URL.getMacanomy()).getKeyValue()
						+ MaconomyConstant.MACANOMY_REVENUS_DEPARTMENT.getMacanomy();

				String username = maconomyConfigMap.get(MaconomyConstant.MACONOMY_USER_NAME.getMacanomy()).getKeyValue();
				String password = maconomyConfigMap.get(MaconomyConstant.MACONOMY_PASSWORD.getMacanomy()).getKeyValue();

				responseEntity = (ResponseEntity<JsonNode>) maconomyRestClient.callRestServiceForGet(maconomyUrl,username, password);

			}
			return extractResponse(responseEntity, approverTeamDto, departmentName);
		}
		return approverTeamDto;
	}

	@SuppressWarnings("unchecked")
	private ApproverTeamDto extractResponse(ResponseEntity<JsonNode> responseEntity, ApproverTeamDto approverTeamDto, String departmentName) {

		List<ApproverTeamDto> approverTeamDtoList = new ArrayList<>();

		if (null == responseEntity.getBody()) {

			throw new CBSValidationException("Please Provide Valid Department Name ");
		}

		JsonNode records = responseEntity.getBody().get("panes").get("filter").get("records");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			approverTeamDtoList = (List<ApproverTeamDto>) objectMapper.readerFor(new TypeReference<List<ApproverTeamDto>>() {
					}).readValue(records);

			approverTeamDtoList = approverTeamDtoList.stream().filter((approverTeam) -> approverTeam.getData().getName().equals(departmentName))
					.collect(Collectors.toList());

			return approverTeamDtoList.get(0);

		} catch (IOException ioException) {

			ioException.printStackTrace();
		}
		return approverTeamDto;

	}

}
