package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


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
	private Long teamId;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="changed_by")
	private String changedBy;
	
	@Column(name="team_name")
	private String teamName;

	//bi-directional many-to-one association to Approver
	@OneToMany(mappedBy="team")
	private List<Approver> approvers;

	//bi-directional many-to-one association to Booking
	@OneToMany(mappedBy="team")
	private List<Booking> bookings;

	public Team() {
	}

	public Long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Timestamp getChangedDate() {
		return this.changedDate;
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

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<Approver> getApprovers() {
		return this.approvers;
	}

	public void setApprovers(List<Approver> approvers) {
		this.approvers = approvers;
	}

	public Approver addApprover(Approver approver) {
		getApprovers().add(approver);
		approver.setTeam(this);

		return approver;
	}

	public Approver removeApprover(Approver approver) {
		getApprovers().remove(approver);
		approver.setTeam(null);

		return approver;
	}

	public List<Booking> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Booking addBooking(Booking booking) {
		getBookings().add(booking);
		booking.setTeam(this);

		return booking;
	}

	public Booking removeBooking(Booking booking) {
		getBookings().remove(booking);
		booking.setTeam(null);

		return booking;
	}

}