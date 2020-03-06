package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the approver database table.
 * 
 */
@Entity
@Table(name = "approver")
@NamedQuery(name = "Approver.findAll", query = "SELECT a FROM Approver a")
public class Approver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approver_id")
	private long approverId;

	@Column(name = "approver_number")
	private long approverNumber;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "team_id")
	private long teamId;

	// bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team team;

	// bi-directional one-to-one association to EmployeeMapping
	@OneToOne(mappedBy = "approver")
	private EmployeeMapping employeeMapping;

	public Approver() {
	}

	public long getApproverId() {
		return approverId;
	}

	public void setApproverId(long approverId) {
		this.approverId = approverId;
	}

	public long getApproverNumber() {
		return this.approverNumber;
	}

	public void setApproverNumber(long approverNumber) {
		this.approverNumber = approverNumber;
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

	public long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public EmployeeMapping getEmployeeMapping() {
		return this.employeeMapping;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employeeMapping = employeeMapping;
	}

}