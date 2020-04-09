package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.dto.SiteOptionsDto;

@Mapper(componentModel = "spring")
public interface SiteOptionsMapper {
	public List<SiteOptionsDto> convertToDto(List<SiteOptions> siteOptions);
}
