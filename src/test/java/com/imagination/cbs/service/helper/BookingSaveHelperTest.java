package com.imagination.cbs.service.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.security.CBSUser;

@RunWith(MockitoJUnitRunner.class)
public class BookingSaveHelperTest {
	
	@InjectMocks
	private BookingSaveHelper bookingSaveHelper;
	
	@Mock
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	@Mock
	private BookingRepository bookingRepository;
	
	@Test
	public void shouldReturnBookingAfterSave_SaveBooking() {
		CBSUser cbsUser = createCBSUser();
		Optional<ApprovalStatusDm> OptapprovalStatus = Optional.of(createApprovalStatusDm()); 
		BookingRevision bookingRevision = createBookingRevision();
		Booking booking = createBooking();
		
		when(approvalStatusDmRepository.findById(1010L)).thenReturn(OptapprovalStatus);
		when(bookingRepository.save(booking)).thenReturn(booking);
		
		Booking actual = bookingSaveHelper.saveBooking(booking, bookingRevision, 1010L,cbsUser );
		
		assertEquals(new Long(1010), actual.getApprovalStatus().getApprovalStatusId());
		assertEquals("Canclelled", actual.getApprovalStatus().getApprovalName());
		verify(approvalStatusDmRepository).findById(1010L);
		verify(bookingRepository).save(booking);	
	}

	@Test
	public void shouldReturnMaxBookingRevisionWhenPresentInDB_getLatestRevision() {
		Booking booking = createBooking();
		booking.getBookingRevisions().get(0).setRevisionNumber(5L);
		
		BookingRevision actual = bookingSaveHelper.getLatestRevision(booking);
		
		assertEquals(new Long(5), actual.getRevisionNumber());		
	}
	@Test
	public void shouldReturnNewBookingRevisionWhenNotPresentInDB_getLatestRevision() {
		Booking booking = new Booking();
	
		BookingRevision  actual = bookingSaveHelper.getLatestRevision(booking);
		 
		assertEquals(null, actual.getRevisionNumber());		
	}
	private ApprovalStatusDm createApprovalStatusDm() {
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm(); 
		approvalStatusDm.setApprovalName("Canclelled");
		approvalStatusDm.setApprovalStatusId(1010L);
		return approvalStatusDm;
	}
	private BookingRevision createBookingRevision() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setApprovalStatus(createApprovalStatusDm());
		bookingRevision.setAgreementId("C-546");
		bookingRevision.setChangedBy("Pappu");
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf - Production"); 
		bookingRevision.setJobNumber("100204205-02");
		bookingRevision.setRevisionNumber(5l);
		return bookingRevision;
	}
	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
	private Booking createBooking() {
		BookingRevision bookingRevision1 = new BookingRevision();
		bookingRevision1.setBookingRevisionId(4065l);
		bookingRevision1.setJobNumber("100204205-02");
		
		List<BookingRevision> bookingRevisionList = new ArrayList<>();
		bookingRevisionList.add(bookingRevision1);
		
		Booking booking = new Booking();
		booking.setBookingRevisions(bookingRevisionList);
		booking.setApprovalStatus(createApprovalStatusDm());
		return booking;
	}
}
