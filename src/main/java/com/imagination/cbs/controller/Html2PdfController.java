package com.imagination.cbs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.service.Html2PdfService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/htmlToPdf")
public class Html2PdfController {

	@Autowired
	private final Html2PdfService pdfService;

	@RequestMapping(value = "/generateConfirmationOfServicePdf", method = RequestMethod.GET)
	public void convertHtmlToPdf() {
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("consultancyCompanyName", "Yash Technology");
		dataModel.put("consultancyCompanyNumber", "1001");
		dataModel.put("consultancyCompanyAddress", "Magarpatta, hadapsar,pune");
		dataModel.put("commencementDate", "09/04/2020");
		dataModel.put("endDate", "30/04/2020");
		dataModel.put("services", " Test ");
		dataModel.put("companyProject", "Development and Support");
		dataModel.put("confirmationOfServiceNumber", "test");
		dataModel.put("representative", "Ramesh Suryaneni");
		dataModel.put("consultancyEmailAddress", "hr@yash.com");
		dataModel.put("engagingManager", "Mitul Deshmukh");
		dataModel.put("fee", "1001");
		dataModel.put("invoiceMilestones", "Sprint");
		dataModel.put("cestTestOutput", "Test");
		dataModel.put("signedBy", "Monali Jadhav");
		dataModel.put("signedDate", "09/04/2020");

		pdfService.generateConfirmationOfService(dataModel);

	}
}