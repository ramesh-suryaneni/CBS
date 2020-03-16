package com.imagination.cbs.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.service.RegionOfficeService;

@RestController
@RequestMapping("/countries")
public class CountryOfficeController {

	@Autowired
	private RegionOfficeService countryOfficeServiceImpl;
	
	@GetMapping
	public List<RegionDto> findAllCountries()
	{
		return countryOfficeServiceImpl.getAllCountries();
	}
	
	@GetMapping("/{countryId}/offices")
	public List<OfficeDto> findOfficesInCountry(@PathVariable("countryId") Long countryId)
	{
		return countryOfficeServiceImpl.getAllOfficesInCountry(countryId);
		
	}
}
