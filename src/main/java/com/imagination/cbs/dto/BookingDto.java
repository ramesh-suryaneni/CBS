package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class BookingDto {
	private Long bookingId;
	private String bookingDescription;
	private String changedBy;
	private String changedDate;
	private Long roleId;
	private Long disciplineId;
	private String startDate;
	private String endDate;
	private Long jobNumber;
}
