package com.imagination.cbs.util;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.dto.MailResponse;

@Component("emailUtility")
public class EmailUtility {

	@Autowired
	private JavaMailSender sender;


	public MailResponse sendEmail(MailRequest request,String body) throws MessagingException{
		
		MailResponse response = new MailResponse();
		
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(request.getMailTo());
		helper.setText(body, true);
		helper.setSubject(request.getSubject());
		

		sender.send(message);

		response.setStatus(Boolean.TRUE);
		
		return response;

	}
}
