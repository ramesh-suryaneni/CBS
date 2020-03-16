package com.imagination.cbs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the booking_revision database table.
 * 
 */
@Entity
@Table(name = "booking_revision")
@NamedQuery(name = "BookingRevision.findAll", query = "SELECT b FROM BookingRevision b")
public class BookingRevision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "booking_revision_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingRevisionId;

	// bi-directional many-to-one association to Booking
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@Column(name = "revision_number")
	private Long revisionNumber;

	// bi-directional one-to-one association to ContractorEmployeeRole
	@OneToOne
	@JoinColumn(name = "contractor_employee_role_id")
	private ContractorEmployeeRole contractorEmployeeRole;

	@Column(name = "contracted_from_date")
	private Timestamp contractedFromDate;

	@Column(name = "contracted_to_date")
	private Timestamp contractedToDate;

	@Column(name = "changed_by")
	private String changedBy;

	// bi-directional one-to-one association to CurrencyDm
	@OneToOne(mappedBy = "bookingRevision", cascade = CascadeType.ALL)
	private CurrencyDm currencyDm;

	@Column(name = "job_number")
	private Long jobNumber;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "rate")
	private BigDecimal rate;

	// bi-directional one-to-one association to ApprovalStatusDm
	@OneToOne
	@JoinColumn(name = "approval_status_id")
	private ApprovalStatusDm approvalStatusDm;

	// bi-directional one-to-one association to ContractorEmployeeRating
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_revision_id")
	private ContractorEmployeeRating contractorEmployeeRating;

	@Column(name = "contractor_signed_date")
	private Timestamp contractorSignedDate;

	@Column(name = "currency_id")
	private Long currencyId;

	@Column(name = "agreement_document_id")
	private Long agreementDocumentId;

	@Column(name = "agreement_id")
	private Long agreementId;

	public BookingRevision() {
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getBookingRevisionId() {
		return this.bookingRevisionId;
	}

	public void setBookingRevisionId(Long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
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

	public Long getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(Long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public ContractorEmployeeRole getContractorEmployeeRole() {
		return this.contractorEmployeeRole;
	}

	public void setContractorEmployeeRole(ContractorEmployeeRole contractorEmployeeRole) {
		this.contractorEmployeeRole = contractorEmployeeRole;
	}

	public CurrencyDm getCurrencyDm() {
		return this.currencyDm;
	}

	public void setCurrencyDm(CurrencyDm currencyDm) {
		this.currencyDm = currencyDm;
	}

	public ApprovalStatusDm getApprovalStatusDm() {
		return this.approvalStatusDm;
	}

	public void setApprovalStatusDm(ApprovalStatusDm approvalStatusDm) {
		this.approvalStatusDm = approvalStatusDm;
	}

	public ContractorEmployeeRating getContractorEmployeeRating() {
		return this.contractorEmployeeRating;
	}

	public void setContractorEmployeeRating(ContractorEmployeeRating contractorEmployeeRating) {
		this.contractorEmployeeRating = contractorEmployeeRating;
	}

	public Timestamp getContractorSignedDate() {
		return contractorSignedDate;
	}

	public void setContractorSignedDate(Timestamp contractorSignedDate) {
		this.contractorSignedDate = contractorSignedDate;
	}

	public Long getAgreementDocumentId() {
		return agreementDocumentId;
	}

	public void setAgreementDocumentId(Long agreementDocumentId) {
		this.agreementDocumentId = agreementDocumentId;
	}

	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

}