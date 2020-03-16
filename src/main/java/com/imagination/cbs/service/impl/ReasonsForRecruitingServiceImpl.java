package com.imagination.cbs.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imagination.cbs.dto.ReasonsForRecruitingDto;
import com.imagination.cbs.mapper.ReasonsForRecruitingMapper;
import com.imagination.cbs.repository.ReasonsForRecruitingRepository;
import com.imagination.cbs.service.ReasonsForRecruitingService;

@Service("reasonsForRecruitingService")
public class ReasonsForRecruitingServiceImpl implements ReasonsForRecruitingService{

	@Autowired
	private ReasonsForRecruitingRepository reasonsForRecruitingRepository;
	
	@Autowired(required = true)
	private ReasonsForRecruitingMapper reasonsForRecruitingMapper;
	
	@Override
	public List<ReasonsForRecruitingDto> getReasonsForRecruiting() {
	
		return reasonsForRecruitingMapper.toListOfReasonsForRecruitingDto(reasonsForRecruitingRepository.findAll());
	}

}
