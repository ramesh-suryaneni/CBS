package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@Table(name="team")
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="team_id")
	private long teamId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="team_name")
	private String teamName;
//
//	//bi-directional one-to-one association to Booking
//	@OneToOne(mappedBy="team")
//	private Booking booking;

	//bi-directional one-to-one association to Approver
	@OneToOne(mappedBy="team")
	private Approver approver;

	public Team() {
	}

	public long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
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

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	// public Booking getBooking() {
	// return this.booking;
	// }
	//
	// public void setBooking(Booking booking) {
	// this.booking = booking;
	// }

	public Approver getApprover() {
		return this.approver;
	}

	public void setApprover(Approver approver) {
		this.approver = approver;
	}

}