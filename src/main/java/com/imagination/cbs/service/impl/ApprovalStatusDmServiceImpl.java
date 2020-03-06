package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.service.ApprovalStatusDmService;

@Repository("approvalStatusDmServiceImpl")
public class ApprovalStatusDmServiceImpl implements ApprovalStatusDmService {

	@Autowired
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	@Override
	public ApprovalStatusDm storeApprovalStatusDmDetails(ApprovalStatusDm approvalStatusDm) {
		return approvalStatusDmRepository.save(approvalStatusDm);
	}

}
