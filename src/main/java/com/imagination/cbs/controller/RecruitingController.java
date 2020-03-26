package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.service.RecruitingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/recruiting_reasons")
public class RecruitingController {

	@Autowired
	private RecruitingService recruitingService;

	@GetMapping
	public List<RecruitingDto> getAllReasonForRecruiting() {
		return recruitingService.getAllReasonForRecruiting();
	}

}
