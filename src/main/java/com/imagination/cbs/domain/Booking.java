package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@Table(name = "booking")
@NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private long bookingId;

	@Column(name = "booking_description")
	private String bookingDescription;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	// bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	// bi-directional one-to-one association to ApprovalStatusDm
	@OneToOne
	@JoinColumn(name = "status_id")
	private ApprovalStatusDm approvalStatusDm;

	// bi-directional many-to-one association to BookingRevision
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
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
		if (CollectionUtils.isEmpty(this.bookingRevisions)) {
			return this.bookingRevisions = new ArrayList<BookingRevision>();
		}
		return this.bookingRevisions;
	}

	public void setBookingRevisions(List<BookingRevision> bookingRevisions) {
		this.bookingRevisions = bookingRevisions;
	}

	public void addBookingRevision(BookingRevision bookingRevision) {
		getBookingRevisions().add(bookingRevision);
		bookingRevision.setBooking(this);
	}

	public BookingRevision removeBookingRevision(BookingRevision bookingRevision) {
		getBookingRevisions().remove(bookingRevision);
		return bookingRevision;
	}

}