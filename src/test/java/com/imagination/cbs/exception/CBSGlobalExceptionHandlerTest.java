package com.imagination.cbs.exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.imagination.cbs.dto.ErrorResponse;

@RunWith(MockitoJUnitRunner.class)
public class CBSGlobalExceptionHandlerTest {
	
	
	@InjectMocks
	private CBSGlobalExceptionHandler cbsGlobalExceptionHandler;
	
	
	
	@Test
	public void shouldHandleCBSApplicationExceptionWhenBookingIDISNotValid() {
		
		ResponseEntity<ErrorResponse> actual = cbsGlobalExceptionHandler.handlerCBSApplicationException(new CBSApplicationException("Please provide valid Booking ID"));
		
		assertEquals(400, actual.getStatusCodeValue());
		assertEquals("Please provide valid Booking ID", actual.getBody().getMessage());
	}
	
	@Test
	public void shouldHandlehandleValidationExceptionWhenJobNumberIsNnull() {
		
		MethodParameter methodParameter = mock(MethodParameter.class);
		BindingResult bindingResult = mock(BindingResult.class);
		
		FieldError error = new FieldError("jobNumber", "jobNumber", "Job Number cannot be null");
		List<ObjectError> filedErrorList = new ArrayList<>();
		filedErrorList.add(error);
		MethodArgumentNotValidException argumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);
		
		when(bindingResult.getAllErrors()).thenReturn(filedErrorList);
		
		Map<String, String> actual = cbsGlobalExceptionHandler.handleValidationException(argumentNotValidException);
		
		verify(bindingResult).getAllErrors();
		
		assertEquals("Job Number cannot be null", actual.get("jobNumber"));
	}
	
	@Test
	public void handleResourceNotFoundExceptionWhenNoBookingAvailable() {
		
		ResponseEntity<String> actual = cbsGlobalExceptionHandler.handleResourceNotFoundException(new RuntimeException("No Booking Available with this number"));
		
		assertEquals("No Booking Available with this number", actual.getBody());
	}
	
	@Test
	public void handleCBSUnAuthorizedExceptionWhenUseIsNotAuthorizedToApproveBooking() {
		
		String actual = cbsGlobalExceptionHandler.handleCBSUnAuthorizedException(new RuntimeException("Not Authorized to perform this operation; insufficient previllages"));
		
		assertEquals("Not Authorized to perform this operation; insufficient previllages", actual);
	}
	
	
	
	
	
	
	
	
}
