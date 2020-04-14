package com.imagination.cbs.service.impl;

import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.service.Html2PdfService;
import com.itextpdf.html2pdf.HtmlConverter;

import freemarker.template.Configuration;
import freemarker.template.Template;



@Service
public class Html2PdfServiceImpl implements Html2PdfService {
	
	@Autowired
	private Configuration config;
	

	@Override
	public void generateConfirmationOfService(Map<String, Object> data) {
		
		try {
			Template template=config.getTemplate("pdf.agreement.ftl");
			 
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			HtmlConverter.convertToPdf(html, new FileOutputStream("service.pdf"));
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
		} 
		
		
	}

}
