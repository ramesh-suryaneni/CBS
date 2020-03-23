package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.mapper.RecruitingMapper;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.service.RecruitingService;

@Service("recruitingService")
public class RecruitingServiceImpl implements RecruitingService {

	@Autowired
	private RecruitingRepository recruitingRepository;

	@Autowired
	private RecruitingMapper recruitingMapper;

	@Override
	public List<RecruitingDto> getAllReasonForRecruiting() {
		return recruitingMapper.toListRecruitingDto(recruitingRepository.findAll());
	}

}
