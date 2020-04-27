package com.imagination.cbs.service.helper;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.CBSUnAuthorizedException;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.Html2PdfService;
import com.imagination.cbs.service.LoggedInUserService;

@RunWith(MockitoJUnitRunner.class)
public class BookingHrApproveHelperTest {

	@InjectMocks
	private BookingHrApproveHelper bookingHrApproveHelper;
	
	@Mock
	private LoggedInUserService loggedInUserService;

	@Mock
	private Html2PdfService html2PdfService;

	@Mock
	private AdobeSignService adobeSignService;

	@Mock
	private BookingSaveHelper bookingSaveHelper;

	@Mock
	private EmailHelper emailHelper;
	
	@Test
	public void shouldPrepareMailAndSendToHrWithBookingRevisionDetailsWhenUserIsAuthorized() throws IOException {
		Booking booking = createBooking();
		BookingRevision bookingRevision = createBookingRevision();
		byte b[] = {20,10,30,5};
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(b);
		CBSUser cbsUser = createCBSUser();
		
		when(loggedInUserService.isCurrentUserInHRRole()).thenReturn(true);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(html2PdfService.generateAgreementPdf(bookingRevision)).thenReturn(byteArrayOutputStream);
		when(adobeSignService.uploadAndCreateAgreement((Mockito.any(InputStream.class)),Mockito.eq("service.pdf"))).thenReturn("CBS-111");
		when(adobeSignService.sendAgreement("CBS-111", bookingRevision)).thenReturn("C-546");
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingSaveHelper.saveBooking(booking, bookingRevision, 1006L,cbsUser)).thenReturn(booking);
		
		bookingHrApproveHelper.hrApprove(booking);
		
		verify(loggedInUserService).isCurrentUserInHRRole();
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(html2PdfService).generateAgreementPdf(bookingRevision);		
		verify(emailHelper).prepareMailAndSendToHR(bookingRevision);
		verify(adobeSignService).uploadAndCreateAgreement(Mockito.any(InputStream.class),Mockito.eq("service.pdf"));
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingSaveHelper).saveBooking(booking, bookingRevision, 1006L,cbsUser);
	}

	@Test(expected = CBSUnAuthorizedException.class )
	public void shouldThrowCBSUnAuthorizedExceptionWhenUserIsUnAuthorized() {
		
		when(loggedInUserService.isCurrentUserInHRRole()).thenReturn(false);
		
		bookingHrApproveHelper.hrApprove(createBooking());
		
	}
	
	@Test(expected = CBSApplicationException.class )
	public void shouldThrowCBSApplicationExceptionWhenBookingAlreadyApprovedOrNotInApprovalStatus() {
		
		ApprovalStatusDm approvalStatus = createApprovalStatusDm();
		approvalStatus.setApprovalStatusId(1111L);
		Booking booking = createBooking();
		booking.setApprovalStatus(approvalStatus);
		
		when(loggedInUserService.isCurrentUserInHRRole()).thenReturn(true);
		
		bookingHrApproveHelper.hrApprove(booking);
	}
	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setApprovalStatus(createApprovalStatusDm());
		booking.setBookingDescription("Test Data");
		booking.setBookingId(1910L);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setBookingDescription("Test Data");
		return booking;
	}
	private ApprovalStatusDm createApprovalStatusDm() {
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalName("Sent To HR");
		approvalStatusDm.setApprovalStatusId(1005L);
		return approvalStatusDm;
	}
	private BookingRevision createBookingRevision() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setAgreementId("C-546");
		bookingRevision.setAgreementDocumentId("CBS-111");
		bookingRevision.setChangedBy("nafisa.ujloomwale");
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf - Production"); 
		bookingRevision.setJobNumber("100204205-02");
		bookingRevision.setRevisionNumber(4l);
		bookingRevision.setInsideIr35("true");
		return bookingRevision;
	}
	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
}
