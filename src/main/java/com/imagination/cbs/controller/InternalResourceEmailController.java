package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.InternalResourceEmailDto;
import com.imagination.cbs.service.EmailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/internal-resource-email")
public class InternalResourceEmailController {

	@Autowired
	private EmailService emailServiceImpl;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public HttpStatus addNewContractorEmployee(@RequestBody InternalResourceEmailDto internalResourceEmailDto) {

		emailServiceImpl.sendInternalResourceEmail(internalResourceEmailDto); 

		return HttpStatus.OK;

	}
}
