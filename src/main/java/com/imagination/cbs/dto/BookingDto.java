package com.imagination.cbs.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingDto {
	private String bookingId;
	private String bookingDescription;
	private String changedBy;
	private String changedDate;

	private String jobNumber;

	private String bookingRevisionId;
	private String agreementDocumentId;
	private String agreementId;

	private String contractAmountAftertax;

	private String contractAmountBeforetax;

	private String contractedFromDate;

	private String contractedToDate;

	private String contractorContactDetails;

	private String contractorEmployeeName;

	private String contractorName;

	private String contractorSignedDate;

	private String contractorTotalAvailableDays;

	private String contractorTotalWorkingDays;

	private String contractorType;

	private String employeeContactDetails;

	private String employerTaxPercent;

	private String rate;

	private String jobDeptName;

	private String approverComments;

	private List<WorkDaysDto> workDays;

	private List<WorkTasksDto> workTasks;

	private String jobname;

	private TeamDto team;

	private ApprovalStatusDmDto approvalStatus;

	private ContractorRoleDto role;

	private DisciplineDto discipline;

	private ContractorDto contractor;

	private SupplierTypeDto supplierType;

	private SupplierWorkLocationTypeDto supplierWorkLocationType;

	private RecruitingDto reasonForRecruiting;

	private OfficeDto commisioningOffice;

	private OfficeDto contractWorkLocation;

	private CurrencyDto currency;

	private ContractorEmployeeSearchDto contractEmployee;
}