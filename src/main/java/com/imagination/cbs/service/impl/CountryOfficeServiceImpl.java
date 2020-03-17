package com.imagination.cbs.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.mapper.RegionMapper;
import com.imagination.cbs.mapper.OfficeMapper;
import com.imagination.cbs.repository.RegionRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.service.RegionOfficeService;

@Service("countryOfficeService")
public class CountryOfficeServiceImpl implements RegionOfficeService {

	@Autowired
	private RegionRepository countryRepository;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private OfficeMapper officeMapper;

	@Autowired
	private RegionMapper regionMapper;

	@Override
	public List<RegionDto> getAllCountries() {
		return regionMapper.toListOfRegionDTO(countryRepository.findAll());
	}

	@Override
	public List<OfficeDto> getAllOfficesInCountry(Long countryId) {
		return officeMapper.toListOfficeDTO(officeRepository.findByOfficeId(countryId));
	}
}
