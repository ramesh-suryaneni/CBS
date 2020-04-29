package com.imagination.cbs.service.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorWorkSite;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApproverTeamDetailDto;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.JobDetailDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.ContractorEmployeeRepository;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.repository.CurrencyRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.repository.RegionRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.repository.SiteOptionsRepository;
import com.imagination.cbs.repository.SupplierTypeRepository;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.CBSDateUtils;

@RunWith(MockitoJUnitRunner.class)
public class CreateBookingHelperTest {
	
	@InjectMocks
	private CreateBookingHelper createBookingHelper;
	
	@Mock
	private MaconomyService maconomyService;

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private RegionRepository regionRepository;

	@Mock
	private ContractorRepository contractorRepository;

	@Mock
	private SiteOptionsRepository siteOptionsRepository;

	@Mock
	private SupplierTypeRepository supplierTypeRepository;

	@Mock
	private RecruitingRepository recruitingRepository;

	@Mock
	private OfficeRepository officeRepository;

	@Mock
	private CurrencyRepository currencyRepository;

	@Mock
	private ContractorEmployeeRepository contractorEmployeeRepository;

	@Mock
	private BookingMapper bookingMapper;

	@Mock
	private LoggedInUserService loggedInUserService;

	
	@Before
	public void setUp() {
		final Region region = new Region();
		region.setRegionId(5555L);
		final ReasonsForRecruiting recruiting  = new ReasonsForRecruiting();
		recruiting.setReasonId(3333L);
		final OfficeDm office = new OfficeDm();
		office.setOfficeId(12345L);
		final SiteOptions siteOptions = new SiteOptions();
		siteOptions.setId(6666L); 
		final CBSUser user = new CBSUser("Pappu");
		final Booking booking = new Booking();
		final BookingRequest bookingRequest = createBookingRequest();
		bookingRequest.setSiteOptions(Arrays.asList("6666"));
		
		 when(loggedInUserService.getLoggedInUserDetails()).thenReturn(user);
		 when(bookingMapper.toBookingDomainFromBookingDto(Mockito.any(BookingRequest.class))).thenReturn(booking); 
		 when(officeRepository.findById(12345L)).thenReturn(Optional.of(office)); //2times
		 when(recruitingRepository.findById(3333L)).thenReturn(Optional.of(recruiting));
		 when(regionRepository.findById(5555L)).thenReturn(Optional.of(region));//2
		 when(siteOptionsRepository.findById(6666L)).thenReturn(Optional.of(siteOptions)); 
	}

	@Test
	public void shouldCall_getLoggedInUserDetails() throws IOException {	
		final CBSUser user = new CBSUser("Pappu");
		final BookingRequest bookingRequest = createBookingRequest();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(user);
		
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		
		assertEquals("Pappu", actual.getChangedBy());	 
	}
	
	@Test
	public void shouldCall_toBookingDomainFromBookingDto() {
		final BookingRequest bookingRequest = createBookingRequest();
		bookingRequest.setBookingDescription("Test Data");
		final Booking booking = new Booking();
		
		when(bookingMapper.toBookingDomainFromBookingDto(bookingRequest)).thenReturn(booking);
		
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
			
		verify(bookingMapper).toBookingDomainFromBookingDto(bookingRequest);
		
		assertEquals("Test Data", actual.getBookingDescription());
	}
	
	@Test
	public void shouldCall_findByIdFromOfficeRepository () {
		final BookingRequest bookingRequest = createBookingRequest();
		final OfficeDm office = new OfficeDm();
		office.setOfficeId(12345L);
		
		when(officeRepository.findById(12345L)).thenReturn(Optional.of(office)); //2times
	
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
		
		verify(officeRepository,times(2)).findById(12345L);
		
		assertEquals(new Long(12345), actual.getBookingRevisions().get(0).getCommisioningOffice().getOfficeId());
	}
	
	@Test
	public void shouldCall_findByIdFromRecruitingRepository(){
		final BookingRequest bookingRequest = createBookingRequest();
		final ReasonsForRecruiting recruiting  = new ReasonsForRecruiting();
		recruiting.setReasonId(3333L);
		
		when(recruitingRepository.findById(3333L)).thenReturn(Optional.of(recruiting));
		
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
		
		verify(recruitingRepository).findById(3333L);
		
		assertEquals(new Long(3333), actual.getBookingRevisions().get(0).getReasonForRecruiting().getReasonId());
	}
	
	@Test
	public void shouldCall_findByIdFromRegionRepository() {
		final BookingRequest bookingRequest = createBookingRequest();
		final Region region = new Region();
		region.setRegionId(5555L);
		
		when(regionRepository.findById(5555L)).thenReturn(Optional.of(region));//2
		 
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
			
		verify(regionRepository,times(2)).findById(5555L);
			
		assertEquals(new Long(5555), actual.getBookingRevisions().get(0).getCommOffRegion().getRegionId());
	}
	
	@Test
	public void shouldCall_findByIdFromSiteOptionsRepository() {
		final BookingRequest bookingRequest = createBookingRequest();
		final SiteOptions siteOptions = new SiteOptions();
		siteOptions.setId(6666L); 
		
		when(siteOptionsRepository.findById(6666L)).thenReturn(Optional.of(siteOptions)); 
		 
		Booking actual =  createBookingHelper.populateBooking(bookingRequest, 5L,false);
			
		verify(siteOptionsRepository).findById(6666L);
			
		assertEquals(new Long(6666), actual.getBookingRevisions().get(0).getContractorWorkSites().get(0).getSiteOptions().getId());
	}
	
	private BookingRequest createBookingRequest(){
		
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCommOffRegion("5555");
		bookingRequest.setContractorWorkRegion("5555");
		bookingRequest.setBookingDescription("Test Data");
		bookingRequest.setCommisioningOffice("12345");
		bookingRequest.setCommOffRegion("5555");	
		bookingRequest.setContractEmployeeId("5004");
		bookingRequest.setContractorId("6002");
		bookingRequest.setContractWorkLocation("12345");
		bookingRequest.setCurrencyId("103");
		bookingRequest.setInsideIr35("true");
		bookingRequest.setJobDeptName("2D");
		bookingRequest.setJobNumber("1111l");
		bookingRequest.setRate("154");
		bookingRequest.setReasonForRecruiting("3333");
		bookingRequest.setRoleId("4326");
		bookingRequest.setSupplierTypeId("7658");
		bookingRequest.setSiteOptions(Arrays.asList("6666"));
		bookingRequest.setContractedToDate("29/04/2020");
		bookingRequest.setContractedFromDate("29/04/2020");
		return bookingRequest;
	}

}
