package com.imagination.cbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.InternalResourceEmail;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.impl.EmailServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/internal-resource-email")
public class InternalResourceEmailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailService emailServiceImpl;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public HttpStatus addNewContractorEmployee(@RequestBody InternalResourceEmail emailEequest) {

		emailServiceImpl.sendInternalResourceEmail(emailEequest.getMailRequest(), emailEequest);

		LOGGER.info("Email send successfully");

		return HttpStatus.OK;

	}
}
