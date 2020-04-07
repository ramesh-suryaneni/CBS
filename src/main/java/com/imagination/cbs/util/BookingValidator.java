package com.imagination.cbs.util;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;

@Component
public class BookingValidator implements Validator {

	private static String BAD_ERROR_CODE = HttpStatus.BAD_REQUEST.toString();

	private void validateWorkTasks(List<WorkTasksDto> workTasks, Errors errors) {
		for (WorkTasksDto work : workTasks) {
			if (work.getTaskId() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Id cannot be null");
			}
			if (work.getTaskName() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Name cannot be null");
			}
			if (work.getTaskDeliveryDate() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Delivery Date cannot be null");
			}
			if (work.getTaskDateRate() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Date Rate cannot be null");
			}
			if (work.getTaskTotalDays() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Total Days cannot be null");
			}
			if (work.getTaskTotalAmount() == null) {
				errors.rejectValue("workTasks", BAD_ERROR_CODE, "Task Total Amount cannot be null");
			}
		}
	}

	private void validateWorkDays(List<WorkDaysDto> workDays, Errors errors) {
		for (WorkDaysDto work : workDays) {
			if (work.getMonthName() == null) {
				errors.rejectValue("workDays", BAD_ERROR_CODE, "Month Name cannot be null");
			}
			if (work.getMonthWorkingDays() == null) {
				errors.rejectValue("workDays", BAD_ERROR_CODE, "Month Working Days cannot be null");
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return BookingRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BookingRequest bookingRequest = (BookingRequest) target;
		boolean insideIr35 = Boolean.parseBoolean(bookingRequest.getInsideIr35());
		if (insideIr35 && bookingRequest.getContractWorkLocation() == null) {
			errors.rejectValue("contractWorkLocation", BAD_ERROR_CODE, "Contract Work Location cannot be null");
		}
		if (insideIr35 && bookingRequest.getContractorTotalAvailableDays() == null) {
			errors.rejectValue("contractWorkLocation", BAD_ERROR_CODE, "Contract Work Location cannot be null");
		}
		if (insideIr35 && bookingRequest.getContractAmountAftertax() == null) {
			errors.rejectValue("contractAmountAfterTax", BAD_ERROR_CODE, "Contract Amount Tax Location cannot be null");
		}
		if (insideIr35 && bookingRequest.getEmployerTaxPercent() == null) {
			errors.rejectValue("employerTaxPercent", BAD_ERROR_CODE, "Employer Tax Percent cannot be null");
		}
		if (!insideIr35) {
			validateWorkTasks(bookingRequest.getWorkTasks(), errors);
		}
		if (insideIr35) {
			validateWorkDays(bookingRequest.getWorkDays(), errors);
		}
		if (insideIr35 && bookingRequest.getCommisioningOffice() == null) {
			errors.rejectValue("commisioningOffice", BAD_ERROR_CODE, "Commisioning Office cannot be null");
		}
	}
}
