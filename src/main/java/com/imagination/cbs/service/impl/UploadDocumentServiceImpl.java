package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.AGREEMENTS_ENDPOINT;
import static com.imagination.cbs.util.AdobeConstant.TRANSIENT_DOCUMENTS_ENDPOINT;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.imagination.cbs.service.UploadDocumentService;
import com.imagination.cbs.util.Utils;

@Service
public class UploadDocumentServiceImpl implements UploadDocumentService {

	@Autowired
	Environment env;

	@Autowired
	AdobeOAuthTokensServiceImpl oAuth;

	@Override
	public String uploadDocUsingTransientDocument(FileSystemResource file) throws Exception {

		String fileName = file.getFilename();
		System.out.println("fileName::::"+fileName);
		ResponseEntity<JsonNode> result = null;
		RestTemplate restTemplate = new RestTemplate();
		String transientDocUrl = env.getProperty("adobe.baseUris") + "/api/rest/v6" + TRANSIENT_DOCUMENTS_ENDPOINT;
		System.out.println("filetransientDocUrl::::"+transientDocUrl);
		String accessToken = oAuth.getOauthAccessToken();

		Resource resource = new InputStreamResource(file.getInputStream());
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add(Utils.HttpHeaderField.FILE.toString(), resource);
		body.add(Utils.HttpHeaderField.FILE_NAME.toString(), fileName);
		body.add(Utils.HttpHeaderField.MIME_TYPE.toString(), MediaType.APPLICATION_PDF_VALUE);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
		headers.add(Utils.HttpHeaderField.AUTHORIZATION.toString(), accessToken);

		HttpEntity<?> entity = new HttpEntity<>(body, headers);

		result = restTemplate.exchange(transientDocUrl, HttpMethod.POST, entity, JsonNode.class);

		String transientDocumentId = result.getBody().path("transientDocumentId").asText();
		String sendAgreement = sendAgreement(transientDocumentId);
		System.out.println("sendAgreement retrun agreement id::" + sendAgreement);
		System.out.println("File uplaod through transient Doc API::>" + result.getBody() + " id=" + transientDocumentId);

		return transientDocumentId;
	}

	@SuppressWarnings("unchecked")
	public String sendAgreement(String transientDocId) throws Exception {
		JSONParser parser = new JSONParser();
		RestTemplate restTemplate = new RestTemplate();

		String accessToken = oAuth.getOauthAccessToken();

		Object obj = parser.parse(new FileReader("SendAgreement.json"));
		JSONObject jsonBody = (JSONObject) obj;
		ArrayList<JSONObject> fileInfos = new ArrayList<JSONObject>();
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("transientDocumentId", transientDocId);
		fileInfos.add(fileInfo);
		jsonBody.put("fileInfos", fileInfos);
		System.out.println("body:::" + jsonBody.toString());

		String agreementsUrl = env.getProperty("baseUris") + "/api/rest/v6" + AGREEMENTS_ENDPOINT;

		ResponseEntity<JsonNode> response = null;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add(Utils.HttpHeaderField.AUTHORIZATION.toString(), accessToken);

		HttpEntity<?> httpentity = new HttpEntity<>(jsonBody.toString(), header);
		response = restTemplate.exchange(agreementsUrl, HttpMethod.POST, httpentity, JsonNode.class);

		System.out.println("After File Upload Agreements send to User::::" + response.getBody());

		return response.getBody().path("id").asText();

	}

}
