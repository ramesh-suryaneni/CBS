package com.imagination.cbs.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imagination.cbs.dto.ReasonsForRecruitingDto;
import com.imagination.cbs.service.impl.ReasonsForRecruitingServiceImpl;

@RestController
@RequestMapping("/reasonsForRecruting")
public class ReasonsForRecruitingController {
	
	@Autowired
	private ReasonsForRecruitingServiceImpl reasonsForRecruitingServiceImpl;
	
	@GetMapping
	public List<ReasonsForRecruitingDto> findReasonForRecruitings()
	{
		return reasonsForRecruitingServiceImpl.getReasonsForRecruiting();
	}

}
