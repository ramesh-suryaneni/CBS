package com.imagination.cbs.service.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.CBSUnAuthorizedException;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.Html2PdfService;
import com.imagination.cbs.service.LoggedInUserService;

/**
 * @author pravin.budage
 *
 */
@Component("bookingHrApproveHelper")
public class BookingHrApproveHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingHrApproveHelper.class);

	private static final String FILE_NAME = "service.pdf";

	@Autowired
	private LoggedInUserService loggedInUserService;

	@Autowired
	private Html2PdfService html2PdfService;

	@Autowired
	private AdobeSignService adobeSignService;

	@Autowired
	private BookingSaveHelper bookingSaveHelper;

	@Autowired
	private EmailHelper emailHelper;

	public void hrApprove(Booking booking) {

		if (loggedInUserService.isCurrentUserInHRRole()) {
			Long currentStatus = booking.getApprovalStatus().getApprovalStatusId();
			LOGGER.info("BOOKING ID :: {} CURRENT STATUS :: {} ", booking.getBookingId(), currentStatus);
			if (ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId().equals(currentStatus)) {
				Long nextStatus = ApprovalStatusConstant.APPROVAL_SENT_FOR_CONTRACTOR.getApprovalStatusId();
				BookingRevision latestRevision = bookingSaveHelper.getLatestRevision(booking);

				try {
					// Generate PDF.
					LOGGER.info("PDF Generation :::::::::::::: ");
					ByteArrayOutputStream pdfStream = html2PdfService.generateAgreementPdf(latestRevision);
					InputStream inputStream = new ByteArrayInputStream(pdfStream.toByteArray());

					// integrate Adobe - upload, create agreement
					LOGGER.info("Uploading Document To Adobe :::::::::::::: ");
					String agreementDocumentId = adobeSignService.uploadAndCreateAgreement(inputStream, FILE_NAME);
					LOGGER.info("Creating Agreement :::::::::::::: ");
					String agreementId = adobeSignService.sendAgreement(agreementDocumentId, latestRevision);

					// populate document id and agreement id to revision
					latestRevision.setAgreementDocumentId(agreementDocumentId);
					latestRevision.setAgreementId(agreementId);

				} catch (Exception e) {
					LOGGER.info("Exception inside sendAgreement.");
				}

				bookingSaveHelper.saveBooking(booking, latestRevision, nextStatus,
						loggedInUserService.getLoggedInUserDetails());
				// send Email to creator - need to confirm with business
				emailHelper.prepareMailAndSendToHR(latestRevision);
			} else {
				throw new CBSApplicationException("Booking already approved or not in approval status");
			}

		} else {
			throw new CBSUnAuthorizedException("Not Authorized to perform this operation; insufficient previllages");
		}
	}

}
