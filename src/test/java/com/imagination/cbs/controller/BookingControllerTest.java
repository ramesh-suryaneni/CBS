package com.imagination.cbs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.dto.ApprovalStatusDmDto;
import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.OfficeDto;
import com.imagination.cbs.dto.SupplierTypeDto;
import com.imagination.cbs.dto.TeamDto;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.DashBoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookingControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@MockBean
	private BookingService bookingService;
	
	@MockBean
	private DashBoardService dashBoardService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Before
	public void setUp()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
										.apply(SecurityMockMvcConfigurers.springSecurity())
										.build();
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldAddBookingDetails() throws Exception{
		
		when(bookingService.addBookingDetails(Mockito.any(BookingRequest.class))).thenReturn(createBookingDto());
		
		MvcResult mvcResult = this.mockMvc.perform(post("/bookings")
										.content(createJsonRequest())
										.contentType(MediaType.APPLICATION_JSON))
										.andExpect(status().isCreated())
										.andReturn();
		assertEquals(HttpStatus.SC_CREATED, mvcResult.getResponse().getStatus());
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldReturnDashBoardBookingStatusDetails() throws Exception
	{
		
		when(dashBoardService.getDashBoardBookingsStatusDetails(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt()))
								.thenReturn(createPageDashBoardDto());
		
		this.mockMvc.perform(get("/bookings").param("status", "draft").param("pageNo", "0").param("pageSize", "100")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.content[0].contractorName").value("Yash"))
					.andExpect(jsonPath("$.content[1].contractorName").value("Yard Nine LTD"));
		verify(dashBoardService).getDashBoardBookingsStatusDetails("draft", 0, 100);
					
	}
	
	@WithMockUser("developer")
	@Test
	public void shouldUpdateBookingDetailsBasedOnBookingId() throws Exception
	{
		long bookingId = 2020l;
		when(bookingService.updateBookingDetails(Mockito.anyLong(), Mockito.any(BookingRequest.class))).thenReturn(createBookingDto());
		this.mockMvc.perform(patch("/bookings/{bookingId}",bookingId)
				.content(createJsonRequest())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bookingRevisionId").value("2025"))
				.andExpect(jsonPath("$.discipline.disciplineId").value("8000"));
		verify(bookingService).updateBookingDetails(bookingId, createBookingRequest());
	}
	
	/*@WithMockUser("developer")
	@Test
	public void shouldProcessBookingDetailsBasedOnBookingId() throws Exception {
		long bookingId = 2020l;
		when(bookingService.submitBookingDetails(Mockito.anyLong(),Mockito.any(BookingRequest.class))).thenReturn(createBookingDto());
		this.mockMvc.perform(put("/bookings/{bookingId}",bookingId)
				.content(createJsonRequest())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jobname").value("JLR Experience Center"))
				.andExpect(jsonPath("$.approvalStatus.approvalName").value("Draft"));
		verify(bookingService).submitBookingDetails(bookingId, createBookingRequest());
	}*/
	
	private String createJsonRequest() throws JsonProcessingException
	{
		return objectMapper.writeValueAsString(createBookingRequest());
	}
	private BookingDto createBookingDto()
	{
		BookingDto bookingDto = new BookingDto();
		bookingDto.setApprovalStatus(createApprovalStatusDmDto());
		bookingDto.setBookingId("1035");
		bookingDto.setBookingRevisionId("2025");
		bookingDto.setChangedBy("Pravin");
		bookingDto.setContractedFromDate("2020-03-11 20:48:05.123");
		bookingDto.setContractedToDate("2020-04-02 20:48:05.123");
		bookingDto.setContractEmployee(createContractorEmployeeSerachDto());
		bookingDto.setContractor(createContractorDto());
		bookingDto.setContractorSignedDate("2020-03-11 20:48:05.123");
		bookingDto.setCurrency(createCurrencyDto());
		bookingDto.setDiscipline(createDisciplineDto());
		bookingDto.setJobname("JLR Experience Center");
		bookingDto.setJobNumber("0987");
		bookingDto.setCommisioningOffice(createCommisioningOffice());
		bookingDto.setSupplierType(createSupplierTypeDto());
		bookingDto.setTeam(createTeamDto());
		bookingDto.setMonthlyWorkDays(createWorkDaysDtoList());
		bookingDto.setBookingWorkTasks(createWorkTaskDtoList());
		
		return bookingDto;
	}
	
	private ApprovalStatusDmDto createApprovalStatusDmDto()
	{
		ApprovalStatusDmDto approvalStatusDmDto  = new ApprovalStatusDmDto();
		approvalStatusDmDto.setApprovalName("Draft");
		approvalStatusDmDto.setApprovalStatusId("1001");
		approvalStatusDmDto.setChangedDate("1899-12-31 00:00:00.0");
		return approvalStatusDmDto;
	}
	
	private ContractorEmployeeSearchDto createContractorEmployeeSerachDto() {
		ContractorEmployeeSearchDto contractorEmployeeSearchDto  = new ContractorEmployeeSearchDto();
		contractorEmployeeSearchDto.setCompany("ImaginationUK");
		contractorEmployeeSearchDto.setContractorEmployeeName("Alex");
		contractorEmployeeSearchDto.setContractorId(6000l);
		contractorEmployeeSearchDto.setRole("2D");
		contractorEmployeeSearchDto.setRoleId(3214l);
		return contractorEmployeeSearchDto;
	}

	private ContractorDto createContractorDto()
	{
		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(6000);
		contractorDto.setContractorName("Yash");
		return contractorDto;
	}

	private CurrencyDto createCurrencyDto()
	{
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencyName("Euros");
		currencyDto.setCurrencyId("103");
		currencyDto.setCurrencyCode("EUR");
		return currencyDto;
	}
	private DisciplineDto createDisciplineDto()
	{
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
	
	private SupplierTypeDto createSupplierTypeDto()
	{
		SupplierTypeDto supplierType = new SupplierTypeDto();
		supplierType.setId(1);
		supplierType.setName("Yash");
		supplierType.setDescription("test data ");
		return supplierType;
	}
	private TeamDto createTeamDto()
	{
		TeamDto teamDto = new TeamDto();
		teamDto.setChangedBy("david.harman");
		teamDto.setChangedDate("2020-03-09 16:05:12.367");
		teamDto.setTeamId("1003");
		teamDto.setTeamName("ADM");
		return teamDto;
	}
	private List<WorkDaysDto> createWorkDaysDtoList()
	{
		List<WorkDaysDto> workDaysDtoList = new ArrayList<WorkDaysDto>();
		WorkDaysDto workDaysDto  = new WorkDaysDto();
		workDaysDto.setBookingRevisionId("2207");
		workDaysDto.setMonthName("Jan");
		workDaysDto.setMonthWorkingDays("20");
		workDaysDto.setChangedBy("ramesh.suryaneni");
		
		workDaysDtoList.add(workDaysDto);
		return  workDaysDtoList;
	}
	
	private List<WorkTasksDto> createWorkTaskDtoList()
	{
		List<WorkTasksDto> workTaskDtoList = new ArrayList<>();
		WorkTasksDto workTasksDto  = new WorkTasksDto();
		workTasksDto.setBookingRevisionId("2207");
		workTasksDto.setTaskId("3214");
		workTasksDto.setTaskTotalDays("30");
		workTasksDto.setChangedBy("MITUL");
		workTasksDto.setTaskName("task10");
		
		workTaskDtoList.add(workTasksDto);
		return workTaskDtoList;
	}
	
	private BookingRequest createBookingRequest()
	{
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
		bookingRequest.setReasonForRecruiting("Specific Skills Required");
		bookingRequest.setRoleId("4326");
		bookingRequest.setSupplierTypeId("7658");
		bookingRequest.setWorkDays(createWorkDaysDtoList());
		bookingRequest.setWorkTasks(createWorkTaskDtoList());
		
		return bookingRequest;
	}
		
	private Page<BookingDashBoardDto> createPageDashBoardDto()
	{
		List<BookingDashBoardDto> bookingDashBoardDtoList = new ArrayList<>();
		
		BookingDashBoardDto dashBoardDto1 = new BookingDashBoardDto();
		BookingDashBoardDto dashBoardDto2 = new BookingDashBoardDto();
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
		
		return new PageImpl<>(bookingDashBoardDtoList,PageRequest.of(0, 100),2);
	}
}
