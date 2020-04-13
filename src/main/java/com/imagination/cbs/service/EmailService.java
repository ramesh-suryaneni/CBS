package com.imagination.cbs.service;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.InternalResourceEmail;
import com.imagination.cbs.dto.MailRequest;

public interface EmailService {

	void sendMail(MailRequest request);

	void sendForBookingApprovalEmail(MailRequest request, BookingRevision bookingRevision);

	void sendInternalResourceEmail(MailRequest request, InternalResourceEmail internalResourceEmail);

}