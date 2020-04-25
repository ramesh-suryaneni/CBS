package com.imagination.cbs.controller;
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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.SiteOptionsDto;
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.WorkSiteOptionsService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(WorkSiteOptionsController.class)
@ContextConfiguration(classes= {TestConfig.class})
public class WorkSiteOptionsControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;
	
	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;
	
	@MockBean
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WorkSiteOptionsService workSiteOptionsService;
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnSiteOptionDtoListForFetchWorkSiteOptions() throws Exception {
		
		when(workSiteOptionsService.fetchWorkSites()).thenReturn(createSiteOptionDtoList());
		
		this.mockMvc.perform(get("/work-site-options").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].name",comparesEqualTo("Clients premises")))
					.andExpect(jsonPath("$[1].id", comparesEqualTo("2")));
		
		verify(workSiteOptionsService).fetchWorkSites();
	}
	
	private List<SiteOptionsDto> createSiteOptionDtoList(){
		
		SiteOptionsDto siteOptionsDto1 = new SiteOptionsDto();
		SiteOptionsDto siteOptionsDto2 = new SiteOptionsDto();
		siteOptionsDto1.setId("1");
		siteOptionsDto1.setName("Clients premises");
		siteOptionsDto2.setId("2");
		siteOptionsDto2.setName("Home Office");
		
		List<SiteOptionsDto> siteOptionDtoList = new ArrayList<SiteOptionsDto>();
		siteOptionDtoList.add(siteOptionsDto1);
		siteOptionDtoList.add(siteOptionsDto2);
		
		return siteOptionDtoList;
		
	}

}
