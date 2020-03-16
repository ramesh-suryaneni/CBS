package com.imagination.cbs.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingDto {
	private String bookingDescription;
	private String changedBy;
	private String changedDate;

	@NotNull(message = "Role Id cannot be null")
	private Long roleId;

	@NotNull(message = "Discipline Id cannot be null")
	private Long disciplineId;

	@NotNull(message = "Start Date cannot be null")
	private String startDate;

	@NotNull(message = "End Date cannot be null")
	private String endDate;

	@NotNull(message = "Job Number cannot be null")
	private String jobNumber;
}
