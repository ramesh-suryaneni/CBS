package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the approver database table.
 * 
 */
@Entity
@Table(name = "approver")
public class Approver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approver_id")
	private Long approverId;

	@Column(name = "approver_order")
	private Long approverOrder;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private EmployeeMapping employee;

	public Approver() {
	}

	public Long getApproverId() {
		return this.approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public Long getApproverOrder() {
		return this.approverOrder;
	}

	public void setApproverOrder(Long approverOrder) {
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

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public EmployeeMapping getEmployee() {
		return this.employee;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employee = employeeMapping;
	}
}