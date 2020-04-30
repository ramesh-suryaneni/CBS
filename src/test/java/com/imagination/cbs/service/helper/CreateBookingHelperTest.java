package com.imagination.cbs.service.helper;

import static org.junit.Assert.assertEquals;
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

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.exception.CBSApplicationException;
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
	public void setUp() throws Exception {
		
		 when(loggedInUserService.getLoggedInUserDetails()).thenReturn( new CBSUser("Pappu"));
		 when(roleRepository.findById(4326L)).thenReturn(Optional.of(new RoleDm()));
		 when(bookingMapper.toBookingDomainFromBookingDto(Mockito.any(BookingRequest.class))).thenReturn(new Booking());  
		 when(officeRepository.findById(12345L)).thenReturn(Optional.of(new OfficeDm())); 
		 when(recruitingRepository.findById(3333L)).thenReturn(Optional.of(new ReasonsForRecruiting()));
		 when(regionRepository.findById(5555L)).thenReturn(Optional.of(new Region()));
		 when(siteOptionsRepository.findById(6666L)).thenReturn(Optional.of(new SiteOptions())); 
		/* when(maconomyService.getMaconomyJobNumberAndDepartmentsDetails(
				 Mockito.eq("1111"), Mockito.any(JobDataDto.class),Mockito.eq("jobs/data;jobnumber="),Mockito.eq(""))).thenReturn(jobDataDto); */
		 /*when(maconomyService.getMaconomyJobNumberAndDepartmentsDetails(Mockito.eq(""),
						Mockito.any(ApproverTeamDto.class), Mockito.eq("Department"), Mockito.eq("2D"))).thenReturn(Mockito.any(ApproverTeamDto.class));
		 */	 
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
	
	@Test(expected = CBSApplicationException.class)
	public void shouldThrowCBSApplicationExceptionWhwnStatusIsTrue() {
		final BookingRequest bookingRequest = createBookingRequest();
		RoleDm roleDm = new RoleDm();	
		
		when(roleRepository.findById(4326L)).thenReturn(Optional.of(roleDm)); 
	
		createBookingHelper.populateBooking(bookingRequest, 5L,true);
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
		bookingRequest.setEmployerTaxPercent("");
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
		WorkDaysDto work = new WorkDaysDto();
		work.setMonthName("June");
		work.setMonthWorkingDays("23");
		WorkTasksDto task = new WorkTasksDto();
		task.setTaskDeliveryDate("29/4/20");
		task.setTaskDateRate("74");
		task.setTaskTotalDays("32");
		task.setTaskTotalAmount("453");
		List< WorkDaysDto> listDays = new ArrayList<>();
		List< WorkTasksDto> listTask = new ArrayList<>();
		listDays.add(work);
		listTask.add(task);
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCommOffRegion("5555");
		bookingRequest.setContractorWorkRegion("5555");
		bookingRequest.setBookingDescription("Test Data");
		bookingRequest.setCommisioningOffice("12345");
		bookingRequest.setCommOffRegion("5555");	
		bookingRequest.setContractEmployeeId("5004");
		bookingRequest.setContractorId("6002");
		bookingRequest.setContractWorkLocation("12345");
		bookingRequest.setContractorTotalAvailableDays("30");
		bookingRequest.setContractorTotalWorkingDays("28");
		bookingRequest.setContractAmountAftertax("82");
		bookingRequest.setContractAmountBeforetax("54");
		bookingRequest.setEmployerTaxPercent("67");
		bookingRequest.setCurrencyId("103");
		bookingRequest.setInsideIr35("true");
		bookingRequest.setJobDeptName("2D");
		bookingRequest.setJobNumber("1111");
		bookingRequest.setRate("154");
		bookingRequest.setReasonForRecruiting("3333");
		bookingRequest.setRoleId("4326");
		bookingRequest.setSupplierTypeId("7658");
		bookingRequest.setSiteOptions(Arrays.asList("6666"));
		bookingRequest.setContractedToDate("29/04/2020");
		bookingRequest.setContractedFromDate("29/04/2020");
		bookingRequest.setWorkDays(listDays);
		bookingRequest.setWorkTasks(listTask);
		bookingRequest.setSiteOptions(Arrays.asList("6666"));
		return bookingRequest;
	}

}
