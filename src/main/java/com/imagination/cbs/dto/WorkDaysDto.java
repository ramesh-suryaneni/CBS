package com.imagination.cbs.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class WorkDaysDto {
	private String monthName;
	
	@Pattern(regexp = "[0-9]+", message = "Month Working Days should be numeric only")
	private String monthWorkingDays;
	private String bookingRevisionId;
	private String changedBy;
	private String changedDate;
}
