package com.imagination.cbs.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imagination.cbs.dto.CountryDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.mapper.CountryMapper;
import com.imagination.cbs.mapper.OfficeMapper;
import com.imagination.cbs.repository.CountryRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.service.CountryOfficeService;

@Service("countryOfficeService")
public class CountryOfficeServiceImpl implements CountryOfficeService{

	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private OfficeMapper officeMapper;
	
	@Autowired
	private CountryMapper countryMapper;
	
	
	@Override
	public List<CountryDto> getAllCountries() {
		
		return countryMapper.toListOfCountryDTO(countryRepository.findAll());
	}

	@Override
	public List<OfficeDto> getAllOfficesInCountry(Long countryId) {
		
		return officeMapper.toListOfficeDTO(officeRepository.findByCountryDmCountryId(countryId));
	
	}

}
