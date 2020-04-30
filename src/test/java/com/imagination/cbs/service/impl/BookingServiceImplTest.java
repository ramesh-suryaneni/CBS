/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
import com.imagination.cbs.dto.ApproveRequest;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.TeamDto;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.AdobeSignService;
import com.imagination.cbs.service.EmailService;
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

@RunWith(MockitoJUnitRunner.class)
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

	@Mock
	private EmailService emailService;
	
	@Test
	public void shouldReturnBookingDto_AddBookingDetails() {
		
		BookingRequest bookingRequest = createBookingRequest();
		Booking booking = createBooking();
		Optional<Booking> Optionalbooking = Optional.of(booking);
		BookingRevision bookingRevision = createBookingRevision();
		
		when(createBookingHelper.populateBooking(bookingRequest,1L, false)).thenReturn(booking);
		when(bookingRepository.save(booking)).thenReturn(booking);
		when(bookingRepository.findById(1910l)).thenReturn(Optionalbooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.addBookingDetails(bookingRequest);
		
		verify(createBookingHelper).populateBooking(bookingRequest,1l, false);
		verify(bookingRepository).save(booking);
		verify(bookingRepository).findById(1910l);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		
		assertEquals("2025", actual.getBookingRevisionId());
	}
	
	@Test
	public void shouldReturnBookingDtoWhenBookingDomainPresentInDB_UpdateBookingDetails() { 

		Booking booking = createBooking();
		Optional<Booking> Optionalbooking = Optional.of(booking);
		BookingRequest bookingRequest = createBookingRequest();
		BookingRevision bookingRevision = createBookingRevision();
		
		when(bookingRepository.findById(1910l)).thenReturn(Optionalbooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(createBookingHelper.populateBooking(createBookingRequest(), 5L, false)).thenReturn(booking);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actualBookingDto = bookingServiceImpl.updateBookingDetails(1910L, bookingRequest);
		
		verify(bookingRepository,times(2)).findById(1910l);
		verify(bookingSaveHelper,times(2)).getLatestRevision(booking);
		verify(createBookingHelper).populateBooking(bookingRequest, 5L, false);
		verify(bookingMapper).convertToDto(bookingRevision);
		verify(bookingRepository).save(booking);
		
		assertEquals("1910", actualBookingDto.getBookingId());
		assertEquals("Test Data", actualBookingDto.getBookingDescription());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingDomainNotPresentInDB_UpdateBookingDetails() {
		
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		
		bookingServiceImpl.updateBookingDetails(1910l, createBookingRequest());
		
	}
	
	@Test
	public void shouldSendEmailAndReturnBookingDtoWhenBookingDomainPresentInDB_SubmitBookingDetails() {
		Booking booking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(booking);
		BookingRequest bookingRequest = createBookingRequest();
		BookingRevision bookingRevision =  createBookingRevision();
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(createBookingHelper.populateBooking(bookingRequest, 5L, true)).thenReturn(booking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actualBookingDto = bookingServiceImpl.submitBookingDetails(1910l, bookingRequest);
		
		verify(bookingRepository,times(2)).findById(1910l);
		verify(bookingSaveHelper,times(3)).getLatestRevision(booking);
		verify(createBookingHelper).populateBooking(bookingRequest, 5L, true);
		verify(bookingRepository).save(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		verify(emailHelper).prepareMailAndSend(booking, bookingRevision, 1L);
		
		assertEquals("2025", actualBookingDto.getBookingRevisionId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingDomainNotPresentInDB_SubmitBookingDetails() {
		
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		
		bookingServiceImpl.submitBookingDetails(1910l, createBookingRequest());
		
	}
	
	@Test
	public void shouldReturnBookingDtoWhenBookingDomainPresentInDB_RetriveBookingDetails() {
	
		Booking booking = createBooking();
		Optional<Booking> bookingDomain = Optional.of(booking);
		BookingRevision bookingRevision =  createBookingRevision();
		
		when(bookingRepository.findById(1910l)).thenReturn(bookingDomain);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.retrieveBookingDetails(1910l);
		
		verify(bookingRepository).findById(1910l);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		
		assertEquals("2025", actual.getBookingRevisionId());
		assertEquals(new Long(8000L), actual.getCommisioningOffice().getOfficeId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingDomainNotPresentInDB_RetriveBookingDetails() {
		
		when(bookingRepository.findById(1910l)).thenReturn(Optional.empty());
		
		bookingServiceImpl.retrieveBookingDetails(1910l);
		
	}
	
	@Test
	public void shouldSendEmailToDomainWhenBookingRevisionPresentInDB_UpdateContract() throws URISyntaxException { 
		
		URI uri = new URI("https://imaginationcbs.blob.core.windows.net/templates/page.html");
		byte b[] = {20,10,30,5};
		InputStream pdfInputStream = new ByteArrayInputStream(b);
		
		BookingRevision bookingRevision = createBookingRevision();
		bookingRevision.setAgreementId("C-546");
		bookingRevision.setCompletedAgreementPdf(uri.toString());
	
		StringJoiner agreementName = new StringJoiner("-");
		agreementName.add("1910");
		agreementName.add(bookingRevision.getJobNumber());
		agreementName.add(bookingRevision.getJobname());
		
		when(bookingRevisionRepository.findTopByAgreementIdOrderByChangedDateAsc("C-546")).thenReturn(Optional.of(bookingRevision));
		when(adobeSignService.downloadAgreement("C-546")).thenReturn(pdfInputStream);
		when(azureStorageUtility.uploadFile(pdfInputStream,agreementName + ".pdf")).thenReturn(uri);
		
		bookingServiceImpl.updateContract("C-546", "22-04-2020");
		
		verify(bookingRevisionRepository).findTopByAgreementIdOrderByChangedDateAsc("C-546");
		verify(adobeSignService).downloadAgreement("C-546");
		verify(azureStorageUtility).uploadFile(pdfInputStream,agreementName + ".pdf");
		verify(bookingRevisionRepository).save(bookingRevision);
		verify(emailService).sendContractReceipt(bookingRevision);
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingRevisionNotPresentInDB_UpdateContract() throws URISyntaxException {
		
		when(bookingRevisionRepository.findTopByAgreementIdOrderByChangedDateAsc("C-546")).thenReturn(Optional.empty());
		
		bookingServiceImpl.updateContract("C-546", "22-04-2020");
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingIdIsNotPresentInDB_CancelBooking() {
	
		CBSUser cbsUser = createCBSUser();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(Optional.empty());
		
		bookingServiceImpl.cancelBooking(1910L);
	}
	
	@Test
	public void shouldReturnEmptyBookingDtoWhenBookingIdIsPresentInDBAndApprovalStatusISDraft_CancelBooking() {
	
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
	
	@Test
	public void shouldReturnBookingDtoWhenBookingIdIsPresentInDBAndApprovalStatusISCancelled_CancelBooking() {
	
		CBSUser cbsUser = createCBSUser();
		Booking booking = createBooking();
		BookingRevision bookingRevision = createBookingRevision();
		Optional<Booking> bookingList = Optional.of(booking);
		booking.setChangedBy("Pappu");
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(bookingList);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingSaveHelper.saveBooking(booking, bookingRevision, 1010L, cbsUser)).thenReturn(booking);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.cancelBooking(1910l);
		
		verify(loggedInUserService,times(2)).getLoggedInUserDetails();
		verify(bookingRepository,times(2)).findById(1910l);
		verify(bookingSaveHelper,times(2)).getLatestRevision(booking);
		verify(bookingSaveHelper).saveBooking(booking, bookingRevision, 1010L, cbsUser);
		verify(bookingMapper).convertToDto(bookingRevision);
		
		assertEquals("1910", actual.getBookingId());
	}
	
	@Test
	public void shouldReturnBookingDtoWhenBookingIdIsPresentInDBAndActionIsApprove_ApproveBooking() {
		
		ApproveRequest request = createApproveRequest();
		request.setAction("APPROVE");
		BookingRevision bookingRevision = createBookingRevision();
		CBSUser cbsUser = createCBSUser();
		Booking booking = createBooking();
		Optional<Booking> optionalBooking = Optional.of(booking);
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(optionalBooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.approveBooking(request);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository,times(2)).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		verify(bookingApproveHelper).approve(booking, cbsUser);
		
		assertEquals("1910", actual.getBookingId());
	}
	
	@Test
	public void shouldReturnBookingDtoWhenBookingIdIsPresentInDBAndActionIsHRApprove_ApproveBooking() {
		
		ApproveRequest request = createApproveRequest();
		request.setAction("HRAPPROVE");
		BookingRevision bookingRevision = createBookingRevision();
		CBSUser cbsUser = createCBSUser();
		Booking booking = createBooking();
		Optional<Booking> bookingList = Optional.of(booking);
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(bookingList);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.approveBooking(request);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository,times(2)).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		verify(bookingHrApproveHelper).hrApprove(booking);
		
		assertEquals("1910", actual.getBookingId());
		
	}
	
	@Test
	public void shouldReturnBookingDtoWhenBookingIdIsPresentInDBAndActionIsDecline_ApproveBooking() {
		
		ApproveRequest request = createApproveRequest();
		request.setAction("DECLINE");
		BookingRevision bookingRevision = createBookingRevision();
		CBSUser cbsUser = createCBSUser();
		Booking booking = createBooking();
		Optional<Booking> bookingList = Optional.of(booking);
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(bookingList);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		when(bookingMapper.convertToDto(bookingRevision)).thenReturn(createBookingDto());
		
		BookingDto actual = bookingServiceImpl.approveBooking(request);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository,times(2)).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(bookingMapper).convertToDto(bookingRevision);
		verify(bookingDeclineHelper).decline(booking);
		
		assertEquals("1910", actual.getBookingId());
	}
	
	@Test(expected = CBSApplicationException.class)
	public void shouldThrowCBSApplicationExceptionWhenActionIsOtherThanApproveHrApproveAndDecline_ApproveBooking() {
		
		ApproveRequest request = createApproveRequest();
		request.setAction("TEST");
		Booking booking = createBooking();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(createCBSUser());
		when(bookingRepository.findById(1910L)).thenReturn(Optional.of(booking));
		
		bookingServiceImpl.approveBooking(request);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository).findById(1910L);
		verify(bookingDeclineHelper).decline(booking);
		
	}

	@Test(expected = CBSApplicationException.class)
	public void shouldThrowCBSApplicationExceptionWhenBookingIdIsNotPresentInDB_ApproveBooking() {
		
		ApproveRequest request = createApproveRequest();
		request.setAction("APPROVE");
		CBSUser cbsUser = createCBSUser();
		
		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(bookingRepository.findById(1910L)).thenReturn(Optional.empty());
		
		bookingServiceImpl.approveBooking(request);
		
		verify(loggedInUserService).getLoggedInUserDetails();
		verify(bookingRepository).findById(1910L);
	}
	
	@Test
	public void shouldReturnBookingDtoListWhenBookingIsPresentInDB_RetrieveBookingRevisions() {
		List<BookingDto> bookingDtoList = new ArrayList<>();
		bookingDtoList.add(createBookingDto());
		Optional<Booking> booking = Optional.of(createBooking());
		
		when(bookingRepository.findById(1910L)).thenReturn(booking);
		when(bookingMapper.convertToDtoList(booking.get().getBookingRevisions())).thenReturn(bookingDtoList);
		
		List<BookingDto> actual = bookingServiceImpl.retrieveBookingRevisions(1910L);
		
		verify(bookingRepository).findById(1910L);
		verify(bookingMapper).convertToDtoList(booking.get().getBookingRevisions());
		
		assertEquals("Test Data", actual.get(0).getBookingDescription()); 
		assertEquals("100204205-02", actual.get(0).getJobNumber());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingIsNotPresentInDB_RetrieveBookingRevisions() {
		
		when(bookingRepository.findById(1910L)).thenReturn(Optional.empty());
		
		bookingServiceImpl.retrieveBookingRevisions(1910L);
		
	}
	
	@Test
	public void shouldPrepareMailAndSendToHRWhenBookingIsPresentInDB_SendBookingReminder() {
		BookingRevision bookingRevision = createBookingRevision();
		Optional<Booking> optionalBooking = Optional.of(createBooking());
		Booking booking = optionalBooking.get();
		booking.getApprovalStatus().setApprovalStatusId(1005L);
		
		when(bookingRepository.findById(1910L)).thenReturn(optionalBooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		
		bookingServiceImpl.sendBookingReminder(1910L);
		
		verify(bookingRepository).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(emailHelper).prepareMailAndSendToHR(bookingRevision);
	}
	
	@Test
	public void shouldPrepareMailAndSendToAPPROVAL_1WhenBookingIsPresentInDB_SendBookingReminder() {
		BookingRevision bookingRevision = createBookingRevision();
		Optional<Booking> optionalBooking = Optional.of(createBooking());
		Booking booking = optionalBooking.get();
		booking.getApprovalStatus().setApprovalStatusId(1002L);
		
		when(bookingRepository.findById(1910L)).thenReturn(optionalBooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		
		bookingServiceImpl.sendBookingReminder(1910L);
		
		verify(bookingRepository).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(emailHelper).prepareMailAndSend(booking,bookingRevision,1L);
	}
	
	@Test
	public void shouldPrepareMailAndSendToAPPROVAL_2WhenBookingIsPresentInDB_SendBookingReminder() {
		BookingRevision bookingRevision = createBookingRevision();
		Optional<Booking> optionalBooking = Optional.of(createBooking());
		Booking booking = optionalBooking.get();
		booking.getApprovalStatus().setApprovalStatusId(1003L);
		
		when(bookingRepository.findById(1910L)).thenReturn(optionalBooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
	
		bookingServiceImpl.sendBookingReminder(1910L);
		
		verify(bookingRepository).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(emailHelper).prepareMailAndSend(booking,bookingRevision,2L);
	}
	@Test
	public void shouldPrepareMailAndSendToAPPROVAL_3WhenBookingIsPresentInDB_SendBookingReminder() {
		BookingRevision bookingRevision = createBookingRevision();
		Optional<Booking> optionalBooking = Optional.of(createBooking());
		Booking booking = optionalBooking.get();
		booking.getApprovalStatus().setApprovalStatusId(1004L);
		
		when(bookingRepository.findById(1910L)).thenReturn(optionalBooking);
		when(bookingSaveHelper.getLatestRevision(booking)).thenReturn(bookingRevision);
		
		bookingServiceImpl.sendBookingReminder(1910L);
		
		verify(bookingRepository).findById(1910L);
		verify(bookingSaveHelper).getLatestRevision(booking);
		verify(emailHelper).prepareMailAndSend(booking,bookingRevision,3L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenBookingIsNotPresentInDB_SendBookingReminder() {
		
		when(bookingRepository.findById(1910L)).thenReturn(Optional.empty()); 
		
		bookingServiceImpl.sendBookingReminder(1910L);	
	}

	private CBSUser createCBSUser() {
		CBSUser cbsUser = new CBSUser("Pappu");
		cbsUser.setEmpId(1002L);
		return cbsUser;
	}
	private ApproveRequest createApproveRequest() {
		ApproveRequest request = new ApproveRequest();
		request.setAction("Approve");
		request.setBookingId("1910");
		return request;
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
	
	private TeamDto createTeamDto(){
		TeamDto teamDto = new TeamDto();
		teamDto.setTeamId("1003");
		teamDto.setTeamName("ADM");
		return teamDto;
	}
	private Booking createBooking() {
		BookingRevision bookingRevision1 = new BookingRevision();
		bookingRevision1.setBookingRevisionId(4065l);
		bookingRevision1.setJobNumber("100204205-02");
		bookingRevision1.setRevisionNumber(5l);
		List<BookingRevision> bookingRevisionList = new ArrayList<>();
		bookingRevisionList.add(bookingRevision1);
		Booking booking = new Booking();
		booking.setApprovalStatus(createApprovalStatusDm());
		booking.setBookingDescription("Test Data");
		booking.setBookingId(1910L);
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setTeam(createTeam());
		booking.setBookingDescription("Test Data");
		booking.setBookingRevisions(bookingRevisionList);
		return booking;
	}
	private ApprovalStatusDm createApprovalStatusDm() {
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalName("Canclelled");
		approvalStatusDm.setApprovalStatusId(1010L);
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
		bookingRevision.setRevisionNumber(4l);
		bookingRevision.setInsideIr35("true");
		return bookingRevision;
	}
	private RoleDm createRoleDm(){
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
