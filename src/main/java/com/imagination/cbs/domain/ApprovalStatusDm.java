package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the approval_status_dm database table.
 * 
 */
@Entity
@Table(name="approval_status_dm")
@NamedQuery(name="ApprovalStatusDm.findAll", query="SELECT a FROM ApprovalStatusDm a")
public class ApprovalStatusDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="approval_status_id")
	private long approvalStatusId;

	@Column(name="approval_description")
	private String approvalDescription;

	@Column(name="approval_name")
	private String approvalName;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	public ApprovalStatusDm() {
	}

	public long getApprovalStatusId() {
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getApprovalDescription() {
		return this.approvalDescription;
	}

	public void setApprovalDescription(String approvalDescription) {
		this.approvalDescription = approvalDescription;
	}

	public String getApprovalName() {
		return this.approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
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

}