package com.imagination.cbs.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.dto.InternalResourceEmailDto;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.util.EmailUtility;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {
	
	@InjectMocks
	EmailServiceImpl emailServiceImpl;
	
	@Mock
	private EmailUtility emailUtility;

	@Mock
	private Configuration config;

	@Mock
	private LoggedInUserService loggedInUserService;

	@Mock
	private Environment environment;

	@Mock
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	private Template template;
	private MailRequest emailRequest;
	private ApprovalStatusDm approvalStatusDm;
	private BookingRevision bookingRevision;
	private CBSUser cbsUser;
	
	@Before
	public void init() {
		template = mock(Template.class);
		emailRequest = createMailRequest();
	}

	@Test
	public void sendEmailForBookingApproval_shouldGetApprovalRequestTeplate_fromConfiguration() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		beforeSendEmailForBookingApprovalTests(false,false);
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
		verify(config).getTemplate("email.approval_request.ftl");
	}

	@Test
	public void sendEmailForBookingApproval_shouldFindApproverStatusDm_byId() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {

		beforeSendEmailForBookingApprovalTests(false, false);
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
		verify(approvalStatusDmRepository).findById(201L);
	}

	@Test
	public void sendEmailForBookingApproval_shouldSendEmailWithEmptyStrings_whenContentOfObjectsInBookingRevisionIsEmpty() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		beforeSendEmailForBookingApprovalTests(true, true);
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
		verify(config).getTemplate("email.approval_request.ftl");
	}

	@Test
	public void sendEmailForBookingApproval_shouldSendEmailWithEmptyStrings_whenObjectsInBookingRevisionAreNull() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		beforeSendEmailForBookingApprovalTests(true, true);
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
		verify(approvalStatusDmRepository).findById(201L);
	}

	@Test
	public void sendEmailForBookingApproval_shouldLogExceptionMessage_occuredDuringSendingEmail() {

		final BookingRevision bookingRevision = createBookingRevisionWithNullObjects();
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
	}
	
	@Test
	public void sendContractReceipt_shouldSendContractorReceiptEmail_fromContractReceiptTemplate() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		beforeSendContractReceipt();
		emailServiceImpl.sendContractReceipt(bookingRevision);
		verify(config).getTemplate("email.contractreceipt.ftl");
	}

	@Test
	public void sendContractReceipt_shouldSendContractorReceiptEmail_withCBSUserDetails() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		beforeSendContractReceipt();
		emailServiceImpl.sendContractReceipt(bookingRevision);
		verify(loggedInUserService).getLoggedInUserDetails();
	}

	@Test
	public void sendContractReceipt_shouldLogExceptionMessage_occuredDuringSendingEmail() {
		
		final BookingRevision bookingRevision = createBookingRevisionForContractReceipt();
		emailServiceImpl.sendContractReceipt(bookingRevision);
	}
	
	@Test
	public void shouldSendInternalResourceEmail() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		final InternalResourceEmailDto internalResourceEmailDto = createInternalResourceEmailDto();
		when(config.getTemplate("email.internalsource.ftl")).thenReturn(template);
		emailServiceImpl.sendInternalResourceEmail(internalResourceEmailDto);
		verify(config).getTemplate("email.internalsource.ftl");
	}
	
	@Test
	public void sendInternalResourceEmail_shouldLogExceptionMessage_occuredDuringSendingEmail() {
		
		final InternalResourceEmailDto internalResourceEmailDto = createInternalResourceEmailDto();
		emailServiceImpl.sendInternalResourceEmail(internalResourceEmailDto);
	}
	
	private void beforeSendEmailForBookingApprovalTests(boolean isEmptyContent, boolean isEmptyApprovalStatus) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		bookingRevision = createBookingRevision(isEmptyContent);
		approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalName("approver");
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");

		when(config.getTemplate("email.approval_request.ftl")).thenReturn(template);
		if(isEmptyApprovalStatus) {
			when(approvalStatusDmRepository.findById(201L)).thenReturn(Optional.empty());
		}
		else {
			when(approvalStatusDmRepository.findById(201L)).thenReturn(Optional.of(approvalStatusDm));
		}
	}
	
	private void beforeSendContractReceipt() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		bookingRevision = createBookingRevisionForContractReceipt();
		cbsUser = new CBSUser("cbsUser");
		cbsUser.setEmail("cbs.user@imagination.com");
		
		when(config.getTemplate("email.contractreceipt.ftl")).thenReturn(template);
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
	}

	private MailRequest createMailRequest() {

		MailRequest emailRequest = new MailRequest();
		emailRequest.setMailFrom("CBS@imagination.com");
		emailRequest.setMailTo(new String[] {"CBS_Receiver@imagination.com"});
		emailRequest.setSubject("Test mail");
		
		return emailRequest;
	}
	
	private BookingRevision createBookingRevision(boolean isEmptyContent) {
		
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setContractAmountAftertax(new BigDecimal("1000")); 
		
		ContractorEmployee contractEmployee = new ContractorEmployee(); 
		if(!isEmptyContent) {
			contractEmployee.setContractorEmployeeName("contractorEmployee"); 
		}
		bookingRevision.setContractEmployee(contractEmployee);

		Contractor contractor = new Contractor(); 
		if(!isEmptyContent) {
			contractor.setContractorName("Imagination"); 
		}
		bookingRevision.setContractor(contractor);
		bookingRevision.setRole(createRoleDm());
		
		SupplierTypeDm supplierType = new SupplierTypeDm();
		if(!isEmptyContent) {
			supplierType.setName("supplier"); 
		}
		bookingRevision.setSupplierType(supplierType);
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		bookingRevision.setContractedFromDate(timeStamp);
		bookingRevision.setContractedToDate(timeStamp);
		
		OfficeDm officeDm = new OfficeDm();
		if(!isEmptyContent) {
			officeDm.setOfficeName("NewOffice"); 
		}
		bookingRevision.setContractWorkLocation(officeDm);
		
		ReasonsForRecruiting reasonForRecruiting = new ReasonsForRecruiting();
		if(!isEmptyContent) {
			reasonForRecruiting.setReasonName("Specific skills Required"); 
		}
		bookingRevision.setReasonForRecruiting(reasonForRecruiting);
		
		if(!isEmptyContent) {
			List<BookingWorkTask> bookingWorkTasks = getBookingWorkTasks();
			bookingRevision.setBookingWorkTasks(bookingWorkTasks);
		}
		
		bookingRevision.setBooking(createBoooking());
		bookingRevision.setApprovalStatus(createApprovalStatusDm());

		return bookingRevision;
	}
	
	private BookingRevision createBookingRevisionWithNullObjects() {

		BookingRevision bookingRevision = new BookingRevision();
		
		bookingRevision.setRole(createRoleDm());
		bookingRevision.setSupplierType(new SupplierTypeDm());
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		bookingRevision.setContractedFromDate(timeStamp);
		bookingRevision.setContractedToDate(timeStamp);
		bookingRevision.setContractWorkLocation(new OfficeDm());
		bookingRevision.setReasonForRecruiting(new ReasonsForRecruiting());
		bookingRevision.setBooking(createBoooking());
		bookingRevision.setApprovalStatus(createApprovalStatusDm());

		return bookingRevision;
	}

	private List<BookingWorkTask> getBookingWorkTasks(){
		List<BookingWorkTask> bookingWorkTasks = new ArrayList<>();

		BookingWorkTask bookingWorkTask1 = new BookingWorkTask();
		bookingWorkTask1.setTaskName("TestTask");
		bookingWorkTask1.setTaskDeliveryDate(new Date(System.currentTimeMillis()));
		bookingWorkTask1.setTaskDateRate(500.50);
		bookingWorkTask1.setTaskTotalDays(10L);
		bookingWorkTask1.setTaskTotalAmount(1500.00);
		bookingWorkTasks.add(bookingWorkTask1);
		
		return bookingWorkTasks;
	}

	private RoleDm createRoleDm() {
		
		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		Discipline discipline = new Discipline();
		discipline.setDisciplineName("Creative");
		roleDm.setDiscipline(discipline);
		return roleDm;
	}
	
	private Booking createBoooking() {

		Booking booking = new Booking();
		booking.setBookingId(101L);
		booking.setChangedBy("changedByUser");
		return booking;
	}
	
	private ApprovalStatusDm createApprovalStatusDm() {
		
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalStatusId(201L);
		approvalStatusDm.setApprovalName("approver");
		return approvalStatusDm;
	}
	
	private BookingRevision createBookingRevisionForContractReceipt() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy("changedByUser");
		
		Contractor contractor = new Contractor();
		contractor.setEmail("contractor@imagination.com");
		bookingRevision.setContractor(contractor);

		bookingRevision.setJobNumber("Billable Template:123");
		bookingRevision.setJobname("Billable Template");
		
		Booking booking = new Booking();
		booking.setBookingId(101L);
		bookingRevision.setBooking(booking);

		bookingRevision.setCompletedAgreementPdf("Completed Agreement PDF File");
		return bookingRevision;
	}
	
	private InternalResourceEmailDto createInternalResourceEmailDto() {
		
		InternalResourceEmailDto internalResourceEmailDto = new InternalResourceEmailDto();
		internalResourceEmailDto.setDiscipline("Creative");
		internalResourceEmailDto.setRole("2D");
		internalResourceEmailDto.setJobNumber("Billable Template:123");
		internalResourceEmailDto.setJobName("Billable Template");
		internalResourceEmailDto.setContractedFromDate("27-04-2020");
		internalResourceEmailDto.setContractedToDate("27-05-2020");
		
		return internalResourceEmailDto;
	}
}
