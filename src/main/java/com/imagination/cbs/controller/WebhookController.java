/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.BookingService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/webhooks")
public class WebhookController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);
	
	@Autowired
	private BookingService bookingService; 
	
	@Autowired
	private AdobeSignService adobeSignService;
	
	@PostMapping("/adobesign-callback")
	public ResponseEntity<String> adobeSignCallback(@RequestBody Map<String, Object> payload) {

		JsonNode payloadNode = new ObjectMapper().valueToTree(payload);

		JsonNode agreement = payloadNode.get("agreement");
		String agreementId = agreement.get("id").asText();
		String eventDate = payloadNode.get("eventDate").asText();

		LOGGER.info("payload requestBody:::{}", payloadNode);

		// fetch agreement id and update booking with signed date
		// implement contractor signed event to update in database for booking
		
		bookingService.updateContract(agreementId, eventDate);

		return ResponseEntity.ok().body("adobe sign event handled successfully");

	}
	
	@GetMapping("/adobesign-callback")
	public ResponseEntity<String> webhookVerification(@RequestHeader HttpHeaders headers) {
		
	    headers.forEach((key, value) -> 
	    	LOGGER.info(String.format("Header '%s' = %s", key, value))
	    );
	    //respond with request headers     
	    return ResponseEntity.ok()
	    	      .headers(headers)
	    	      .body("successfull verification");
		
	}
	
	@GetMapping("/code-callback")
	public String callbackAdobeSignInCode(@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = false, name = "client_id") String adobeSignClientId,
			@RequestParam(required = false, name = "api_access_point") String apiAccessPoint,
			@RequestParam(required = false, name = "web_access_point") String webAccessPoint) {

		adobeSignService.saveOrUpdateAuthCode(code, apiAccessPoint, webAccessPoint);
				
		return "sucess";
		
	}
	
}
