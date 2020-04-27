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

	@Before
	public void init() {
		template = mock(Template.class);
	}
	
	@Test
	public void shouldSendEmailForBookingApproval() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		MailRequest emailRequest = createMailRequest();
		BookingRevision bookingRevision = createBookingRevision(false);
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalName("Akshay");
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");
		
		when(config.getTemplate("email.approval_request.ftl")).thenReturn(template);
		when(approvalStatusDmRepository.findById(201L)).thenReturn(Optional.of(approvalStatusDm));
		
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");

		verify(config).getTemplate("email.approval_request.ftl");
		verify(approvalStatusDmRepository).findById(201L);
	}

	@Test
	public void shouldSendEmailForBookingApprovalWithEmptyStringsWhenContentOfObjectsInBookingRevisionIsEmpty() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		MailRequest emailRequest = createMailRequest();
		BookingRevision bookingRevision = createBookingRevision(true);
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");
		
		when(config.getTemplate("email.approval_request.ftl")).thenReturn(template);
		when(approvalStatusDmRepository.findById(201L)).thenReturn(Optional.empty());
		
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");

		verify(config).getTemplate("email.approval_request.ftl");
		verify(approvalStatusDmRepository).findById(201L);
	}

	@Test
	public void shouldSendEmailForBookingApprovalWithEmptyStringsWhenObjectsInBookingRevisionAreNull() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, MessagingException {

		MailRequest emailRequest = createMailRequest();
		BookingRevision bookingRevision = createBookingRevisionWithNullObjects();
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");
		
		when(config.getTemplate("email.approval_request.ftl")).thenReturn(template);
		when(approvalStatusDmRepository.findById(201L)).thenReturn(Optional.empty());
		
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");

		verify(config).getTemplate("email.approval_request.ftl");
		verify(approvalStatusDmRepository).findById(201L);
	}

	@Test
	public void shouldLogExceptionMessageOccuredDuringEmailSending_sendEmailForBookingApproval() {

		MailRequest emailRequest = createMailRequest();
		BookingRevision bookingRevision = createBookingRevisionWithNullObjects();
		ReflectionTestUtils.setField(emailServiceImpl, "baseUrl", "https://imaginagtion.com/bookingSummary?bookingId={bookingId}&status={status}");
		
		emailServiceImpl.sendEmailForBookingApproval(emailRequest, bookingRevision, "approval_request");
	}
	
	@Test
	public void shouldSendContractorReceiptEmail() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		BookingRevision bookingRevision = createBookingRevisionForContractReceipt();
		CBSUser cbsUser = new CBSUser("Akshay");
		cbsUser.setEmail("akshay@imagination.com");
		
		when(config.getTemplate("email.contractreceipt.ftl")).thenReturn(template);
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		
		emailServiceImpl.sendContractReceipt(bookingRevision);
		
		verify(config).getTemplate("email.contractreceipt.ftl");
		verify(loggedInUserService).getLoggedInUserDetails();
	}
	
	@Test
	public void shouldLogExceptionMessageOccuredDuringEmailSending_sendContractReceipt() {
		
		BookingRevision bookingRevision = createBookingRevisionForContractReceipt();
		
		emailServiceImpl.sendContractReceipt(bookingRevision);
	}
	
	@Test
	public void shouldSendInternalResourceEmail() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		InternalResourceEmailDto internalResourceEmailDto = createInternalResourceEmailDto();
		
		when(config.getTemplate("email.internalsource.ftl")).thenReturn(template);
		
		emailServiceImpl.sendInternalResourceEmail(internalResourceEmailDto);

		verify(config).getTemplate("email.internalsource.ftl");
	}
	
	@Test
	public void shouldLogExceptionMessageOccuredDuringEmailSending_sendInternalResourceEmail() {
		
		InternalResourceEmailDto internalResourceEmailDto = createInternalResourceEmailDto();
		
		emailServiceImpl.sendInternalResourceEmail(internalResourceEmailDto);
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
			contractEmployee.setContractorEmployeeName("Akshay"); 
		}
		bookingRevision.setContractEmployee(contractEmployee);

		Contractor contractor = new Contractor(); 
		if(!isEmptyContent) {
			contractor.setContractorName("Imagination"); 
		}
		bookingRevision.setContractor(contractor);

		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		Discipline discipline = new Discipline();
		discipline.setDisciplineName("Creative");
		roleDm.setDiscipline(discipline);
		bookingRevision.setRole(roleDm);
		
		SupplierTypeDm supplierType = new SupplierTypeDm();
		if(!isEmptyContent) {
			supplierType.setName("Yash"); 
		}
		bookingRevision.setSupplierType(supplierType);
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		bookingRevision.setContractedFromDate(timeStamp);
		bookingRevision.setContractedToDate(timeStamp);
		
		OfficeDm officeDm = new OfficeDm();
		if(!isEmptyContent) {
			officeDm.setOfficeName("India"); 
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
		
		Booking booking = new Booking();
		booking.setBookingId(101L);
		booking.setChangedBy("Akshay");
		bookingRevision.setBooking(booking);
		
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalStatusId(201L);
		approvalStatusDm.setApprovalName("Mitul");
		bookingRevision.setApprovalStatus(approvalStatusDm);

		return bookingRevision;
	}
	
	private BookingRevision createBookingRevisionWithNullObjects() {

		BookingRevision bookingRevision = new BookingRevision();
		
		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		Discipline discipline = new Discipline();
		discipline.setDisciplineName("Creative");
		roleDm.setDiscipline(discipline);
		bookingRevision.setRole(roleDm);
		
		bookingRevision.setSupplierType(new SupplierTypeDm());
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		bookingRevision.setContractedFromDate(timeStamp);
		bookingRevision.setContractedToDate(timeStamp);
		
		bookingRevision.setContractWorkLocation(new OfficeDm());
		
		bookingRevision.setReasonForRecruiting(new ReasonsForRecruiting());
		
		Booking booking = new Booking();
		booking.setBookingId(101L);
		booking.setChangedBy("Akshay");
		bookingRevision.setBooking(booking);
		
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalStatusId(201L);
		approvalStatusDm.setApprovalName("Mitul");
		bookingRevision.setApprovalStatus(approvalStatusDm);

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
	
	private BookingRevision createBookingRevisionForContractReceipt() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy("Akshay");
		
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
