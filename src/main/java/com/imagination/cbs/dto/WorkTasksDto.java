package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class WorkTasksDto {
	private String taskName;
	private String taskDeliveryDate;
	private String taskDateRate;
	private String taskTotalDays;
	private String taskTotalAmount;
	private String bookingRevisionId;
	private String changedBy;
	private String changedDate;
}
