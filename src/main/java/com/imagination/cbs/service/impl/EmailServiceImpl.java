package com.imagination.cbs.service.impl;

import java.util.HashMap;
import java.util.List;
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
	public void sendEmailForBookingApproval(MailRequest request, BookingRevision bookingRevision, String templateName) {
		LOGGER.info("MailRequest :: {} CURRENT STATUS :: {} BOOKING_ID :: {}", request.toString(),
				bookingRevision.getApprovalStatus().toString(), bookingRevision.getBooking().getBookingId());

		try {

			Template contractNotificationEmailTemplate = config.getTemplate(
					EmailConstants.PREFIX.getConstantString() + templateName + EmailConstants.EXT.getConstantString());

			String body = FreeMarkerTemplateUtils.processTemplateIntoString(contractNotificationEmailTemplate,
					getBookingApprovalDataModel(bookingRevision));

			emailUtility.sendEmail(request, body);

		} catch (Exception e) {
			LOGGER.error("Not able to send Booking approval email", e);
		}
	}

	@Override
	public void sendContractReceipt(MailRequest request) {
		try {

			Template contractNotificationEmailTemplate = config.getTemplate(EmailConstants.PREFIX.getConstantString()
					+ EmailConstants.CONTRACT_RECEIPT_TEMPLATE.getConstantString()
					+ EmailConstants.EXT.getConstantString());

			String body = FreeMarkerTemplateUtils.processTemplateIntoString(contractNotificationEmailTemplate,
					getContractReceiptDataModel());

			emailUtility.sendEmail(request, body);

		} catch (Exception e) {

			LOGGER.error("Not able to send Contract Receipt email", e);
		}
	}

	@Override
	public void sendInternalResourceEmail(InternalResourceEmailDto internalResourceEmail) {

		try {
			String[] internalResourceToEmailReceipt = env.getProperty(EmailConstants.TO_EMAIL.getConstantString(),
					String[].class);

			MailRequest emailrequestDetails = new MailRequest();
			emailrequestDetails.setMailFrom(EmailConstants.FROM_EMAIL.getConstantString());
			emailrequestDetails.setMailTo(internalResourceToEmailReceipt);
			emailrequestDetails.setSubject(EmailConstants.INTERNAL_NOTIFICATION_SUBJECT_LINE.getConstantString());

			Template pushNotificationEmailTemplate = config.getTemplate(EmailConstants.PREFIX.getConstantString()
					+ EmailConstants.INTERNAL_SOURCE.getConstantString() + EmailConstants.EXT.getConstantString());
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(pushNotificationEmailTemplate,
					getInternalResourceEmailDataModel(internalResourceEmail));
			emailUtility.sendEmail(emailrequestDetails, body);

			LOGGER.info("Email send Successfully :: {}", emailrequestDetails);
		} catch (Exception e) {
			LOGGER.error("Not able to send email", e);

		}

	}

	private Map<String, Object> getBookingApprovalDataModel(BookingRevision bookingRevision) {

		Map<String, Object> mapOfTemplateValues = new HashMap<>();

		mapOfTemplateValues.put(EmailConstants.DISCIPLINE.getConstantString(),
				bookingRevision.getRole().getDiscipline().getDisciplineName());
		mapOfTemplateValues.put(EmailConstants.ROLE.getConstantString(), bookingRevision.getRole().getRoleName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR_EMPLOYEE.getConstantString(),
				bookingRevision.getContractEmployee().getContractorEmployeeName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR.getConstantString(),
				bookingRevision.getContractor().getContractorName());
		mapOfTemplateValues.put(EmailConstants.SUPPLIER_TYPE.getConstantString(),
				bookingRevision.getSupplierType().getName());
		mapOfTemplateValues.put(EmailConstants.START_DATE.getConstantString(),
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedFromDate()));
		mapOfTemplateValues.put(EmailConstants.END_DATE.getConstantString(),
				CBSDateUtils.convertTimeStampToString(bookingRevision.getContractedToDate()));
		mapOfTemplateValues.put(EmailConstants.WORK_LOCATIONS.getConstantString(),
				bookingRevision.getContractWorkLocation().getOfficeName());
		mapOfTemplateValues.put(EmailConstants.REASON_FOR_RECRUITING.getConstantString(),
				bookingRevision.getReasonForRecruiting().getReasonName());

		List<BookingWorkTask> bookingWorkTasks = bookingRevision.getBookingWorkTasks();
		if (bookingWorkTasks != null) {
			BookingWorkTask task = bookingWorkTasks.get(0);
			mapOfTemplateValues.put(EmailConstants.TASK.getConstantString(), task.getTaskName());
			mapOfTemplateValues.put(EmailConstants.DELIVERY_DATE.getConstantString(), task.getTaskDeliveryDate());
			mapOfTemplateValues.put(EmailConstants.DAY_RATE.getConstantString(), task.getTaskDateRate());
			mapOfTemplateValues.put(EmailConstants.TOTAL_DAYS.getConstantString(), task.getTaskTotalDays());
			mapOfTemplateValues.put(EmailConstants.TOTAL.getConstantString(), task.getTaskTotalAmount());
			mapOfTemplateValues.put(EmailConstants.TOTAL_COST.getConstantString(), task.getTaskTotalAmount());
		} else {
			mapOfTemplateValues.put(EmailConstants.TASK.getConstantString(), "");
			mapOfTemplateValues.put(EmailConstants.DELIVERY_DATE.getConstantString(), "");
			mapOfTemplateValues.put(EmailConstants.DAY_RATE.getConstantString(), "");
			mapOfTemplateValues.put(EmailConstants.TOTAL_DAYS.getConstantString(), "");
			mapOfTemplateValues.put(EmailConstants.TOTAL.getConstantString(), "");
			mapOfTemplateValues.put(EmailConstants.TOTAL_COST.getConstantString(), "");
		}

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY.getConstantString(), user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS.getConstantString(),
				user.getEmail() + EmailConstants.DOMAIN.getConstantString());

		return mapOfTemplateValues;
	}

	private Map<String, Object> getInternalResourceEmailDataModel(InternalResourceEmailDto internalResourceEmail) {

		Map<String, Object> mapOfTemplateValues = new HashMap<>();

		mapOfTemplateValues.put(EmailConstants.DISCIPLINE.getConstantString(), internalResourceEmail.getDiscipline());
		mapOfTemplateValues.put(EmailConstants.ROLE.getConstantString(), internalResourceEmail.getRole());
		mapOfTemplateValues.put(EmailConstants.START_DATE.getConstantString(),
				internalResourceEmail.getContractedFromDate());
		mapOfTemplateValues.put(EmailConstants.END_DATE.getConstantString(),
				internalResourceEmail.getContractedToDate());
		mapOfTemplateValues.put(EmailConstants.JOB_NUMBER.getConstantString(), internalResourceEmail.getJobNumber());
		mapOfTemplateValues.put(EmailConstants.JOB_NAME.getConstantString(), internalResourceEmail.getJobName());

		return mapOfTemplateValues;
	}

	private Map<String, Object> getContractReceiptDataModel() {

		// TODO Need to replace below variables
		String contractorPdfLink = "dummyLink";
		String scopeOfWorkLink = "dummyLink";

		Map<String, Object> mapOfTemplateValues = new HashMap<>();

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		mapOfTemplateValues.put(EmailConstants.REQUESTED_BY.getConstantString(), user.getDisplayName());
		mapOfTemplateValues.put(EmailConstants.EMAIL_ADDRESS.getConstantString(),
				user.getEmail() + EmailConstants.DOMAIN.getConstantString());
		mapOfTemplateValues.put(EmailConstants.CONTRACTOR_PDF_LINK.getConstantString(), contractorPdfLink);
		mapOfTemplateValues.put(EmailConstants.SCOPE_OF_WORK_LINK.getConstantString(), scopeOfWorkLink);

		return mapOfTemplateValues;
	}

}
