package com.imagination.cbs.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BookingDashBoardDto {

	private Long bookingId;
	private String roleName;
	private String jobname;
	private String contractorName;
	private Timestamp contractedToDate;
	private Timestamp contractedFromDate;
	private String changedBy;
	private Timestamp changedDate;
	
}
	

