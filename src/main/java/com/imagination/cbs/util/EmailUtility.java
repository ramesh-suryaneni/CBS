package com.imagination.cbs.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("emailUtility")
public class EmailUtility {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
    private Configuration config;
	
	public MailResponse sendEmail(MailRequest request,Map<String,Object>model) throws Exception
    {
    	MailResponse response=new MailResponse();
    	MimeMessage message=sender.createMimeMessage();
		
		MimeMessageHelper helper=new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
		
		Template t=config.getTemplate("email.template41page.ftl");
		String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		
		helper.setTo(request.getMailTo());
		helper.setText(html,true);
		helper.setSubject(request.getSubject());
		helper.setFrom(request.getMailFrom());
		
		sender.send(message);
		
		response.setStatus(Boolean.TRUE);
    	return response;
    		
    	}
}
