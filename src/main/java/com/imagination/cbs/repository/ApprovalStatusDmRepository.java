package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.ApprovalStatusDm;

public interface ApprovalStatusDmRepository extends JpaRepository<ApprovalStatusDm, Long> {

	public ApprovalStatusDm save(ApprovalStatusDm approver);
}
