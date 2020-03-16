package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Team;
/**
 * The persistent class for the booking database table.
 * 
 */

@Entity
@Table(name="booking")
@NamedQuery(name="Booking.findAll", query="SELECT b from Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	private long bookingId;
	
	@Column(name="booking_description")
	private String bookingDescription;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;

	@OneToOne
	@JoinColumn(name="approval_status_id")
	private ApprovalStatusDm approvalStatusDm;
	
	@OneToOne
	@JoinColumn(name="team_id")
	private Team team;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<BookingRevision> bookingRevisions;
	
	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingDescription() {
		return bookingDescription;
	}

	public void setBookingDescription(String bookingDescription) {
		this.bookingDescription = bookingDescription;
	}

	public Timestamp getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public ApprovalStatusDm getApprovalStatusDm() {
		return approvalStatusDm;
	}

	public void setApprovalStatusDm(ApprovalStatusDm approvalStatusDm) {
		this.approvalStatusDm = approvalStatusDm;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<BookingRevision> getBookingRevisions() {
		return bookingRevisions;
	}

	public void setBookingRevisions(List<BookingRevision> bookingRevisions) {
		this.bookingRevisions = bookingRevisions;
	}
	
}