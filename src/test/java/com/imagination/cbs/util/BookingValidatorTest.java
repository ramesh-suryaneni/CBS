package com.imagination.cbs.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;

@RunWith(MockitoJUnitRunner.class)
public class BookingValidatorTest {

	@InjectMocks
	private BookingValidator bookingValidator;
	
	@Mock
	private Errors errors;
	
	private BookingRequest bookingRequest;

	@Test
	public void validate_shouldRegisterAnError_ifBookingRequestAttributesAndWorkDaysAreNull() {
		bookingRequest = createEmptyBookingRequestWithWorkDays();
		bookingValidator.validate(bookingRequest, errors);
	}

	@Test
	public void validate_shouldNotRegisterAnError_ifBookingRequestAttributesAreNotNull() {
		bookingRequest = createBookingRequestWithWorkDays();
		bookingValidator.validate(bookingRequest, errors);
	}

	@Test
	public void validate_shouldRegisterAnError_ifBookingRequestAttributeAndWorkTasksAreNull() {
		bookingRequest = createEmptyBookingRequestWithTasks();
		bookingValidator.validate(bookingRequest, errors);
	}

	@Test
	public void validate_shouldNotRegisterAnError_ifBookingRequestAttributeAndWorkTasksAreNotNull() {
		bookingRequest = createBookingRequestWithTasks();
		bookingValidator.validate(bookingRequest, errors);
	}

	@Test
	public void supports_shouldReturnTrue_forBookingRequest() {
		
		boolean actual = bookingValidator.supports(BookingRequest.class);
		assertTrue(actual);
	}

	@Test
	public void supports_shouldReturnFalse_whenNoBookingRequest() {
		
		boolean actual = bookingValidator.supports(BookingValidatorTest.class);
		assertFalse(actual);
	}

	private BookingRequest createEmptyBookingRequestWithWorkDays() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setInsideIr35("true");
		
		List<WorkDaysDto> workDays = new ArrayList<>();
		workDays.add(new WorkDaysDto());
		bookingRequest.setWorkDays(workDays);
		return bookingRequest;
	}
	
	private BookingRequest createBookingRequestWithWorkDays() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setInsideIr35("true");
		
		bookingRequest.setContractWorkLocation("WorkLocation");
		bookingRequest.setContractorTotalAvailableDays("10");
		bookingRequest.setContractAmountAftertax("1000");
		bookingRequest.setEmployerTaxPercent("50");
		bookingRequest.setCommisioningOffice("New Office");
		
		List<WorkDaysDto> workDays = new ArrayList<>();
		WorkDaysDto workDay = new WorkDaysDto();
		workDay.setMonthName("Jan");
		workDay.setMonthWorkingDays("31");
		workDays.add(workDay);
		bookingRequest.setWorkDays(workDays);
		return bookingRequest;
	}
	
	private BookingRequest createEmptyBookingRequestWithTasks() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setInsideIr35("false");
		
		List<WorkTasksDto> workTasks = new ArrayList<>();
		workTasks.add(new WorkTasksDto());
		bookingRequest.setWorkTasks(workTasks);
		return bookingRequest;
	}

	private BookingRequest createBookingRequestWithTasks() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setInsideIr35("false");
		
		List<WorkTasksDto> workTasks = new ArrayList<>();
		WorkTasksDto workTask = new WorkTasksDto();
		workTask.setTaskId("Task100");
		workTask.setTaskName("TestTask");
		workTask.setTaskDeliveryDate("300");
		workTask.setTaskDateRate("100");
		workTask.setTaskTotalDays("20");
		workTask.setTaskTotalAmount("1500");
	
		workTasks.add(workTask);
		bookingRequest.setWorkTasks(workTasks);
		return bookingRequest;
	}
}