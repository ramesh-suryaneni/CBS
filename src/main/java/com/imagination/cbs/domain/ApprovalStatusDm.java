package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the approval_status_dm database table.
 * 
 */
@Entity
@Table(name="approval_status_dm")
@NamedQuery(name="ApprovalStatusDm.findAll", query="SELECT a FROM ApprovalStatusDm a")
public class ApprovalStatusDm implements Serializable {

	private static final long serialVersionUID = -4679925113436317122L;

	@Id
	@Column(name="approval_status_id")
	private long approvalStatusId;

	@Column(name="approval_description")
	private String approvalDescription;

	@Column(name="approval_name")
	private String approvalName;

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

}