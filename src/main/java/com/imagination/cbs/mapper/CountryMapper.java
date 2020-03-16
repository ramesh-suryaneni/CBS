package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.CountryDm;
import com.imagination.cbs.dto.CountryDto;


@Mapper(componentModel = "spring")
public interface CountryMapper {
	
	public List<CountryDto> toListOfCountryDTO(List<CountryDm> listOfCountry);
}
