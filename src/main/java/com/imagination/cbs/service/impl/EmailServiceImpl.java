package com.imagination.cbs.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.repository.BookingRevisionRepository;
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
	private BookingRevisionRepository bookingRevisionRepository;

	@Autowired
	private LoggedInUserService loggedInUserService;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendMail(MailRequest request) {

		Optional<BookingRevision> optionalBookingRevision = bookingRevisionRepository.findById(2523l);

		sendForBookingApprovalEmail(request, optionalBookingRevision.get());
	}

	@Override
	public void sendForBookingApprovalEmail(MailRequest request, BookingRevision bookingRevision) {

		try {

			Template contractNotificationEmailTemplate = config.getTemplate("email.template41page.ftl");

			String body = FreeMarkerTemplateUtils.processTemplateIntoString(contractNotificationEmailTemplate,
					getBookingApprovalDataModel(bookingRevision));

			emailUtility.sendEmail(request, body);

		} catch (Exception e) {

			LOGGER.error("Not able to send Booking approval email");
			e.printStackTrace();
		}
	}

	private Map<String, Object> getBookingApprovalDataModel(BookingRevision bookingRevision) {

		Map<String, Object> mapOfTemplateValues = new HashMap<>();

		mapOfTemplateValues.put(EmailConstants.DESCIPLINE,
				bookingRevision.getRole().getDiscipline().getDisciplineName());
		mapOfTemplateValues.put(EmailConstants.ROLE, bookingRevision.getRole().getRoleName());
		mapOfTemplateValues.put(EmailConstants.CONTRCTOR, bookingRevision.getContractor().getContractorName());
		mapOfTemplateValues.put(EmailConstants.SUPPLIER_TYPE, bookingRevision.getSupplierType().getName());
		mapOfTemplateValues.put(EmailConstants.START_DATE,
				CBSDateUtils.conevrtTimeStampIntoStringFormat(bookingRevision.getContractedFromDate()));
		mapOfTemplateValues.put(EmailConstants.END_DATE,
				CBSDateUtils.conevrtTimeStampIntoStringFormat(bookingRevision.getContractedToDate()));
		mapOfTemplateValues.put(EmailConstants.WORK_LOCATIONS,
				bookingRevision.getContractWorkLocation().getOfficeName());

		BookingWorkTask task = bookingRevision.getBookingWorkTasks().get(0);

		mapOfTemplateValues.put(EmailConstants.TASK, task.getTaskName());
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
}
