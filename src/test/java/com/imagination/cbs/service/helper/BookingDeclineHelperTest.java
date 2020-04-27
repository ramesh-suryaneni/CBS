package com.imagination.cbs.service.helper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.LoggedInUserService;

@RunWith(MockitoJUnitRunner.class)
public class BookingDeclineHelperTest {

	@InjectMocks
	private BookingDeclineHelper bookingDeclineHelper;
	
	@Mock
	private BookingSaveHelper bookingSaveHelper;

	@Mock
	private LoggedInUserService loggedInUserService;

	@Mock
	private EmailService emailService;
	
	@Test
	public void shouldSendEmailForRejectedBookingApproval() {
		
		Booking booking = createBooking();
		BookingRevision bookingRevision = createBookingRevision();
		CBSUser cbsUser = createCBSUser();
		
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingSaveHelper.saveBooking(booking, bookingRevision, 1011L,cbsUser)).thenReturn(booking);
		
		bookingDeclineHelper.decline(booking);
		
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingSaveHelper).saveBooking(booking, bookingRevision, 1011L,cbsUser);
		verify(emailService).sendEmailForBookingApproval(createMailRequest(),bookingRevision,"approval_request");
	}

	private BookingRevision createBookingRevision() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setChangedBy(" Production-Pappu");
		bookingRevision.setRevisionNumber(5l);
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf ");
		return bookingRevision;
	}
	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setBookingId(1910L);
		booking.setChangedBy("nafisa.ujloomwale");
		return booking;
	}
	private MailRequest createMailRequest() {
		String [] mailTo = {"nafisa.ujloomwale@imagination.com"};
		MailRequest request = new MailRequest();
		request.setMailTo(mailTo);
		request.setSubject("Declined : Contractor Booking request #1910 from RRMC Geneva AS 20 Press Conf - Production-Pappu");
		request.setMailFrom("CBS@imagination.com");
		return request;
	}
}
