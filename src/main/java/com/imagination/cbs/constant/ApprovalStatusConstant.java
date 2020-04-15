package com.imagination.cbs.constant;

/**
 * @author pravin.budage
 *
 */
public enum ApprovalStatusConstant {
	APPROVAL_DRAFT(1001L),
	APPROVAL_1(1002L),
	APPROVAL_2(1003L),
	APPROVAL_3(1004L),
	APPROVAL_SENT_TO_HR(1005L),
	APPROVAL_SENT_FOR_CONTRACTOR(1006L),
	APPROVAL_CONTRACT_SIGNED(1007L),
	APPROVAL_PO_GENERATED(1008L),
	APPROVAL_COMPLETED(1009L),
	APPROVAL_CANCELLED(1010L),
	APPROVAL_REJECTED(1011L);
	
	private Long approvalStatusId;

	private ApprovalStatusConstant(Long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public Long getApprovalStatusId() {
		return approvalStatusId;
	}

}
