package com.imagination.cbs.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.constant.EmailConstants;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.LoggedInUserService;

/**
 * @author pravin.budage
 *
 */
@Service("bookingDeclineHelper")
public class BookingDeclineHelper {

	private static final String DECLINE_SUBJECT_LINE = "Declined : Contractor Booking request # from ";

	@Autowired
	private BookingSaveHelper bookingSaveHelper;

	@Autowired
	private LoggedInUserService loggedInUserService;

	@Autowired
	private EmailService emailService;

	public void decline(Booking booking) {

		Long nextStatus = ApprovalStatusConstant.APPROVAL_REJECTED.getApprovalStatusId();

		BookingRevision latestRevision = bookingSaveHelper.getLatestRevision(booking);
		bookingSaveHelper.saveBooking(booking, latestRevision, nextStatus,
				loggedInUserService.getLoggedInUserDetails());
		MailRequest request = new MailRequest();
		request.setMailTo(new String[] { booking.getChangedBy() + EmailConstants.DOMAIN.getConstantString() });
		request.setSubject(DECLINE_SUBJECT_LINE.replace("#", "#" + booking.getBookingId()) + latestRevision.getJobname()
				+ "-" + latestRevision.getChangedBy());
		request.setMailFrom(EmailConstants.FROM_EMAIL.getConstantString());
		emailService.sendEmailForBookingApproval(request, latestRevision,
				EmailConstants.BOOKING_REQUEST_TEMPLATE.getConstantString());
	}
}
