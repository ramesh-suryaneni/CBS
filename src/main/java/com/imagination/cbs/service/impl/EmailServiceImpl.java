package com.imagination.cbs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.constant.EmailConstants;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.dto.InternalResourceEmailDto;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.util.CBSDateUtils;
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
	
	@Autowired
	private Environment env;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendEmailForBookingApproval(MailRequest request,BookingRevision bookingRevision, String templateName){
		
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
	public void sendInternalResourceEmail(InternalResourceEmailDto internalResourceEmail) {

	try {
			String[] internalResourceToEmailReceipt = env.getProperty(EmailConstants.TO_EMAIL.getEmailConstantsString(), String[].class);
			
			MailRequest emailrequestDetails = new MailRequest();
			emailrequestDetails.setMailFrom(EmailConstants.FROM_EMAIL.getEmailConstantsString());
			emailrequestDetails.setMailTo(internalResourceToEmailReceipt);
			emailrequestDetails.setSubject(EmailConstants.INTERNAL_NOTIFICATION_SUBJECT_LINE.getEmailConstantsString());
			
			Template pushNotificationEmailTemplate = config.getTemplate("email.internalsource.ftl");
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(pushNotificationEmailTemplate,getInternalResourceEmailDataModel(internalResourceEmail));
			emailUtility.sendEmail(emailrequestDetails, body);
			
			LOGGER.info("Email send Successfully :: {}",emailrequestDetails);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	private Map<String,Object> getBookingApprovalDataModel(BookingRevision bookingRevision){
		
		Map<String,Object> mapOfTemplateValues=new HashMap<>();
		
		mapOfTemplateValues.put(EmailConstants.DISCIPLINE.getEmailConstantsString(), bookingRevision.getRole().getDiscipline());
		mapOfTemplateValues.put(EmailConstants.ROLE.getEmailConstantsString(), bookingRevision.getRole().getRoleName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR_EMPLOYEE.getEmailConstantsString(), bookingRevision.getContractEmployee().getContractorEmployeeName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR.getEmailConstantsString(), bookingRevision.getContractor().getContractorName());
		mapOfTemplateValues.put(EmailConstants.SUPPLIER_TYPE.getEmailConstantsString(), bookingRevision.getSupplierType().getName());
		mapOfTemplateValues.put(EmailConstants.START_DATE.getEmailConstantsString(),
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedFromDate()));
		mapOfTemplateValues.put(EmailConstants.END_DATE.getEmailConstantsString(),
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedToDate()));
		mapOfTemplateValues.put(EmailConstants.WORK_LOCATIONS.getEmailConstantsString(),
				bookingRevision.getContractWorkLocation().getOfficeName());
		mapOfTemplateValues.put(EmailConstants.REASON_FOR_RECRUITING.getEmailConstantsString(),bookingRevision.getReasonForRecruiting().getReasonName());
		
		BookingWorkTask task=bookingRevision.getBookingWorkTasks().get(0);
		
		mapOfTemplateValues.put(EmailConstants.TASK.getEmailConstantsString(),task.getTaskName() );
		mapOfTemplateValues.put(EmailConstants.DELIVERY_DATE.getEmailConstantsString(), task.getTaskDeliveryDate());
		mapOfTemplateValues.put(EmailConstants.DAY_RATE.getEmailConstantsString(), task.getTaskDateRate());
		mapOfTemplateValues.put(EmailConstants.TOTAL_DAYS.getEmailConstantsString(), task.getTaskTotalDays());
		mapOfTemplateValues.put(EmailConstants.TOTAL.getEmailConstantsString(), task.getTaskTotalAmount());
		mapOfTemplateValues.put(EmailConstants.TOTAL_COST.getEmailConstantsString(), task.getTaskTotalAmount());

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY.getEmailConstantsString(), user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS.getEmailConstantsString(), user.getEmail() + EmailConstants.DOMAIN.getEmailConstantsString());

		return mapOfTemplateValues;
	}
	
	private Map<String, Object> getInternalResourceEmailDataModel(InternalResourceEmailDto internalResourceEmail) {

		Map<String, Object> mapOfTemplateValues = new HashMap<>();
		
		mapOfTemplateValues.put(EmailConstants.DISCIPLINE.getEmailConstantsString(),internalResourceEmail.getDiscipline());
		mapOfTemplateValues.put(EmailConstants.ROLE.getEmailConstantsString(), internalResourceEmail.getRole());
		mapOfTemplateValues.put(EmailConstants.START_DATE.getEmailConstantsString(),internalResourceEmail.getContractedFromDate());
		mapOfTemplateValues.put(EmailConstants.END_DATE.getEmailConstantsString(),internalResourceEmail.getContractedToDate());
		mapOfTemplateValues.put(EmailConstants.JOB_NUMBER.getEmailConstantsString(), internalResourceEmail.getJobNumber());
		mapOfTemplateValues.put(EmailConstants.JOB_NAME.getEmailConstantsString(), internalResourceEmail.getJobName());
		
		return mapOfTemplateValues;
	}
	
	private Map<String, Object> getContractReceiptDataModel(){
	
		// TODO Need to replace below variables 
		String contractorPdfLink = "dummyLink";
		String scopeOfWorkLink = "dummyLink";
		
		Map<String,Object> mapOfTemplateValues=new HashMap<>();
		
		CBSUser user = loggedInUserService.getLoggedInUserDetails();
		
		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY.getEmailConstantsString(), user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS.getEmailConstantsString(), user.getEmail()+EmailConstants.DOMAIN);
		mapOfTemplateValues.put(EmailConstants.CONTRACTOR_PDF_LINK.getEmailConstantsString(), contractorPdfLink);
		mapOfTemplateValues.put(EmailConstants.SCOPE_OF_WORK_LINK.getEmailConstantsString(), scopeOfWorkLink);
		
		return mapOfTemplateValues;
	}


}
