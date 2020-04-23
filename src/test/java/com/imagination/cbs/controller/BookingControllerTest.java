package com.imagination.cbs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.config.TestConfig;
import com.imagination.cbs.dto.ApprovalStatusDmDto;
import com.imagination.cbs.dto.ApproveRequest;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorWorkSiteDto;
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.dto.DashBoardBookingDto;
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
import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationUtility;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.DashBoardService;
import com.imagination.cbs.util.BookingValidator;

@Import(BookingValidator.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(BookingController.class)
@ContextConfiguration(classes = { TestConfig.class })
public class BookingControllerTest {

	@MockBean
	private GoogleIDTokenValidationUtility googleIDTokenValidationUtility;

	@MockBean
	private GoogleAuthenticationEntryPoint googleAuthenticationEntryPoint;

	@MockBean
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookingService bookingService;

	@MockBean
	private DashBoardService dashBoardService;

	@Autowired
	ObjectMapper objectMapper;

	@WithMockUser("developer")
	@Test
	public void shouldAddBookingDetails() throws Exception {

		when(bookingService.addBookingDetails(createBookingRequest())).thenReturn(createBookingDto());

		MvcResult mvcResult = this.mockMvc
				.perform(post("/bookings").content(createBookingJsonRequest()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		assertEquals(HttpStatus.SC_CREATED, mvcResult.getResponse().getStatus());
	}

	@WithMockUser("developer")
	@Test
	public void shouldReturnDashBoardBookingStatusDetails() throws Exception {

		when(dashBoardService.getDashBoardBookingsStatusDetails("draft", 0, 100)).thenReturn(createPageDashBoardDto());

		this.mockMvc
				.perform(get("/bookings").param("status", "draft").param("pageNo", "0").param("pageSize", "100")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].contractorName").value("Yash"))
				.andExpect(jsonPath("$.content[1].contractorName").value("Yard Nine LTD"));
		verify(dashBoardService).getDashBoardBookingsStatusDetails("draft", 0, 100);

	}

	@WithMockUser("/developer")
	@Test
	public void shouldUpdateBookingDetailsBasedOnBookingId() throws Exception {
		
		when(bookingService.updateBookingDetails(2020L, createBookingRequest())).thenReturn(createBookingDto());
		this.mockMvc
				.perform(patch("/bookings/{bookingId}", 2020L).content(createBookingJsonRequest())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.bookingRevisionId").value("2025"))
				.andExpect(jsonPath("$.discipline.disciplineId").value("8000"));
		verify(bookingService).updateBookingDetails(2020L, createBookingRequest());
	}

	@WithMockUser("/developer")
	@Test public void shouldProcessBookingDetailsBasedOnBookingId() throws Exception {
		  
		when(bookingService.submitBookingDetails(2020L, createBookingRequest())).thenReturn(createBookingDto());
		
		this.mockMvc
				.perform(put("/bookings/{bookingId}", 2020L).content(createBookingJsonRequest())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.jobname").value("JLR Experience Center"))
				.andExpect(jsonPath("$.approvalStatus.approvalName").value("Draft"));

		verify(bookingService).submitBookingDetails(2020L, createBookingRequest());
	 
	}


	@WithMockUser("/developer")
	@Test
	public void shouldReturnBookingDetailsBasedOnBookingId() throws Exception {

		when(bookingService.retrieveBookingDetails(1035L)).thenReturn(createBookingDto());
		
		this.mockMvc.perform(get("/bookings/1035").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.bookingRevisionId").value("2025"))
				.andExpect(jsonPath("$.contractor.contractorName").value("Yash"));
		verify(bookingService).retrieveBookingDetails(1035L);
	}

	@WithMockUser("/developer")
	@Test
	public void shouldCreateApprovedBooking() throws Exception {
		
		String jsonRequest = objectMapper.writeValueAsString(createApproveRequest());

		when(bookingService.approveBooking(createApproveRequest())).thenReturn(createBookingDto());

		MvcResult mvcResult = this.mockMvc
				.perform(post("/bookings/process-request").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(HttpStatus.SC_OK, mvcResult.getResponse().getStatus());
		
		verify(bookingService).approveBooking(createApproveRequest());
	}

	@WithMockUser("/developer")
	@Test
	public void shouldCancleBooking() throws Exception {

		long bookingId = 1035l;
		when(bookingService.cancelBooking(bookingId)).thenReturn(createBookingDto());

		this.mockMvc.perform(
				delete("/bookings/1035").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(bookingService).cancelBooking(bookingId);

	}
	
	@WithMockUser("/developer")
	@Test
	public void ShouldReturnAllBookingRevisionsInBookingDtoWhenBiikingIDIsPresentInDB()throws Exception {
		
		BookingDto bookingDto = createBookingDto();
		List<BookingDto> bookingDtoList = new ArrayList<>();
		bookingDtoList.add(bookingDto);
		
		when(bookingService.retrieveBookingRevisions(1035L)).thenReturn(bookingDtoList);
		
		mockMvc.perform(get("/bookings/1035/revisions").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookingRevisionId").value("2025"))
				.andExpect(jsonPath("$[0].contractor.contractorName").value("Yash"));
		
		verify(bookingService).retrieveBookingRevisions(1035L);
	}
	
	@WithMockUser("/developer")
	@Test
	public void ShouldSendBookingReminderThroughEmailToBookingApprover()throws Exception {
		
		mockMvc.perform(get("/bookings/1035/reminder").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		verify(bookingService).sendBookingReminder(1035L);
	}

	

	private String createBookingJsonRequest() throws JsonProcessingException {
		return objectMapper.writeValueAsString(createBookingRequest());
	}

	private BookingDto createBookingDto() {
		BookingDto bookingDto = new BookingDto();
		bookingDto.setAgreementDocumentId("");
		bookingDto.setAgreementId("");
		bookingDto.setApproverComments("");
		bookingDto.setApprovalStatus(createApprovalStatusDmDto());
		bookingDto.setBookingId("1035");
		bookingDto.setBookingDescription("");
		bookingDto.setBookingRevisionId("2025");
		bookingDto.setChangedBy("Pravin");
		bookingDto.setChangedDate("");
		bookingDto.setCommOffRegion(createRegionDto());
		bookingDto.setCommisioningOffice(createCommisioningOffice());
		bookingDto.setContractAmountAftertax("");
		bookingDto.setContractAmountBeforetax("");
		bookingDto.setContractedFromDate("2020-03-11 20:48:05.123");
		bookingDto.setContractedToDate("2020-04-02 20:48:05.123");
		bookingDto.setContractEmployee(createContractorEmployeeDto());
		bookingDto.setContractor(createContractorDto());
		bookingDto.setContractorSignedDate("2020-03-11 20:48:05.123");
		bookingDto.setContractorTotalAvailableDays("");
		bookingDto.setContractorTotalWorkingDays("");
		bookingDto.setContractorWorkRegion(createRegionDto());
		bookingDto.setContractorWorkSites(createContractorWorkSiteDtoList());
		bookingDto.setContractWorkLocation(createOfficeDto());
		bookingDto.setCurrency(createCurrencyDto());
		bookingDto.setDiscipline(createDisciplineDto());
		bookingDto.setEmployerTaxPercent("");
		bookingDto.setInsideIr35("true");
		bookingDto.setJobDeptName("");
		bookingDto.setJobname("JLR Experience Center");
		bookingDto.setJobNumber("0987");
		bookingDto.setMonthlyWorkDays(createWorkDaysDtoList());
		bookingDto.setRate("");
		bookingDto.setReasonForRecruiting(createRecruitingDto());
		bookingDto.setRole(createRoleDto());
		bookingDto.setSupplierType(createSupplierTypeDto());
		bookingDto.setTeam(createTeamDto());

		return bookingDto;
	}

	public OfficeDto createOfficeDto() {
		OfficeDto officeDto = new OfficeDto();
		officeDto.setOfficeId(new Long(8000));
		officeDto.setOfficeName("Melbourne");
		officeDto.setOfficeDescription("Melbourne");

		return officeDto;
	}

	public RecruitingDto createRecruitingDto() {
		RecruitingDto recruitingDto = new RecruitingDto();
		recruitingDto.setReasonId(1);
		recruitingDto.setReasonName("Specific skills required");
		recruitingDto.setReasonDescription("Internal resource not available");
		return recruitingDto;
	}

	public RoleDto createRoleDto() {
		RoleDto roleDto = new RoleDto();

		roleDto.setRoleName("2D");
		roleDto.setRoleId("3214");
		roleDto.setRoleDescription("2D");
		roleDto.setInsideIr35("false");
		roleDto.setDisciplineId("0");
		roleDto.setChangedDate("2020-03-30T16:16:55.000+05:30");
		roleDto.setChangedBy("Akshay");
		roleDto.setCestDownloadLink("https://imaginationcbs.blob.core.windows.net/cbs/IR35 Example PDF outside.pdf");

		return roleDto;
	}

	private RegionDto createRegionDto() {

		RegionDto regionDto = new RegionDto();
		regionDto.setRegionDescription("Europe & Middle East");
		regionDto.setRegionId(1l);
		regionDto.setRegionName("EMEA");
		return regionDto;
	}

	private List<ContractorWorkSiteDto> createContractorWorkSiteDtoList() {
		List<ContractorWorkSiteDto> contractorWorkSiteDtoList = new ArrayList<>();
		ContractorWorkSiteDto contractorWorkSiteDto1 = new ContractorWorkSiteDto();
		contractorWorkSiteDto1.setId("296");
		contractorWorkSiteDto1.setSiteOptions(createSiteOptionsDto());
		contractorWorkSiteDtoList.add(contractorWorkSiteDto1);
		return contractorWorkSiteDtoList;
	}

	private SiteOptionsDto createSiteOptionsDto() {
		SiteOptionsDto siteOptionsDto = new SiteOptionsDto();
		siteOptionsDto.setId("1");
		siteOptionsDto.setName("Clients premises");
		return siteOptionsDto;
	}

	private ApprovalStatusDmDto createApprovalStatusDmDto() {
		ApprovalStatusDmDto approvalStatusDmDto = new ApprovalStatusDmDto();
		approvalStatusDmDto.setApprovalName("Draft");
		approvalStatusDmDto.setApprovalStatusId("1001");
		approvalStatusDmDto.setChangedDate("1899-12-31 00:00:00.0");
		return approvalStatusDmDto;
	}

	private ContractorEmployeeDto createContractorEmployeeDto() {
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setChangedBy("admin");
		contractorEmployeeDto.setChangedDate("2020-03-09 15:59:09.0");
		contractorEmployeeDto.setContactDetails("1111-111-11");
		contractorEmployeeDto.setContractorEmployeeId("6000");
		contractorEmployeeDto.setContractorEmployeeName("Alex");
		contractorEmployeeDto.setEmployeeId("5000");
		contractorEmployeeDto.setKnownAs("Aliase1");
		contractorEmployeeDto.setStatus("Status1");

		return contractorEmployeeDto;
	}

	private ContractorDto createContractorDto() {
		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(6000);
		contractorDto.setContractorName("Yash");
		return contractorDto;
	}

	private CurrencyDto createCurrencyDto() {
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyName("Euros");
		currencyDto.setCurrencyId("103");
		currencyDto.setCurrencyCode("EUR");
		return currencyDto;
	}

	private DisciplineDto createDisciplineDto() {
		DisciplineDto disciplineDto = new DisciplineDto();
		disciplineDto.setDisciplineId(8000);
		disciplineDto.setDisciplineName("Creative");
		disciplineDto.setDisciplineDescription("This is Creative");
		return disciplineDto;
	}

	private OfficeDto createCommisioningOffice() {
		OfficeDto officeDto = new OfficeDto();
		officeDto.setOfficeId(new Long(8000));
		officeDto.setOfficeName("Melbourne");
		officeDto.setOfficeDescription("Melbourne");
		return officeDto;
	}

	private SupplierTypeDto createSupplierTypeDto() {
		SupplierTypeDto supplierType = new SupplierTypeDto();
		supplierType.setId(1);
		supplierType.setName("Yash");
		supplierType.setDescription("test data ");
		return supplierType;
	}

	private TeamDto createTeamDto() {
		TeamDto teamDto = new TeamDto();
		teamDto.setChangedBy("david.harman");
		teamDto.setChangedDate("2020-03-09 16:05:12.367");
		teamDto.setTeamId("1003");
		teamDto.setTeamName("ADM");
		return teamDto;
	}

	private List<WorkDaysDto> createWorkDaysDtoList() {
		List<WorkDaysDto> workDaysDtoList = new ArrayList<WorkDaysDto>();
		WorkDaysDto workDaysDto = new WorkDaysDto();
		workDaysDto.setBookingRevisionId("2207");
		workDaysDto.setMonthName("Jan");
		workDaysDto.setMonthWorkingDays("20");
		workDaysDto.setChangedBy("ramesh.suryaneni");

		workDaysDtoList.add(workDaysDto);
		return workDaysDtoList;
	}

	private List<WorkTasksDto> createWorkTaskDtoList() {
		List<WorkTasksDto> workTaskDtoList = new ArrayList<>();
		WorkTasksDto workTasksDto = new WorkTasksDto();
		workTasksDto.setBookingRevisionId("2207");
		workTasksDto.setTaskId("3214");
		workTasksDto.setTaskTotalDays("30");
		workTasksDto.setChangedBy("MITUL");
		workTasksDto.setTaskName("task10");

		workTaskDtoList.add(workTasksDto);
		return workTaskDtoList;
	}

	private BookingRequest createBookingRequest() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setBookingDescription("Test Booking 10");
		bookingRequest.setCommisioningOffice("Yash");
		bookingRequest.setCommOffRegion("US");
		bookingRequest.setContractAmountAftertax("23");
		bookingRequest.setContractAmountBeforetax("20");
		bookingRequest.setContractedFromDate("2020-04-1 20:48:05.127");
		bookingRequest.setContractedToDate("2020-10-1 20:48:05.127");
		bookingRequest.setContractEmployeeId("5004");
		bookingRequest.setContractorId("6002");
		bookingRequest.setContractorSignedDate("2020-04-1 20:48:05.127");
		bookingRequest.setContractorTotalAvailableDays("30");
		bookingRequest.setContractorTotalWorkingDays("30");
		bookingRequest.setContractorWorkRegion("US");
		bookingRequest.setContractWorkLocation("YashTech");
		bookingRequest.setCurrencyId("103");
		bookingRequest.setEmployerTaxPercent("62");
		bookingRequest.setInsideIr35("true");
		bookingRequest.setJobDeptName("2D");
		bookingRequest.setJobNumber("1111l");
		bookingRequest.setRate("154");
		bookingRequest.setReasonForRecruiting("123");
		bookingRequest.setRoleId("4326");
		bookingRequest.setSiteOptions(createSiteOptions());
		bookingRequest.setSupplierTypeId("7658");
		bookingRequest.setWorkDays(createWorkDaysDtoList());
		bookingRequest.setWorkTasks(createWorkTaskDtoList());
		bookingRequest.setCommisioningOffice("10");
		bookingRequest.setContractAmountAftertax("10.0");
		bookingRequest.setContractAmountBeforetax("0.5");
		
		return bookingRequest;
	}

	private List<String> createSiteOptions() {
		List<String> siteOptionsList = new ArrayList<>();
		siteOptionsList.add("Clients premises");
		siteOptionsList.add("Home Office");
		return siteOptionsList;
	}

	private Page<DashBoardBookingDto> createPageDashBoardDto() {
		List<DashBoardBookingDto> bookingDashBoardDtoList = new ArrayList<>();

		DashBoardBookingDto dashBoardDto1 = new DashBoardBookingDto();
		DashBoardBookingDto dashBoardDto2 = new DashBoardBookingDto();
		dashBoardDto1.setBookingId(new BigInteger("2020"));
		dashBoardDto1.setChangedBy("ramesh.suryaneni");
		dashBoardDto1.setContractorName("Yash");
		dashBoardDto1.setJobName("JLR Experience Center");
		dashBoardDto1.setRoleName("2D");
		dashBoardDto1.setStatus("Draft");

		dashBoardDto2.setBookingId(new BigInteger("2033"));
		dashBoardDto2.setChangedBy("Pravin");
		dashBoardDto2.setContractorName("Yard Nine LTD");
		dashBoardDto2.setJobName("JLR Experience Center");
		dashBoardDto2.setRoleName("2D");
		dashBoardDto2.setStatus("Draft");
		bookingDashBoardDtoList.add(dashBoardDto1);
		bookingDashBoardDtoList.add(dashBoardDto2);

		return new PageImpl<>(bookingDashBoardDtoList, PageRequest.of(0, 100), 2);
	}

	public ApproveRequest createApproveRequest() {
		ApproveRequest approveRequest = new ApproveRequest();
		approveRequest.setAction("Created");
		approveRequest.setBookingId("1035");
		approveRequest.setStatus("Draft");
		return approveRequest;
	}
}
