/*package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.service.impl.CountryOfficeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CountryOfficeController.class)
public class CountryOfficeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CountryOfficeServiceImpl countryOfficeServiceImpl;

	@Test
	public void shouldReturnAllCountries() throws Exception {

//		List<RegionDto> listOfCountryDto = new ArrayList<RegionDto>();
//		listOfCountryDto.add(createCountryDto());
//
//		when(countryOfficeServiceImpl.getAllCountries()).thenReturn(listOfCountryDto);
//
//		mvc.perform(get("/countries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].countryName", comparesEqualTo("Australia")));
//
//		verify(countryOfficeServiceImpl).getAllCountries();
	}

	@Test
	public void shouldReturnAllOfficesBasedOnCountryId() throws Exception {

		List<OfficeDto> listOfficeDtos = new ArrayList<>();
		listOfficeDtos.add(createOfficeDto());

		long countryId = 7000;
		when(countryOfficeServiceImpl.getAllOfficesInCountry(countryId)).thenReturn(listOfficeDtos);

		mvc.perform(get("/countries/7000/offices").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].officeName", comparesEqualTo("Melbourne")));

		verify(countryOfficeServiceImpl).getAllOfficesInCountry(countryId);

	}

	public RegionDto createCountryDto() {
		RegionDto countryDto = new RegionDto();
		countryDto.setRegionId(new Long(7000));
		countryDto.setRegionName("Australia");
		countryDto.setRegionDescription("Australia");

		return countryDto;
	}

	public OfficeDto createOfficeDto() {
		OfficeDto officeDto = new OfficeDto();
		officeDto.setOfficeId(new Long(8000));
		officeDto.setOfficeName("Melbourne");
		officeDto.setOfficeDescription("Melbourne");

		return officeDto;
	}

}
*/