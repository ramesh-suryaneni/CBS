package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class WorkDaysDto {
	private String monthName;
	private String monthWorkingDays;
	private String bookingRevisionId;
	private String changedBy;
	private String changedDate;
}
