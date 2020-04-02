package com.imagination.cbs.constant;

/**
 * @author pravin.budage
 *
 */
public enum ApprovalStatusConstant {
	APPROVAL_DRAFT("Draft"),
	APPROVAL_1("Waiting on Approval 1"),
	APPROVAL_2("Waiting on Approval 2"),
	APPROVAL_3("Waiting on Approval 3"),
	APPROVAL_SENT_TO_HR("Sent to HR"),
	APPROVAL_SENT_FOR_CONTRACTOR("Contract Sent For Contractor Approval"),
	APPROVAL_CONTRACT_SIGNED("Contract Signed"),
	APPROVAL_PO_GENERATED("PO Generated"),
	APPROVAL_COMPLETED("Completed"),
	APPROVAL_CANCELLED("Cancelled"),
	APPROVAL_REJECTED("Rejected");
	

	private String status;

	private ApprovalStatusConstant(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
