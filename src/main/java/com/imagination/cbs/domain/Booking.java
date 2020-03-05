package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@Table(name="booking")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	
	private static final long serialVersionUID = 7994482241189555535L;

	@Id
	@Column(name="booking_id")
	private long bookingId;

	@Column(name="booking_description")
	private String bookingDescription;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="status_id")
	private long statusId;

	@Column(name="team_id")
	private long teamId;

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

	public long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

}