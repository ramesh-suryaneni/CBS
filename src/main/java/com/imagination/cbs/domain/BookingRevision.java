package com.imagination.cbs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;




/**
 * The persistent class for the booking_revision database table.
 * 
 */
@Entity
@Table(name="booking_revision")
@NamedQuery(name="BookingRevision.findAll", query="SELECT b FROM BookingRevision b")
public class BookingRevision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="booking_revision_id")
	private long bookingRevisionId;

	@Column(name="agreement_document_id")
	private String agreementDocumentId;

	@Column(name="agreement_id")
	private String agreementId;

	@Column(name="approval_status_id")
	private long approvalStatusId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contract_amount_aftertax")
	private BigDecimal contractAmountAftertax;

	@Column(name="contract_amount_beforetax")
	private BigDecimal contractAmountBeforetax;

	@Column(name="contracted_from_date")
	private Timestamp contractedFromDate;

	@Column(name="contracted_to_date")
	private Timestamp contractedToDate;

	@Column(name="contractor_contact_details")
	private String contractorContactDetails;

	@Column(name="contractor_employee_name")
	private String contractorEmployeeName;

	@Column(name="contractor_employee_role_id")
	private long contractorEmployeeRoleId;

	@Column(name="contractor_id")
	private long contractorId;

	@Column(name="contractor_name")
	private String contractorName;

	@Column(name="contractor_signed_date")
	private Timestamp contractorSignedDate;

	@Column(name="contractor_total_available_days")
	private long contractorTotalAvailableDays;

	@Column(name="contractor_total_working_days")
	private long contractorTotalWorkingDays;

	@Column(name="contractor_type")
	private String contractorType;

	@Column(name="currency_id")
	private long currencyId;

	@Column(name="employee_contact_details")
	private String employeeContactDetails;

	@Column(name="employer_tax_percent")
	private BigDecimal employerTaxPercent;

	@Column(name="inside_ir35")
	private String insideIr35;

	@Column(name="job_number")
	private String jobNumber;

	@Column(name="known_as")
	private String knownAs;

	@Column(name="office_description")
	private String officeDescription;

	@Column(name="office_id")
	private String officeId;

	private BigDecimal rate;
	
	@ManyToOne
	@JoinColumn(name="booking_id")
	private Booking booking;

	@Column(name="revision_number")
	private long revisionNumber;

	public BookingRevision() {
	}

	public long getBookingRevisionId() {
		return this.bookingRevisionId;
	}

	public void setBookingRevisionId(long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
	}

	public String getAgreementDocumentId() {
		return this.agreementDocumentId;
	}

	public void setAgreementDocumentId(String agreementDocumentId) {
		this.agreementDocumentId = agreementDocumentId;
	}

	public String getAgreementId() {
		return this.agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public long getApprovalStatusId() {
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getChangedBy() {
		return this.changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Timestamp getChangedDate() {
		return this.changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public BigDecimal getContractAmountAftertax() {
		return this.contractAmountAftertax;
	}

	public void setContractAmountAftertax(BigDecimal contractAmountAftertax) {
		this.contractAmountAftertax = contractAmountAftertax;
	}

	public BigDecimal getContractAmountBeforetax() {
		return this.contractAmountBeforetax;
	}

	public void setContractAmountBeforetax(BigDecimal contractAmountBeforetax) {
		this.contractAmountBeforetax = contractAmountBeforetax;
	}

	public Timestamp getContractedFromDate() {
		return this.contractedFromDate;
	}

	public void setContractedFromDate(Timestamp contractedFromDate) {
		this.contractedFromDate = contractedFromDate;
	}

	public Timestamp getContractedToDate() {
		return this.contractedToDate;
	}

	public void setContractedToDate(Timestamp contractedToDate) {
		this.contractedToDate = contractedToDate;
	}

	public String getContractorContactDetails() {
		return this.contractorContactDetails;
	}

	public void setContractorContactDetails(String contractorContactDetails) {
		this.contractorContactDetails = contractorContactDetails;
	}

	public String getContractorEmployeeName() {
		return this.contractorEmployeeName;
	}

	public void setContractorEmployeeName(String contractorEmployeeName) {
		this.contractorEmployeeName = contractorEmployeeName;
	}

	public long getContractorEmployeeRoleId() {
		return this.contractorEmployeeRoleId;
	}

	public void setContractorEmployeeRoleId(long contractorEmployeeRoleId) {
		this.contractorEmployeeRoleId = contractorEmployeeRoleId;
	}

	public long getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Timestamp getContractorSignedDate() {
		return this.contractorSignedDate;
	}

	public void setContractorSignedDate(Timestamp contractorSignedDate) {
		this.contractorSignedDate = contractorSignedDate;
	}

	public long getContractorTotalAvailableDays() {
		return this.contractorTotalAvailableDays;
	}

	public void setContractorTotalAvailableDays(long contractorTotalAvailableDays) {
		this.contractorTotalAvailableDays = contractorTotalAvailableDays;
	}

	public long getContractorTotalWorkingDays() {
		return this.contractorTotalWorkingDays;
	}

	public void setContractorTotalWorkingDays(long contractorTotalWorkingDays) {
		this.contractorTotalWorkingDays = contractorTotalWorkingDays;
	}

	public String getContractorType() {
		return this.contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public String getEmployeeContactDetails() {
		return this.employeeContactDetails;
	}

	public void setEmployeeContactDetails(String employeeContactDetails) {
		this.employeeContactDetails = employeeContactDetails;
	}

	public BigDecimal getEmployerTaxPercent() {
		return this.employerTaxPercent;
	}

	public void setEmployerTaxPercent(BigDecimal employerTaxPercent) {
		this.employerTaxPercent = employerTaxPercent;
	}

	public String getInsideIr35() {
		return this.insideIr35;
	}

	public void setInsideIr35(String insideIr35) {
		this.insideIr35 = insideIr35;
	}

	public String getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getKnownAs() {
		return this.knownAs;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	public String getOfficeDescription() {
		return this.officeDescription;
	}

	public void setOfficeDescription(String officeDescription) {
		this.officeDescription = officeDescription;
	}

	public String getOfficeId() {
		return this.officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public long getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
}