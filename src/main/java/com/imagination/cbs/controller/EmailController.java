package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailServiceImpl;
	
	@PostMapping("/send")
	public void sendEmail(@RequestBody MailRequest request){
		
		emailServiceImpl.sendMail(request);
	}
}
