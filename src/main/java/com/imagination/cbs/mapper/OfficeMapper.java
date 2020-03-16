package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.dto.OfficeDto;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

	public List<OfficeDto> toListOfficeDTO(List<OfficeDm> listOfOffices);
}
