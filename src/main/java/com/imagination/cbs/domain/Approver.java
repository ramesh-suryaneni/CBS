package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the approver database table.
 * 
 */
@Entity
@Table(name="approver")
@NamedQuery(name="Approver.findAll", query="SELECT a FROM Approver a")
public class Approver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="approver_id")
	private long approverId;

	@Column(name="approver_order")
	private long approverOrder;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@OneToOne
	@JoinColumn(name="employe_id")
	private EmployeeMapping employeeMapping;

	@OneToOne
	@JoinColumn(name="team_id")
	private Team team;

	public Approver() {
	}

	public long getApproverId() {
		return this.approverId;
	}

	public void setApproverId(long approverId) {
		this.approverId = approverId;
	}

	public long getApproverOrder() {
		return this.approverOrder;
	}

	public void setApproverOrder(long approverOrder) {
		this.approverOrder = approverOrder;
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

	public EmployeeMapping getEmployeeMapping() {
		return employeeMapping;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employeeMapping = employeeMapping;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}