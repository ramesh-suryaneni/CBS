package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.imagination.cbs.dto.DashBoardBookingDto;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;

@RunWith(MockitoJUnitRunner.class)
public class DashBoardServiceImplTest {
	
	
	@InjectMocks
	private DashBoardServiceImpl dashBoardServiceImpl;
	
	@Mock
	private BookingRevisionRepository bookingRevisionRepository;
	
	@Mock
	private LoggedInUserService loggedInUserService;
	
	@Mock
	private Tuple tuple;
	
	@Test
	public void ShouldReturnListOfBookingDetailsForSubmittedStatus(){
		
		CBSUser cbsUser = createCBSUser();
		
		List<Tuple> bookingRevisions = createTupleList();
		
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRevisionRepository.retrieveBookingRevisionForSubmitted("Pappu", PageRequest.of(10, 100))).thenReturn(bookingRevisions);
		when(tuple.get("status", String.class)).thenReturn("Submitted");
		
		Page<DashBoardBookingDto> actual = dashBoardServiceImpl.getDashBoardBookingsStatusDetails("Submitted", 10, 100);
		
		verify(loggedInUserService, times(2)).getLoggedInUserDetails();
		verify(bookingRevisionRepository).retrieveBookingRevisionForSubmitted("Pappu", PageRequest.of(10, 100));
		
		assertEquals(1002, actual.getContent().get(0).getBookingId().intValue());
		assertEquals("Submitted", actual.getContent().get(0).getStatus());
		assertEquals("100_Admin_Jobs", actual.getContent().get(0).getJobName());
		assertEquals("YASH", actual.getContent().get(0).getContractorName());
	}
	
	@Test
	public void ShouldReturnListOfBookingDetailsForDraftStatus(){
		
		CBSUser cbsUser = createCBSUser();
		
		List<Tuple> bookingRevisions = createTupleList();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRevisionRepository.retrieveBookingRevisionForDraftOrCancelled("Pappu", "Draft", PageRequest.of(10, 100))).thenReturn(bookingRevisions);
		when(tuple.get("status", String.class)).thenReturn("Draft");
		when(tuple.get("completedAgreementPdf", String.class)).thenReturn("maginationcbs.z10.web.core.windows.net");
		
		Page<DashBoardBookingDto> actual = dashBoardServiceImpl.getDashBoardBookingsStatusDetails("Draft", 10, 100);
		
		verify(loggedInUserService, times(2)).getLoggedInUserDetails();
		verify(bookingRevisionRepository).retrieveBookingRevisionForDraftOrCancelled("Pappu", "Draft", PageRequest.of(10, 100));
		
		assertEquals(1002, actual.getContent().get(0).getBookingId().intValue());
		assertEquals("Draft", actual.getContent().get(0).getStatus());
		assertEquals("100_Admin_Jobs", actual.getContent().get(0).getJobName());
		assertEquals("YASH", actual.getContent().get(0).getContractorName());
		
	}
	
	@Test
	public void ShouldReturnListOfBookingDetailsForWaitingStatus(){
		
		CBSUser cbsUser = createCBSUser();
		
		List<Tuple> bookingRevisions = createTupleList();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByJobNumber(1002L, PageRequest.of(10, 100))).thenReturn(bookingRevisions);
		when(bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByEmployeeId(1002L, PageRequest.of(10, 100))).thenReturn(bookingRevisions);
		when(bookingRevisionRepository.retrieveBookingRevisionForWaitingForHRApproval(1002L, PageRequest.of(10, 100))).thenReturn(bookingRevisions);
		when(tuple.get("status", String.class)).thenReturn("waiting");
		
		Page<DashBoardBookingDto> actual = dashBoardServiceImpl.getDashBoardBookingsStatusDetails("waiting", 10, 100);
		
		verify(loggedInUserService, times(2)).getLoggedInUserDetails();
		verify(bookingRevisionRepository).retrieveBookingRevisionForWaitingForApprovalByJobNumber(1002L, PageRequest.of(10, 100));
		verify(bookingRevisionRepository).retrieveBookingRevisionForWaitingForApprovalByEmployeeId(1002L, PageRequest.of(10, 100));
		verify(bookingRevisionRepository).retrieveBookingRevisionForWaitingForHRApproval(1002L, PageRequest.of(10, 100));
		
		assertEquals(1002, actual.getContent().get(0).getBookingId().intValue());
		assertEquals("waiting", actual.getContent().get(0).getStatus());
		assertEquals("100_Admin_Jobs", actual.getContent().get(0).getJobName());
		assertEquals("YASH", actual.getContent().get(0).getContractorName());
		
	}
	

	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
	
	private List<Tuple> createTupleList(){
		
		List<Tuple> bookingRevisions = new ArrayList<>();
		
		when(tuple.get("jobName", String.class)).thenReturn("100_Admin_Jobs");
		when(tuple.get("contractorName",String.class)).thenReturn("YASH");
		when(tuple.get("contractedFromDate", Timestamp.class)).thenReturn(new Timestamp(System.currentTimeMillis()));
		when(tuple.get("contractedToDate", Timestamp.class)).thenReturn(new Timestamp(System.currentTimeMillis()));
		when(tuple.get("changedBy", String.class)).thenReturn("Pappu");
		when(tuple.get("changedDate", Timestamp.class)).thenReturn(new Timestamp(System.currentTimeMillis()));
		when(tuple.get("bookingId", BigInteger.class)).thenReturn(BigInteger.valueOf(1002));
		when(tuple.get("bookingRevisionId", BigInteger.class)).thenReturn(BigInteger.valueOf(1012));
		when(tuple.get("bookingCreater", String.class)).thenReturn("Pappu");
		
		bookingRevisions.add(tuple);
		return bookingRevisions;
	}


}
