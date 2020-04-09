package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.dto.SiteOptionsDto;
import com.imagination.cbs.mapper.SiteOptionsMapper;
import com.imagination.cbs.repository.SiteOptionsRepository;
import com.imagination.cbs.service.WorkSiteOptionsService;

@Service("workSiteOptionsService")
public class WorkSiteOptionsServiceImpl implements WorkSiteOptionsService {

	@Autowired
	private SiteOptionsRepository siteOptionsRepository;

	@Autowired
	private SiteOptionsMapper siteOptionsMapper;

	@Override
	public List<SiteOptionsDto> fetchWorkSites() {
		List<SiteOptions> siteOptions = siteOptionsRepository.findAll();
		return siteOptionsMapper.convertToDto(siteOptions);
	}

}
