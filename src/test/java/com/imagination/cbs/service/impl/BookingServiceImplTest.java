/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.StringJoiner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.TeamDto;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.security.CBSUser;
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
		BookingDto actualBookingDto = bookingServiceImpl.addBookingDetails(bookingRequest);
		
		assertEquals("2025", actualBookingDto.getBookingRevisionId());
		verify(createBookingHelper,times(1)).populateBooking(bookingRequest,1l, false);
		verify(bookingRepository,times(1)).save(booking);
	}
	
	@Test
	public void shouldUpdateBookingDetailsWhenBookingDomainPresentInDB() {

		BookingRequest bookingRequest = createBookingRequest();
		Booking newBooking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(newBooking);
		BookingRevision bookingRevision =  createBookingRevision();
		Booking bookingDetails = bookingDomain.get();
		long revNo = bookingRevision.getRevisionNumber();
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		when(bookingSaveHelper.getLatestRevision(bookingDetails)).thenReturn(bookingRevision);
		when(createBookingHelper.populateBooking(bookingRequest, ++revNo, false)).thenReturn(newBooking);
		when(bookingRepository.save(newBooking)).thenReturn(null);
		
		shouldRetriveBookingDetailsWhenBookingDomainPresentInDB();
		BookingDto actualBookingDto = bookingServiceImpl.updateBookingDetails(1910l, bookingRequest);
		
		assertEquals("1910", actualBookingDto.getBookingId());
		assertEquals("Test Data", actualBookingDto.getBookingDescription());
		verify(bookingRepository,times(1)).save(newBooking);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldNotUpdateBookingDetailsWhenBookingDomainNotPresentInDB() {
		
		BookingRequest bookingRequest = createBookingRequest();
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		bookingServiceImpl.updateBookingDetails(1910l, bookingRequest);
	}
	
	@Test
	public void shouldSubmitBookingDetailsWhenBookingDomainPresentInDB() {
		Booking newBooking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(newBooking);
		BookingRequest bookingRequest = createBookingRequest();
		BookingRevision bookingRevision =  createBookingRevision();
		Booking bookingDetails = bookingDomain.get();
		long revNo = bookingRevision.getRevisionNumber();
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		when(bookingSaveHelper.getLatestRevision(bookingDetails)).thenReturn(bookingRevision);
		when(createBookingHelper.populateBooking(bookingRequest, ++revNo, true)).thenReturn(newBooking);
		when(bookingRepository.save(newBooking)).thenReturn(null);
		
		shouldRetriveBookingDetailsWhenBookingDomainPresentInDB();
		BookingDto actualBookingDto = bookingServiceImpl.submitBookingDetails(1910l, bookingRequest);

		assertEquals("2025", actualBookingDto.getBookingRevisionId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldNotSubmitBookingDetailsWhenBookingDomainNotPresentInDB() {
		
		BookingRequest bookingRequest = createBookingRequest();
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		bookingServiceImpl.submitBookingDetails(1910l, bookingRequest);
	}
	@Test
	public void shouldRetriveBookingDetailsWhenBookingDomainPresentInDB() {
	
		Booking booking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(booking);
		BookingRevision bookingRevision =  createBookingRevision();
		BookingDto bookingDto = createBookingDto();
		Booking bookingDetails = bookingDomain.get();
		
		bookingDto.setTeam(createTeamDtoFromMapper());
		bookingDto.setBookingId(String.valueOf(booking.getBookingId()));
		bookingDto.setDiscipline(createDisciplineDtoFromMapper());
		bookingDto.setBookingDescription(booking.getBookingDescription());
		bookingDto.setInsideIr35(bookingRevision.getRole().getInsideIr35());
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		when(bookingSaveHelper.getLatestRevision(bookingDetails)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(bookingDto);
		
		BookingDto actual = bookingServiceImpl.retrieveBookingDetails(1910l);
		assertEquals("2025", actual.getBookingRevisionId());
		assertEquals(8000l, actual.getCommisioningOffice().getOfficeId());
		
		verify(bookingRepository,times(1)).findById(1910l);
		verify(bookingSaveHelper,times(1)).getLatestRevision(bookingDetails);
		verify(bookingMapper,times(1)).convertToDto(bookingRevision);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldNotRetriveBookingDetailsWhenBookingDomainNotPresentInDB() {
		
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		bookingServiceImpl.retrieveBookingDetails(1910l);
		
		verify(bookingRepository,times(1)).findById(1910l);
	}
	
	
	@Test
	public void shouldUpdateContractWhenBookingRevisionPresentInDB() throws URISyntaxException {
		
		String agreementId = "C-546";
		URI uri = new URI("https://imaginationcbs.blob.core.windows.net/templates/page.html");
		byte b[] = {20,10,30,5};
		InputStream pdfInputStream = new ByteArrayInputStream(b);
		
		Optional<BookingRevision> bookingRevision = Optional.of(createBookingRevision());
		BookingRevision bookingRev = bookingRevision.get();
		bookingRev.setCompletedAgreementPdf(uri.toString());
	
		StringJoiner agreementName = new StringJoiner("-");
		agreementName.add(String.valueOf(bookingRev.getBooking().getBookingId()));
		agreementName.add(bookingRev.getJobNumber());
		agreementName.add(bookingRev.getJobname());
		
		when(bookingRevisionRepository.findByAgreementId(agreementId)).thenReturn(bookingRevision);
		when(adobeSignService.downloadAgreement(agreementId)).thenReturn(pdfInputStream);
		when(azureStorageUtility.uploadFile(pdfInputStream,agreementName + ".pdf")).thenReturn(uri);
		when(bookingRevisionRepository.save(bookingRev)).thenReturn(null);
		
	
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldNotUpdateContractWhenBookingRevisionNotPresentInDB() throws URISyntaxException {
		
		when(bookingRevisionRepository.findByAgreementId("C-546")).thenReturn(Optional.empty());
		bookingServiceImpl.updateContract("C-546", "22-04-2020");
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingIdIsNotPresentInDBForcancelBooking() {
	
		CBSUser cbsUser = createCBSUser();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(Optional.empty());
		
		bookingServiceImpl.cancelBooking(1910L);
	
	}
	
	@Test
	public void shouldReturnEmptyBookingDtoWhenBookingIdIsPresentInDBForcancelBooking() {
	
		CBSUser cbsUser = createCBSUser();
		Booking booking = createBooking();
		Optional<Booking> bookingList = Optional.of(booking);
		bookingList.get().getApprovalStatus().setApprovalStatusId(1001L);
		bookingList.get().setChangedBy("Pappu");
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(bookingList);
		
		BookingDto actual = bookingServiceImpl.cancelBooking(1910L);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository).findById(1910L);
		verify(bookingRepository).delete(bookingList.get());
		
		assertEquals(null, actual.getBookingId());
		
	
	}
	
	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
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
		booking.setBookingId(1910L);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setTeam(createTeam());
		booking.setBookingDescription("Test Data");
		
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
		Booking booking = new Booking();
		booking.setApprovalStatus(createApprovalStatusDm());
		booking.setBookingDescription("Test Data");
		booking.setBookingId(1910l);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setTeam(createTeam());

		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setBooking(booking);
		bookingRevision.setAgreementId("C-546");
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
		bookingDto.setBookingDescription("Test Data");
		bookingDto.setBookingRevisionId("2025");
		bookingDto.setTeam(createTeamDto());
		bookingDto.setCommisioningOffice(createCommisioningOffice());
		bookingDto.setInsideIr35("true");
		bookingDto.setJobDeptName("Admin");
		bookingDto.setJobname("RRMC Geneva AS 20 Press Conf - Production");
		bookingDto.setJobNumber("100204205-02");
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
