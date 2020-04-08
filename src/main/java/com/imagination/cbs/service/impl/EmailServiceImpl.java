package com.imagination.cbs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.util.EmailUtility;

@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService{

	@Autowired
	private EmailUtility emailUtility;
	
	@Override
	public void sendMail(MailRequest request) {
		
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("Name", request.getName());
		dataModel.put("MailRequest", request.getMailRequest());
		
		try {
			emailUtility.sendEmail(request, dataModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
