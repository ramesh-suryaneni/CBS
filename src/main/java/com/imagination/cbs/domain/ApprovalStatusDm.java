package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the approval_status_dm database table.
 * 
 */
@Entity
@Table(name="approval_status_dm")
@NamedQuery(name="ApprovalStatusDm.findAll", query="SELECT a FROM ApprovalStatusDm a")
public class ApprovalStatusDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="approval_status_id")
	private Long approvalStatusId;

	@Column(name="approval_description")
	private String approvalDescription;

	@Column(name="approval_name")
	private String approvalName;

	//bi-directional one-to-one association to Booking
	@OneToOne(mappedBy="approvalStatusDm")
	private Booking booking;

	//bi-directional one-to-one association to BookingRevision
	@OneToOne(mappedBy="approvalStatusDm")
	private BookingRevision bookingRevision;

	public ApprovalStatusDm() {
	}

	public Long getApprovalStatusId() {
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(Long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getApprovalDescription() {
		return this.approvalDescription;
	}

	public void setApprovalDescription(String approvalDescription) {
		this.approvalDescription = approvalDescription;
	}

	public String getApprovalName() {
		return this.approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public BookingRevision getBookingRevision() {
		return this.bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}

}