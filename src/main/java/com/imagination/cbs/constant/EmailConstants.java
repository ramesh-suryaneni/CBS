package com.imagination.cbs.constant;

/**
 * @author pappu.rout
 *
 */

public enum EmailConstants {

	
	DISCIPLINE("discipline"),
	ROLE("role"),
	DISCIPLINE_ID("disciplineId"),
	ROLE_ID("roleId"),
	CONTRCTOR_EMPLOYEE("contractorEmployee"),
	CONTRCTOR ("contractor"),
	SUPPLIER_TYPE("supplierType"),
	START_DATE("startDate"),
	END_DATE("endDate"),
	WORK_LOCATIONS("workLocations"),
	REASON_FOR_RECRUITING("reasonForRecruiting"),
	MAIL_ADDRESS("mailAddress"),
	
	TOTAL_COST("totalCost"),
	WORK_TASKS("workTasks"),
	
	REQUESTED_BY("requestedBy"),
	DOMAIN("@imagination.com"),
	EMAIL_ADDRESS("mailAddress"),
	JOB_NUMBER("jobNumber"),
	JOB_NAME("jobName"),

	CONTRACTOR_PDF_LINK("contractorPdf"),
	SCOPE_OF_WORK_LINK("scopeOfWorkPdf"),

	BOOKING_REQUEST_TEMPLATE("approval_request"),
	CONTRACT_RECEIPT_TEMPLATE("contractreceipt"),
	INTERNAL_SOURCE("internalsource"),
	PREFIX("email."),
	EXT(".ftl"),
	
	FROM_EMAIL("CBS@imagination.com"),
	TO_EMAIL("internal_sourcing.email_to"),
	INTERNAL_NOTIFICATION_SUBJECT_LINE("Internal Email Notification : Contractor Booking request #  "),
	CONTRACT_RCEIPT_SUBJECT_LINE("Completed Contract: JLR Experience Centre - Contractor - Booking # {0}");
	
	
	private String constantString;
	
	private EmailConstants(String constantString) {
		this.constantString = constantString;
	}

	public String getConstantString() {
		return constantString;
	}
	
	
	
}