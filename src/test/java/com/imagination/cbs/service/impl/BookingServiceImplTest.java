/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.ContractorWorkSite;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApprovalStatusDmDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorWorkSiteDto;
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.dto.RegionDto;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.dto.SiteOptionsDto;
import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.dto.TeamDto;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.helper.BookingApproveHelper;
import com.imagination.cbs.service.helper.BookingDeclineHelper;
import com.imagination.cbs.service.helper.BookingHrApproveHelper;
import com.imagination.cbs.service.helper.BookingSaveHelper;
import com.imagination.cbs.service.helper.CreateBookingHelper;
import com.imagination.cbs.service.helper.EmailHelper;
import com.imagination.cbs.util.AzureStorageUtility;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class BookingServiceImplTest {
	
	@InjectMocks
	private BookingServiceImpl bookingServiceImpl;
	
	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private BookingMapper bookingMapper;

	@Mock
	private LoggedInUserService loggedInUserService;

	@Mock
	private TeamMapper teamMapper;

	@Mock
	private DisciplineMapper disciplineMapper;

	@Mock
	private BookingRevisionRepository bookingRevisionRepository;

	@Mock
	private AzureStorageUtility azureStorageUtility;

	@Mock
	private AdobeSignService adobeSignService;

	@Mock
	private BookingApproveHelper bookingApproveHelper;

	@Mock
	private EmailHelper emailHelper;

	@Mock
	private BookingSaveHelper bookingSaveHelper;

	@Mock
	private BookingHrApproveHelper bookingHrApproveHelper;

	@Mock
	private BookingDeclineHelper bookingDeclineHelper;

	@Mock
	private CreateBookingHelper createBookingHelper;

	
	@Test
	public void shouldAddBookingDetails() {
		BookingRequest bookingRequest = createBookingRequest();
		Booking booking = createBooking();
		when(createBookingHelper.populateBooking(bookingRequest,1L, false)).thenReturn(booking);
		when(bookingRepository.save(booking)).thenReturn(booking);
		
		shouldRetriveBookingDetailsWhenBookingDomainPresentInDB();
		
	}
	
	@Test
	public void shouldRetriveBookingDetailsWhenBookingDomainPresentInDB() {
	
		Booking booking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(booking);
		BookingRevision bookingRevision =  createBookingRevision();
		BookingDto bookingDto = createBookingDto();
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		Booking booking1 = bookingDomain.get();
		when(bookingSaveHelper.getLatestRevision(booking1)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(bookingDto);
		
		bookingDto.setTeam(createTeamDtoFromMapper());
		bookingDto.setBookingId(String.valueOf(booking.getBookingId()));
		bookingDto.setDiscipline(createDisciplineDtoFromMapper());
		bookingDto.setBookingDescription(booking.getBookingDescription());
		bookingDto.setInsideIr35(bookingRevision.getRole().getInsideIr35());
		
		BookingDto actual = bookingServiceImpl.retrieveBookingDetails(1910l);
		assertEquals("2025", actual.getBookingRevisionId());
		assertEquals(8000l, actual.getCommisioningOffice().getOfficeId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldRetriveBookingDetailsWhenBookingDomainNotPresentInDB() {
		
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		bookingServiceImpl.retrieveBookingDetails(1910l);
	}
	private BookingRequest createBookingRequest()
	{
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setBookingDescription("Test Booking 10");
		bookingRequest.setCommisioningOffice("Yash");
		bookingRequest.setCommOffRegion("US");	
		bookingRequest.setContractEmployeeId("5004");
		bookingRequest.setContractorId("6002");
		bookingRequest.setContractWorkLocation("YashTech");
		bookingRequest.setCurrencyId("103");
		bookingRequest.setInsideIr35("true");
		bookingRequest.setJobDeptName("2D");
		bookingRequest.setJobNumber("1111l");
		bookingRequest.setRate("154");
		bookingRequest.setReasonForRecruiting("Specific Skills Required");
		bookingRequest.setRoleId("4326");
		bookingRequest.setSupplierTypeId("7658");
		return bookingRequest;
	}
	public TeamDto createTeamDtoFromMapper() {
		TeamDto teamDto = createTeamDto();
		when(teamMapper.toTeamDtoFromTeamDomain(createTeam())).thenReturn(teamDto);
		return teamDto;
	}
	public DisciplineDto createDisciplineDtoFromMapper() {
		DisciplineDto disciplineDto = createDisciplineDto();
		when(disciplineMapper.toDisciplineDtoFromDisciplineDomain(createDiscipline())).thenReturn(disciplineDto);
		return disciplineDto;
	}
	private DisciplineDto createDisciplineDto()
	{
		DisciplineDto disciplineDto = new DisciplineDto();
		disciplineDto.setDisciplineId(8000);
		disciplineDto.setDisciplineName("Creative");
		return disciplineDto;
	}
	private TeamDto createTeamDto(){
		TeamDto teamDto = new TeamDto();
		teamDto.setTeamId("1003");
		teamDto.setTeamName("ADM");
		return teamDto;
	}
	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setApprovalStatus(createApprovalStatusDm());
		booking.setBookingDescription("Test Data");
		booking.setBookingId(1910l);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setTeam(createTeam());
		return booking;
	}
	private ApprovalStatusDm createApprovalStatusDm() {
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalName("Draft");
		approvalStatusDm.setApprovalStatusId(1001l);
		return approvalStatusDm;
	}
	private Team createTeam(){
		Team team = new Team();
		team.setChangedBy("david.harman");
		team.setTeamId(1003l);
		team.setTeamName("ADM");
		return team;
	}
	private BookingRevision createBookingRevision() {
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setChangedBy("nafisa.ujloomwale");
		bookingRevision.setContractorTotalAvailableDays(11l);
		bookingRevision.setContractorTotalWorkingDays(10l);
		bookingRevision.setRole(createRoleDm());
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf - Production");
		bookingRevision.setJobNumber("100204205-02");
		bookingRevision.setRevisionNumber(5l);
		bookingRevision.setInsideIr35("true");
		return bookingRevision;
	}
	public RoleDm createRoleDm(){
		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		roleDm.setRoleId(3214l);
		roleDm.setInsideIr35("true");
		roleDm.setDiscipline(createDiscipline());
		return roleDm;
	}
	private Discipline createDiscipline(){
		Discipline discipline = new Discipline();
		discipline.setDisciplineId(8000l);
		discipline.setDisciplineName("Creative");
		return discipline;
	}
	private BookingDto createBookingDto()
	{
		BookingDto bookingDto = new BookingDto();
		bookingDto.setBookingId("1910");
		bookingDto.setBookingDescription("test data");
		bookingDto.setBookingRevisionId("2025");
		bookingDto.setCommisioningOffice(createCommisioningOffice());
		bookingDto.setInsideIr35("true");
		bookingDto.setJobDeptName("Admin");
		bookingDto.setJobname("JLR Experience Center");
		bookingDto.setJobNumber("0987");
		return bookingDto;
	}
	private OfficeDto createCommisioningOffice() {
		OfficeDto officeDto = new OfficeDto();
		officeDto.setOfficeId(8000l);
		officeDto.setOfficeName("Melbourne");
		officeDto.setOfficeDescription("Melbourne");
		return officeDto;
	}
	
	
}
