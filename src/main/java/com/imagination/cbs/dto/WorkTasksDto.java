package com.imagination.cbs.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class WorkTasksDto {
	private String taskId;;
	private String taskName;

	private String taskDeliveryDate;

	@NotNull(message = "Task Date Rate cannot be null")
	private String taskDateRate;

	@Pattern(regexp = "[0-9]+", message = "Task Total Days should be numeric only")
	private String taskTotalDays;
	private String taskTotalAmount;
	private String bookingRevisionId;
	private String changedBy;
	private String changedDate;
}
