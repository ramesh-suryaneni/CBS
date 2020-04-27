package com.imagination.cbs.service.helper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.ApproverOverrides;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.CBSUnAuthorizedException;
import com.imagination.cbs.repository.ApproverOverridesRepository;
import com.imagination.cbs.repository.ApproverRepository;
import com.imagination.cbs.security.CBSUser;

@RunWith(MockitoJUnitRunner.class)
public class BookingApproveHelperTest {

	@InjectMocks
	private BookingApproveHelper bookingApproveHelper;
	
	@Mock
	private ApproverRepository approverRepository;

	@Mock
	private ApproverOverridesRepository approverOverridesRepository;

	@Mock
	private BookingSaveHelper bookingSaveHelper;

	@Mock
	private EmailHelper emailHelper;
	
	@Test
	public void shouldPrepareEmailAndSendTO_HR_WhenBookingCanBeOverride() {
		final Booking booking = createBooking();
		final ApprovalStatusDm approvalStatus = createApprovalStatusDm();
		final Team approverTeam = createTeam();
		final CBSUser cbsUser = createCBSUser();
		final BookingRevision bookingRevision = createBookingRevision();
		bookingRevision.setApprovalStatus(approvalStatus);
		bookingRevision.setTeam(approverTeam);
		final List<Approver> approverList = new ArrayList<>();
		approverList.add(createApprover());
		
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(approverRepository.findAllByTeam(bookingRevision.getTeam())).thenReturn(approverList); 
		when(approverOverridesRepository
					.findByEmployeeIdAndJobNumber(1002L, "100204205-02")).thenReturn(createApproverOverrides());
		when(bookingSaveHelper.saveBooking(booking, bookingRevision,
				1005L, cbsUser)).thenReturn(booking);
		
		bookingApproveHelper.approve(booking, cbsUser);
		
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(approverRepository).findAllByTeam(approverTeam);
		verify(approverOverridesRepository).findByEmployeeIdAndJobNumber(1002L, "100204205-02"); 
		verify(bookingSaveHelper).saveBooking(booking, bookingRevision,1005L, cbsUser);
		verify(emailHelper).prepareMailAndSendToHR(bookingRevision);
	}

	@Test
	public void shouldPrepareEmailAndSent_TO_HR_WhenBookingCannotBeOverride() {
		final Booking booking = createBooking();
		final ApprovalStatusDm approvalStatus = createApprovalStatusDm();
		final Team approverTeam = createTeam();
		final CBSUser cbsUser = createCBSUser();
		final Approver approver =createApprover();
		final BookingRevision bookingRevision = createBookingRevision();
		bookingRevision.setApprovalStatus(approvalStatus);
		bookingRevision.setTeam(approverTeam);
		final List<Approver> approverList = new ArrayList<>();
		approverList.add(approver);
		
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(approverRepository.findAllByTeam(bookingRevision.getTeam())).thenReturn(approverList); 
		when(approverOverridesRepository.findByEmployeeIdAndJobNumber(1002L,"100204205-02")).thenReturn(null);
		when(approverRepository.findByTeamAndEmployeeAndApproverOrder
				(any(Team.class),any(EmployeeMapping.class),eq(3L))).thenReturn(approver); 
		when(bookingSaveHelper.saveBooking(booking, bookingRevision,1005L, cbsUser)).thenReturn(booking);

		bookingApproveHelper.approve(booking, cbsUser);
		
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(approverRepository).findAllByTeam(approverTeam);
		verify(approverOverridesRepository).findByEmployeeIdAndJobNumber(1002L, "100204205-02");
		verify(approverRepository).findByTeamAndEmployeeAndApproverOrder
				(any(Team.class),any(EmployeeMapping.class),eq(3L));
		verify(emailHelper).prepareMailAndSendToHR(bookingRevision);  
	}
 
	@Test(expected = CBSUnAuthorizedException.class)
	public void shouldThrowCBSUnAuthorizedExceptionWhenUserIsNotAuthorized() {
		
		final Booking booking = createBooking();
		final CBSUser cbsUser = createCBSUser();
		final ApprovalStatusDm approvalStatus = createApprovalStatusDm();
		final Team approverTeam = createTeam();
		final Approver approver =createApprover();
		final BookingRevision bookingRevision = createBookingRevision();
		bookingRevision.setApprovalStatus(approvalStatus);
		bookingRevision.setTeam(approverTeam); 
		final List<Approver> approverList = new ArrayList<>();
		approverList.add(approver);
		
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(approverRepository.findAllByTeam(bookingRevision.getTeam())).thenReturn(approverList); 
		when(approverOverridesRepository.findByEmployeeIdAndJobNumber(1002L,"100204205-02")).thenReturn(null);
		when(approverRepository.findByTeamAndEmployeeAndApproverOrder
				(any(Team.class),any(EmployeeMapping.class),eq(3L))).thenReturn(null); 
		
		bookingApproveHelper.approve(booking, cbsUser);
		
	}
	
	@Test(expected = CBSApplicationException.class)
	public void shouldThrowCBSApplicationExceptionWhenBookingIsNotInApprovalStatus() {
		
		final Booking booking = createBooking();
		final CBSUser cbsUser = createCBSUser();
		final ApprovalStatusDm approvalStatus = createApprovalStatusDm();
		approvalStatus.setApprovalStatusId(1001L);
		final BookingRevision bookingRevision = createBookingRevision();
		bookingRevision.setApprovalStatus(approvalStatus);
		
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		
		bookingApproveHelper.approve(booking, cbsUser);
		
	}
	
	private ApproverOverrides createApproverOverrides() {
		ApproverOverrides approverOverrides = new ApproverOverrides();
		approverOverrides.setEmployeeId(1002L);
		approverOverrides.setJobNumber("100204205-02");
		return approverOverrides;
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
		approvalStatusDm.setApprovalStatusId(1004L);
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
	private Team createTeam(){
		Team team = new Team();
		team.setChangedBy("david.harman");
		team.setTeamId(1003l);
		team.setTeamName("ADM");
		return team;
	}
	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
	private Approver createApprover() {
		Approver approver = new Approver();
		approver.setApproverId(1001L);
		return approver;
	}
}
