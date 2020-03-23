package com.imagination.cbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.RecruitingDto;

@Service("recruitingService")
public interface RecruitingService {

	List<RecruitingDto> getAllReasonForRecruiting();
}
