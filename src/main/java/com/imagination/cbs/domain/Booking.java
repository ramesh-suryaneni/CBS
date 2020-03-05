package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@Table(name="booking")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="booking_id")
	private long bookingId;

	@Column(name="booking_description")
	private String bookingDescription;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	//bi-directional one-to-one association to ApprovalStatusDm
	@OneToOne
	@JoinColumn(name="status_id")
	private ApprovalStatusDm approvalStatusDm;

	//bi-directional many-to-one association to BookingRevision
	@OneToMany(mappedBy="booking")
	private List<BookingRevision> bookingRevisions;

	public Booking() {
	}

	public long getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingDescription() {
		return this.bookingDescription;
	}

	public void setBookingDescription(String bookingDescription) {
		this.bookingDescription = bookingDescription;
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

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public ApprovalStatusDm getApprovalStatusDm() {
		return this.approvalStatusDm;
	}

	public void setApprovalStatusDm(ApprovalStatusDm approvalStatusDm) {
		this.approvalStatusDm = approvalStatusDm;
	}

	public List<BookingRevision> getBookingRevisions() {
		return this.bookingRevisions;
	}

	public void setBookingRevisions(List<BookingRevision> bookingRevisions) {
		this.bookingRevisions = bookingRevisions;
	}

	public BookingRevision addBookingRevision(BookingRevision bookingRevision) {
		getBookingRevisions().add(bookingRevision);
		bookingRevision.setBooking(this);

		return bookingRevision;
	}

	public BookingRevision removeBookingRevision(BookingRevision bookingRevision) {
		getBookingRevisions().remove(bookingRevision);
		bookingRevision.setBooking(null);

		return bookingRevision;
	}

}