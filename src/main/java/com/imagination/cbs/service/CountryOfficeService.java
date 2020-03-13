package com.imagination.cbs.service;

import java.util.List;
import com.imagination.cbs.dto.CountryDto;
import com.imagination.cbs.dto.OfficeDto;

public interface CountryOfficeService {

	public List<CountryDto> getAllCountries();

	public List<OfficeDto> getAllOfficesInCountry(Long countryId);
}
