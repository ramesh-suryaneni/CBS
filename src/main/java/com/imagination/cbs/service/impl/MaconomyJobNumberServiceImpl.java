/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

/**
 * @author pappu.rout
 *
 */

@Service("maconomyService")
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
				String userName = maconomyConfigMap.get(MaconomyConstant.MACONOMY_USER_NAME.getMacanomy()).getKeyValue();
				String password = maconomyConfigMap.get(MaconomyConstant.MACONOMY_PASSWORD.getMacanomy()).getKeyValue();

				try {
					responseEntity = (ResponseEntity<JsonNode>) restTemplate.exchange(maconomyUrl, HttpMethod.GET,
							new HttpEntity<byte[]>(getHttpHeaders(MaconomyConstant.MEDIA_TYPE.getMacanomy(), userName, password)),JsonNode.class);

				} catch (RuntimeException runtimeException) {
					throw new ResourceNotFoundException(runtimeException.getMessage());
				}

			}
			return extractResponse(responseEntity, recordList);
		}
		return recordList;
	}
	
	@SuppressWarnings("unchecked")
	private List<JobDataDto>  extractResponse(ResponseEntity<JsonNode> responseEntity, List<JobDataDto> recordList){
		
		JsonNode records = responseEntity.getBody().get("panes").get("card").get("records");
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			recordList =(List<JobDataDto>) objectMapper.readerFor(new TypeReference<List<JobDataDto>>() {
			}).readValue(records);
			
			return recordList;
			
		} catch (IOException ioException) {
			
			ioException.printStackTrace();
		}
		return recordList;
	
}
		
	
	
	
	 private HttpHeaders getHttpHeaders(String mediaType, String userName, String password) {
	        List<MediaType> acceptHeaders = new ArrayList<MediaType>();
	        acceptHeaders.add(org.springframework.http.MediaType.valueOf(mediaType));
	        HttpHeaders headers = createHeaders(userName, password);
	        headers.setAccept(acceptHeaders);
	        headers.setContentType(acceptHeaders.get(0));
	        //headers.set("Maconomy-Authentication", "X-Force-Maconomy-Credentials");
	        headers.set(MaconomyConstant.MACONOMY_HEADER_KEY.getMacanomy(), MaconomyConstant.MACONOMY_HEADER_VALUE.getMacanomy());
	        return headers;
	    }
	 
	 
	 private HttpHeaders createHeaders(String username, String password) {
	        HttpHeaders headers = new HttpHeaders();
	        String auth = username + ":" + password;
	        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
	        String authHeader = "Basic " + new String(encodedAuth);
	        headers.set("Authorization", authHeader);
	        return headers;
	    }

}
