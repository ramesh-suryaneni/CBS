package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

import com.imagination.cbs.domain.EmployeeMapping;
/**
 * The persistent class for the approver_override_jobs database table.
 * 
 */
@Entity
@Table(name="approver_override_jobs")
@NamedQuery(name="ApproverOverrideJobs.findAll", query="SELECT a FROM ApproverOverrideJobs a")
public class ApproverOverrideJobs implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="override_id")
	private long overrideId;
	
	@Column(name="job_number")
	private String jobNumber;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;
	
	@OneToOne
	@JoinColumn(name="employee_id")
	private EmployeeMapping employeeMapping;

	public long getOverrideId() {
		return overrideId;
	}

	public void setOverrideId(long overrideId) {
		this.overrideId = overrideId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
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

	public EmployeeMapping getEmployeeMapping() {
		return employeeMapping;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employeeMapping = employeeMapping;
	}
	
}
