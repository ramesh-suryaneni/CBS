package com.imagination.cbs.service;

import java.util.List;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.dto.OfficeDto;

public interface RegionOfficeService {

	public List<RegionDto> getAllRegions();

	public List<OfficeDto> getAllOfficesInRegion(Long regionId);
}
