/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.dto.SiteOptionsDto;
import com.imagination.cbs.mapper.SiteOptionsMapper;
import com.imagination.cbs.repository.SiteOptionsRepository;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class WorkSiteOptionsServiceImplTest {
	
	@InjectMocks
	private WorkSiteOptionsServiceImpl workSiteOptionsServiceImpl;
	
	
	@Mock
	private SiteOptionsRepository siteOptionsRepository;

	@Mock
	private SiteOptionsMapper siteOptionsMapper;
	
	@Test
	public void shouldReturnListOfAllWorkSitesPresentInDB(){
		
		List<SiteOptions> siteOptions = new ArrayList<>();
		
		SiteOptions siteOption = new SiteOptions();
		siteOption.setId(1L);
		siteOption.setName("Clients premises");
		siteOptions.add(siteOption);
		
		List<SiteOptionsDto> siteOptionsDtoList = new ArrayList<>();
		
		SiteOptionsDto siteOptionsDto = new SiteOptionsDto();
		siteOptionsDto.setId("1L");
		siteOptionsDto.setName("Clients premises");
		
		siteOptionsDtoList.add(siteOptionsDto);
		
		when(siteOptionsRepository.findAll()).thenReturn(siteOptions);
		when(siteOptionsMapper.convertToDto(siteOptions)).thenReturn(siteOptionsDtoList);
		
		List<SiteOptionsDto> actual = workSiteOptionsServiceImpl.fetchWorkSites();
		
		assertEquals("1L", actual.get(0).getId());
		assertEquals("Clients premises", actual.get(0).getName());
	}


}
