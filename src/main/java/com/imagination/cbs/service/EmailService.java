package com.imagination.cbs.service;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.InternalResourceEmailDto;
import com.imagination.cbs.dto.MailRequest;

public interface EmailService {

	void sendEmailForBookingApproval(MailRequest request, BookingRevision bookingRevision, String templateName);

	void sendContractReceipt(BookingRevision bookingRevision);

	void sendInternalResourceEmail(InternalResourceEmailDto internalResourceEmail);

}