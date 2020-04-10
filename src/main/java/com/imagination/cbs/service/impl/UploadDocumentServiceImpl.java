package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.ADOBE_API_ACCESSPOINT;
import static com.imagination.cbs.util.AdobeConstant.AGREEMENTS_ENDPOINT;
import static com.imagination.cbs.util.AdobeConstant.TRANSIENT_DOCUMENTS_ENDPOINT;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.service.UploadDocumentService;
import com.imagination.cbs.util.AdobeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadDocumentServiceImpl implements UploadDocumentService {

	@Autowired
	Environment env;

	@Autowired
	AdobeOAuthTokensServiceImpl oAuth;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String uploadDocUsingTransientDocument(FileSystemResource file) {

		try {
		String fileName = file.getFilename();
		ResponseEntity<JsonNode> result = null;

		String accessToken = oAuth.getOauthAccessToken();

		String transientDocUrl = env.getProperty(ADOBE_API_ACCESSPOINT) + "/api/rest/v6" + TRANSIENT_DOCUMENTS_ENDPOINT;
		log.info("TransientDocUrl={} fileName={}", transientDocUrl, fileName);

		Resource resource= new InputStreamResource(file.getInputStream());
		
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add(AdobeUtils.HttpHeaderField.FILE.toString(), resource);
		body.add(AdobeUtils.HttpHeaderField.FILE_NAME.toString(), fileName);
		body.add(AdobeUtils.HttpHeaderField.MIME_TYPE.toString(), MediaType.APPLICATION_PDF_VALUE);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
		headers.add(AdobeUtils.HttpHeaderField.AUTHORIZATION.toString(), accessToken);

		HttpEntity<?> entity = new HttpEntity<>(body, headers);

		result = restTemplate.exchange(transientDocUrl, HttpMethod.POST, entity, JsonNode.class);

		String transientDocumentId = result.getBody().path("transientDocumentId").asText();
		log.info("File uplaod through transient Doc API transientDocumentId:: {}", transientDocumentId);

		String sendAgreement = sendAgreement(transientDocumentId);
		log.info("Agreement Id= {}  Body={}", sendAgreement, result.getBody());
		return transientDocumentId;
		} catch (IOException e) {
			log.info("Exception insdie ::::{}"+e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String sendAgreement(String transientDocId) {
		JSONParser parser = new JSONParser();

		String accessToken = oAuth.getOauthAccessToken();
		log.info("AccessToken::: {} ",accessToken);
		
		try {
			
			Object obj = parser.parse(new FileReader("SendAgreement.json"));

			JSONObject jsonBody = (JSONObject) obj;

			ArrayList<JSONObject> fileInfos = new ArrayList<>();
			JSONObject fileInfo = new JSONObject();
			fileInfo.put("transientDocumentId", transientDocId);
			fileInfos.add(fileInfo);
			jsonBody.put("fileInfos", fileInfos);
			log.info("Body::: {}" + jsonBody.toString());

			String agreementsUrl = env.getProperty("baseUris") + "/api/rest/v6" + AGREEMENTS_ENDPOINT;

			ResponseEntity<JsonNode> response = null;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add(HttpHeaders.AUTHORIZATION, accessToken);

			HttpEntity<?> httpentity = new HttpEntity<>(jsonBody.toString(), header);
			response = restTemplate.exchange(agreementsUrl, HttpMethod.POST, httpentity, JsonNode.class);

			log.info("After File Upload Agreements send to User:::: {}" + response.getBody());

			return response.getBody().path("id").asText();

		} catch (IOException | ParseException exception) {
			throw new CBSApplicationException(exception.getLocalizedMessage());
		}
	}

}
