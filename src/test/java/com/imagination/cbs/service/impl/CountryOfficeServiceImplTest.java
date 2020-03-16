package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.mapper.OfficeMapper;
import com.imagination.cbs.mapper.RegionMapper;
import com.imagination.cbs.repository.RegionRepository;
import com.imagination.cbs.repository.OfficeRepository;

@RunWith(MockitoJUnitRunner.class)
public class CountryOfficeServiceImplTest {

	@InjectMocks
	private CountryOfficeServiceImpl countryOfficeServiceImpl;

	@Mock
	private RegionRepository countryRepository;

	@Mock
	private RegionMapper countryMapper;

	@Mock
	private OfficeRepository officeRepository;

	@Mock
	private OfficeMapper officeMapper;

	@Test
	public void shouldReturnListOfCountries() {

		List<Region> listOfCountryDms = createRegionDmList();

		when(countryRepository.findAll()).thenReturn(listOfCountryDms);
		when(countryMapper.toListOfRegionDTO(listOfCountryDms)).thenReturn(createCountryDtoList());

		List<RegionDto> actualListOfCountryDto = countryOfficeServiceImpl.getAllCountries();

		assertEquals(7000, actualListOfCountryDto.get(0).getRegionId());
		assertEquals("Australia", actualListOfCountryDto.get(0).getRegionName());
		assertEquals("Australia", actualListOfCountryDto.get(0).getRegionDescription());

	}

	@Test
	public void shouldReturnListOfOfficesFromCountryId() {

		Long countryId = 7000l;

		List<OfficeDm> listOfOffices = createOfficeDmList();

		when(officeRepository.findByOfficeId(countryId)).thenReturn(listOfOffices);
		when(officeMapper.toListOfficeDTO(listOfOffices)).thenReturn(createOfficeDtoList());

		List<OfficeDto> actualListOfOfficeDto = countryOfficeServiceImpl.getAllOfficesInCountry(countryId);

		assertEquals(8000l, actualListOfOfficeDto.get(0).getOfficeId());
		assertEquals("Melbourne", actualListOfOfficeDto.get(0).getOfficeName());
		assertEquals(8001l, actualListOfOfficeDto.get(1).getOfficeId());
		assertEquals("Sydney", actualListOfOfficeDto.get(1).getOfficeName());
		assertEquals(2, actualListOfOfficeDto.size());
	}

	private List<RegionDto> createCountryDtoList() {
		List<RegionDto> listOfCountryDtos = new ArrayList<>();

		RegionDto regionDto = new RegionDto();
		regionDto.setRegionId(7000l);
		regionDto.setRegionName("Australia");
		regionDto.setRegionDescription("Australia");

		listOfCountryDtos.add(regionDto);
		return listOfCountryDtos;
	}

	private List<Region> createRegionDmList() {
		List<Region> listOfRegion = new ArrayList<Region>();

		Region countryDm = new Region();
		countryDm.setRegionId(7000l);
		countryDm.setRegionName("Australia");
		countryDm.setRegionDescription("Australia");

		listOfRegion.add(countryDm);
		return listOfRegion;
	}

	private List<OfficeDm> createOfficeDmList() {
		List<OfficeDm> listOfOfficeDm = new ArrayList<>();

		OfficeDm officeDm1 = new OfficeDm();
		officeDm1.setOfficeId(8000l);
		officeDm1.setOfficeName("Melbourne");
		officeDm1.setOfficeDescription("Melbourne");

		OfficeDm officeDm2 = new OfficeDm();
		officeDm2.setOfficeId(8001l);
		officeDm2.setOfficeName("Sydney");
		officeDm2.setOfficeDescription("Sydney");

		listOfOfficeDm.add(officeDm1);
		listOfOfficeDm.add(officeDm2);
		return listOfOfficeDm;
	}

	private List<OfficeDto> createOfficeDtoList() {
		List<OfficeDto> listOfOfficeDto = new ArrayList<>();

		OfficeDto officeDto1 = new OfficeDto();
		officeDto1.setOfficeId(8000l);
		officeDto1.setOfficeName("Melbourne");
		officeDto1.setOfficeDescription("Melbourne");

		OfficeDto officeDto2 = new OfficeDto();
		officeDto2.setOfficeId(8001l);
		officeDto2.setOfficeName("Sydney");
		officeDto2.setOfficeDescription("Sydney");

		listOfOfficeDto.add(officeDto1);
		listOfOfficeDto.add(officeDto2);
		return listOfOfficeDto;
	}

}
