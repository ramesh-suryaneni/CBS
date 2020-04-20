package com.imagination.cbs.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.Region;
import com.imagination.cbs.dto.RegionDto;

@Mapper(componentModel = "spring")
public interface RegionMapper {

	public List<RegionDto> toListOfRegionDTO(List<Region> listOfCountry);
}
