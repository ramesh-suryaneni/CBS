package com.imagination.cbs.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingDto {
	private String bookingId;
	private String bookingDescription;
	private String changedBy;
	private String changedDate;

	@NotNull(message = "Role Id cannot be null")
	private String roleId;

	@NotNull(message = "Discipline Id cannot be null")
	private String disciplineId;

	@NotNull(message = "Start Date cannot be null")
	private String startDate;

	@NotNull(message = "End Date cannot be null")
	private String endDate;

	@NotNull(message = "Job Number cannot be null")
	private String jobNumber;

	private String teamId;

	private String statusId;

	private String bookingRevisionId;
	private String agreementDocumentId;
	private String agreementId;
	private String approvalStatusId;
	private String contractAmountAftertax;

	private String contractAmountBeforetax;

	private String contractedFromDate;

	private String contractedToDate;

	private String contractorContactDetails;

	private String contractorEmployeeName;

	private String contractorEmployeeRoleId;

	private String contractorId;

	private String contractorName;

	private String contractorSignedDate;

	private String contractorTotalAvailableDays;

	private String contractorTotalWorkingDays;

	private String contractorType;

	private String currencyId;

	private String employeeContactDetails;

	private String employerTaxPercent;

	private String insideIr35;

	private String knownAs;

	private String officeDescription;

	private String officeId;

	private String rate;

	@NotNull(message = "Revision No cannot be null")
	private String revisionNumber;

	private String jobDeptName;

	private String approverComments;

	private List<WorkDaysDto> workDays;

	private List<WorkTasksDto> workTasks;

}