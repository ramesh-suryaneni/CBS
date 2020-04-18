package com.imagination.cbs.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.imagination.cbs.domain.Region;
import com.imagination.cbs.dto.RegionDto;

@Mapper(componentModel = "spring")
public interface RegionMapper {

	RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

	public List<RegionDto> toListOfRegionDTO(List<Region> listOfCountry);
}
