package com.imagination.cbs.service.helper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.EmployeePermissions;
import com.imagination.cbs.domain.Permission;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.repository.ApproverRepository;
import com.imagination.cbs.repository.EmployeeMappingRepository;
import com.imagination.cbs.repository.EmployeePermissionsRepository;
import com.imagination.cbs.service.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class EmailHelperTest {
	
	@InjectMocks
	private EmailHelper emailHelper;
	
	@Mock
	private EmployeePermissionsRepository employeePermissionsRepository;

	@Mock
	private EmployeeMappingRepository employeeMappingRepository;

	@Mock
	private ApproverRepository approverRepository;

	@Mock
	private EmailService emailService;
	
	@Test
	public void shouldPrepareAndSendMailToHRForBookingApproval() {
	   
		BookingRevision bookingRevision = createBookingRevision();
		EmployeePermissions empPermission = createEmployeePermission();
		List<EmployeePermissions> employeePermissionList = new LinkedList<>();
		employeePermissionList.add(empPermission);
		
		when(employeePermissionsRepository.findAllByPermission(Mockito.any(Permission.class))).thenReturn(employeePermissionList);
		when(employeeMappingRepository.findById(1610L)).thenReturn(Optional.of(createEmployeeMapping()));
		
		emailHelper.prepareMailAndSendToHR(bookingRevision);
		
		verify(employeePermissionsRepository).findAllByPermission(Mockito.any(Permission.class));
		verify(employeeMappingRepository).findById(1610L);
		verify(emailService).sendEmailForBookingApproval(createMailRequest(), bookingRevision, "approval_request");
	}
	
	@Test
	public void shouldPrepareAndSendEmailForBookingApproval() {
		BookingRevision bookingRevision = createBookingRevision();
		Booking booking = createBooking();
		Approver approvers = new Approver();
		approvers.setEmployeeMapping(createEmployeeMapping());
		List<Approver> approvesList = new ArrayList<>();
		approvesList.add(approvers);
		
		when(approverRepository.findAllByTeamAndApproverOrder(booking.getTeam(),2L)).thenReturn(approvesList);
		
		emailHelper.prepareMailAndSend(booking,bookingRevision,2L);
		
		verify(approverRepository).findAllByTeamAndApproverOrder(booking.getTeam(),2L);
		verify(emailService).sendEmailForBookingApproval(createMailRequest(), bookingRevision, "approval_request");
	}
	
	private EmployeePermissions createEmployeePermission() {
		EmployeePermissions empPermissions = new EmployeePermissions();
		empPermissions.setEmpPermissionId(6001L);
		empPermissions.setCompanyNumber(1111L);
		empPermissions.setEmployeeMapping(createEmployeeMapping());
		return empPermissions;	
	}
	
	private MailRequest createMailRequest() {
		List<String> emails = new ArrayList<>();
		emails.add("cbs@imagination.com");
		String [] mailTo =  emails.stream().toArray(n -> new String[n]); 
		MailRequest request = new MailRequest();
		request.setMailTo(mailTo);
		request.setMailFrom("CBS@imagination.com");
		request.setSubject("Please Approve: Contractor Booking request #1910 from RRMC Geneva AS 20 Press Conf - Production-nafisa.ujloomwale");
		return request;
	}
	private EmployeeMapping createEmployeeMapping() {
		EmployeeMapping empMapping = new EmployeeMapping();
		empMapping.setEmployeeId(1610L);
		empMapping.setGoogleAccount("cbs");
		return empMapping;
	}
	
	private BookingRevision createBookingRevision() {
		Booking booking = new Booking();
		booking.setBookingDescription("Test Data");
		booking.setBookingId(1910l);
		booking.setChangedBy("nafisa.ujloomwale");
		
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setBooking(booking);
		bookingRevision.setAgreementId("C-546");
		bookingRevision.setChangedBy("nafisa.ujloomwale");
		bookingRevision.setContractorTotalAvailableDays(11l);
		bookingRevision.setContractorTotalWorkingDays(10l);
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf - Production"); 
		bookingRevision.setJobNumber("100204205-02");
		bookingRevision.setRevisionNumber(4l);
		bookingRevision.setInsideIr35("true");
		return bookingRevision;
	}
	
	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setBookingId(1910L);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setTeam(createTeam());
		booking.setBookingDescription("Test Data");
		return booking;
	}
	
	private Team createTeam(){
		Team team = new Team();
		team.setChangedBy("david.harman");
		team.setTeamId(1003l);
		team.setTeamName("ADM");
		return team;
	}
}
