package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.service.ApprovalStatusDmService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/approvalstatusdm")
public class ApprovalStatusDmController {

	@Autowired
	private ApprovalStatusDmService approvalStatusDmService;

	@PostMapping(value = "/store", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApprovalStatusDm> saveApprovalStatusDmDetails(@RequestBody ApprovalStatusDm approvalStatusDm) {
		ApprovalStatusDm savedApprover = approvalStatusDmService.storeApprovalStatusDmDetails(approvalStatusDm);
		return new ResponseEntity<ApprovalStatusDm>(savedApprover, HttpStatus.CREATED);
	}
}
