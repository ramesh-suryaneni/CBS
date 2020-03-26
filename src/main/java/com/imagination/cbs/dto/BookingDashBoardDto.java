package com.imagination.cbs.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BookingDashBoardDto {
	
	private Long bookingId;
	private String jobname;
	private Timestamp contractedFromDate;
	private Timestamp contractedToDate;
	private String contractorName;
	private String roleName;
	private String changedBy;
	private Timestamp changedDate ;
		
}
