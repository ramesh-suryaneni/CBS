/*package com.imagination.cbs.controller;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.service.RegionOfficeService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegionOfficeControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@MockBean
	private RegionOfficeService regionOfficeService;

	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
	}
	
	@WithMockUser("/developer")
	@Test
	public void shouldReturnAllRegions() throws Exception {

		List<RegionDto> listOfRegionDto = new ArrayList<RegionDto>();
		listOfRegionDto.add(createRegionDto());

		when(regionOfficeService.getAllRegions()).thenReturn(listOfRegionDto);

		this.mockMvc.perform(get("/regions").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].regionName", comparesEqualTo("EMEA")));

		verify(regionOfficeService).getAllRegions();
	}

	@WithMockUser("/developer")
	@Test
	public void shouldReturnAllOfficesBasedOnRegion() throws Exception {

		List<OfficeDto> listOfficeDtos = new ArrayList<>();
		listOfficeDtos.add(createOfficeDto());

		long regionId = 2;
		when(regionOfficeService.getAllOfficesInRegion(regionId)).thenReturn(listOfficeDtos);

		this.mockMvc.perform(get("/regions/2/offices").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].officeName", comparesEqualTo("Melbourne")));

		verify(regionOfficeService).getAllOfficesInRegion(regionId);

	}

	public RegionDto createRegionDto() {
		RegionDto regionDto = new RegionDto();
		regionDto.setRegionId(new Long(1));
		regionDto.setRegionName("EMEA");
		regionDto.setRegionDescription("Europe & Middle East");

		return regionDto;
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