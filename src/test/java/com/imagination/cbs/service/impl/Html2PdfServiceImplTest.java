package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.RoleDm;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class Html2PdfServiceImplTest {

	@InjectMocks
	Html2PdfServiceImpl html2PdfServiceImpl;
	
	@Mock
	private Configuration config;
	
	private Template template;

	@Before
	public void init() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		template = mock(Template.class);
	}
	
	@Test
	public void shouldReturnNullByteArrayOutputStreamWhenExceptionOccured() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		final BookingRevision latestBookingRevision = createBookingRevision(false);
		
		when(config.getTemplate("pdf.agreement.ftl")).thenReturn(template);
		
		ByteArrayOutputStream byteArrayOutputStream = html2PdfServiceImpl.generateAgreementPdf(latestBookingRevision);
		
		verify(config).getTemplate("pdf.agreement.ftl");
		
		assertNull(byteArrayOutputStream);
	}

	@Test
	public void shouldReturnNullByteArrayOutputStreamWhenExceptionOccuredWithEmptyValuesInBookingRevision() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		
		final BookingRevision latestBookingRevision = createBookingRevision(true);
		
		when(config.getTemplate("pdf.agreement.ftl")).thenReturn(template);
		
		ByteArrayOutputStream byteArrayOutputStream = html2PdfServiceImpl.generateAgreementPdf(latestBookingRevision);
		
		verify(config).getTemplate("pdf.agreement.ftl");
		
		assertNull(byteArrayOutputStream);
	}

	private BookingRevision createBookingRevision(boolean isEmpty) {
		
		BookingRevision bookingRevision = new BookingRevision();
		if(!isEmpty) {
			bookingRevision.setContractAmountAftertax(new BigDecimal("1000")); 
		}

		Contractor contractor = createContractor();
		if(!isEmpty) {
			List<BookingWorkTask> bookingWorkTasks = getBookingWorkTasks();
			bookingRevision.setBookingWorkTasks(bookingWorkTasks);
		}
		
		RoleDm role = new RoleDm();
		role.setRoleName("2D");
		role.setInsideIr35("true");
		if(isEmpty) {
			role.setInsideIr35("false");
		}
		Discipline discipline = new Discipline();
		discipline.setDisciplineName("Cerative");
		role.setDiscipline(discipline);
		
		Booking booking = new Booking();
		booking.setChangedBy("changedByUser");
		
		bookingRevision.setContractor(contractor);
		bookingRevision.setRole(role);
		bookingRevision.setBooking(booking);
		return bookingRevision;
	}
	
	private Contractor createContractor() {
		
		Contractor contractor = new Contractor();
		contractor.setAddressLine1("Address Line 1");
		contractor.setAddresLine2("Address Line 2");
		contractor.setAddresLine3("Address Line 3");
		contractor.setPostalCode("100-102");
		contractor.setPostalDistrict("Disctrict");
		contractor.setCountry("Country");
		return contractor;
	}
	
	private List<BookingWorkTask> getBookingWorkTasks(){
		List<BookingWorkTask> bookingWorkTasks = new ArrayList<>();

		BookingWorkTask bookingWorkTask1 = new BookingWorkTask();
		bookingWorkTask1.setTaskId(2001L);
		bookingWorkTask1.setTaskName("TestTask");
		bookingWorkTask1.setTaskDeliveryDate(new Date(System.currentTimeMillis()));
		bookingWorkTask1.setTaskDateRate(500.50);
		bookingWorkTask1.setTaskTotalDays(10L);
		bookingWorkTask1.setTaskTotalAmount(1500.00);
		bookingWorkTasks.add(bookingWorkTask1);
		
		return bookingWorkTasks;
	}
}
