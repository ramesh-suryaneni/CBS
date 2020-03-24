package com.imagination.cbs.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.service.RegionOfficeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/regions")
public class RegionOfficeController {

	@Autowired
	private RegionOfficeService countryOfficeService;
	
	@GetMapping
	public List<RegionDto> findAllCountries()
	{
		return countryOfficeService.getAllRegions();
	}
	
	@GetMapping("/{regionId}/offices")
	public List<OfficeDto> findOfficesInCountry(@PathVariable("regionId") Long regionId)
	{
		return countryOfficeService.getAllOfficesInRegion(regionId);
		
	}
}
