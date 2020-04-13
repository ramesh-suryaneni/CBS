package com.imagination.cbs.service;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.InternalResourceEmail;
import com.imagination.cbs.dto.MailRequest;

public interface EmailService {

	void sendEmailForBookingApproval(MailRequest request, BookingRevision bookingRevision, String templateName);

	void sendContractReceipt(MailRequest request);

	void sendInternalResourceEmail(MailRequest request, InternalResourceEmail internalResourceEmail);

}