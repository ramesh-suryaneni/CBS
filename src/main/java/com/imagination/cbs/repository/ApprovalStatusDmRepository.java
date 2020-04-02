package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ApprovalStatusDm;

/**
 * @author pravin.budage
 *
 */
@Repository("approvalStatusDmRepository")
public interface ApprovalStatusDmRepository extends JpaRepository<ApprovalStatusDm, Long> {
	public ApprovalStatusDm findByApprovalName(String approvalName);
}
