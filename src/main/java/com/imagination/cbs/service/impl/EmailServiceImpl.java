package com.imagination.cbs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.dto.InternalResourceEmail;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.util.CBSDateUtils;
import com.imagination.cbs.util.EmailConstants;
import com.imagination.cbs.util.EmailUtility;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	private Configuration config;

	@Autowired
	private LoggedInUserService loggedInUserService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendForBookingApprovalEmail(MailRequest request,BookingRevision bookingRevision, String templateName){
		
		try {
			
			Template contractNotificationEmailTemplate = config.getTemplate("email.template_"+templateName+".ftl");
			
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(contractNotificationEmailTemplate, getBookingApprovalDataModel(bookingRevision));
			
			emailUtility.sendEmail(request, body);

		} catch (Exception e) {

			LOGGER.error("Not able to send Booking approval email");
			e.printStackTrace();
		}
	}

	@Override
	public void sendContractReceipt(MailRequest request) {
		try {
			
			Template contractNotificationEmailTemplate = config.getTemplate("email.template_ContractReceipt.ftl");
			
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(contractNotificationEmailTemplate, getContractReceiptDataModel());
			
			emailUtility.sendEmail(request, body);
		
		} catch (Exception e) {
			
			LOGGER.error("Not able to send Contract Receipt email");
			e.printStackTrace();
		} 		
	}

	@Override
	public void sendInternalResourceEmail(MailRequest request, InternalResourceEmail internalResourceEmail) {

		try {

			Template pushNotificationEmailTemplate = config.getTemplate("email.template50page.ftl");

			String body = FreeMarkerTemplateUtils.processTemplateIntoString(pushNotificationEmailTemplate,getInternalResourceEmailDataModel(internalResourceEmail));

			emailUtility.sendEmail(request, body);
			
			LOGGER.info("Email send Successfully :: {}",request);

		} catch (Exception e) {

			LOGGER.error("Not able to send internal resource push notification");
			e.printStackTrace();
		}

	}

	private Map<String,Object> getBookingApprovalDataModel(BookingRevision bookingRevision){
		
		Map<String,Object> mapOfTemplateValues=new HashMap<>();
		
		mapOfTemplateValues.put(EmailConstants.DISCIPLINE, bookingRevision.getRole().getDiscipline());
		mapOfTemplateValues.put(EmailConstants.ROLE, bookingRevision.getRole().getRoleName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR_EMPLOYEE, bookingRevision.getContractEmployee().getContractorEmployeeName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR, bookingRevision.getContractor().getContractorName());
		mapOfTemplateValues.put(EmailConstants.SUPPLIER_TYPE, bookingRevision.getSupplierType().getName());
		mapOfTemplateValues.put(EmailConstants.START_DATE,
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedFromDate()));
		mapOfTemplateValues.put(EmailConstants.END_DATE,
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedToDate()));
		mapOfTemplateValues.put(EmailConstants.WORK_LOCATIONS,
				bookingRevision.getContractWorkLocation().getOfficeName());
		mapOfTemplateValues.put(EmailConstants.REASON_FOR_RECRUITING,bookingRevision.getReasonForRecruiting().getReasonName());
		
		BookingWorkTask task=bookingRevision.getBookingWorkTasks().get(0);
		
		mapOfTemplateValues.put(EmailConstants.TASK,task.getTaskName() );
		mapOfTemplateValues.put(EmailConstants.DELIVERY_DATE, task.getTaskDeliveryDate());
		mapOfTemplateValues.put(EmailConstants.DAY_RATE, task.getTaskDateRate());
		mapOfTemplateValues.put(EmailConstants.TOTAL_DAYS, task.getTaskTotalDays());
		mapOfTemplateValues.put(EmailConstants.TOTAL, task.getTaskTotalAmount());
		mapOfTemplateValues.put(EmailConstants.TOTAL_COST, task.getTaskTotalAmount());

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY, user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS, user.getEmail() + EmailConstants.DOMAIN);

		return mapOfTemplateValues;
	}
	
	private Map<String, Object> getInternalResourceEmailDataModel(InternalResourceEmail internalResourceEmail) {

		Map<String, Object> mapOfTemplateValues = new HashMap<>();
		mapOfTemplateValues.put(EmailConstants.DISCIPLINE,internalResourceEmail.getDescipline());
		mapOfTemplateValues.put(EmailConstants.ROLE, internalResourceEmail.getRole());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR, internalResourceEmail.getContractorName());
		mapOfTemplateValues.put(EmailConstants.START_DATE,internalResourceEmail.getContractedFromDate());
		mapOfTemplateValues.put(EmailConstants.END_DATE,internalResourceEmail.getContractedToDate());
		mapOfTemplateValues.put(EmailConstants.JOB_NUMBER, internalResourceEmail.getJobNumber());
		mapOfTemplateValues.put(EmailConstants.SUPPLIER_TYPE, internalResourceEmail.getSupplierType());
		
		return mapOfTemplateValues;
	}
	
	private Map<String, Object> getContractReceiptDataModel(){
	
		// TODO Need to replace below variables 
		String contractorPdfLink = "dummyLink";
		String scopeOfWorkLink = "dummyLink";
		
		Map<String,Object> mapOfTemplateValues=new HashMap<>();
		
		CBSUser user = loggedInUserService.getLoggedInUserDetails();
		
		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY, user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS, user.getEmail()+EmailConstants.DOMAIN);
		mapOfTemplateValues.put(EmailConstants.CONTRACTOR_PDF_LINK, contractorPdfLink);
		mapOfTemplateValues.put(EmailConstants.SCOPE_OF_WORK_LINK, scopeOfWorkLink);
		
		return mapOfTemplateValues;
	}


}
