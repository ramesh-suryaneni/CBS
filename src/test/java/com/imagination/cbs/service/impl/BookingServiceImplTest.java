/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
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

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {
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

	@InjectMocks
	private BookingServiceImpl bookingServiceImpl;
	
	@Test
	public void shouldAddBookingDetails() {
		when(createBookingHelper.populateBooking(createBookingRequest(),1L, false)).thenReturn(createBooking());
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
		bookingRequest.setSiteOptions(createSiteOptionsList());
		bookingRequest.setSupplierTypeId("7658");
		bookingRequest.setWorkDays(createWorkDaysDtoList());
		bookingRequest.setWorkTasks(createWorkTaskDtoList());
		bookingRequest.setSiteOptions(createSiteOptionsList());
		
		return bookingRequest;
	}
	private List<String> createSiteOptionsList(){
		List<String> siteOptionsList = new ArrayList<>();
		siteOptionsList.add("Clients premises");
		siteOptionsList.add("Home Office");
		return siteOptionsList;
	}
	private List<WorkDaysDto> createWorkDaysDtoList(){
		List<WorkDaysDto> workDaysDtoList = new ArrayList<WorkDaysDto>();
		WorkDaysDto workDaysDto  = new WorkDaysDto();
		workDaysDto.setBookingRevisionId("2207");
		workDaysDto.setMonthName("Jan");
		workDaysDto.setMonthWorkingDays("20");
		workDaysDto.setChangedBy("ramesh.suryaneni");
		
		workDaysDtoList.add(workDaysDto);
		return  workDaysDtoList;
	}
	
	private List<WorkTasksDto> createWorkTaskDtoList(){
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
	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setApprovalStatus(createApprovalStatusDm());
		booking.setBookingDescription("null");
		booking.setBookingId(1910l);
		booking.setBookingRevisions(createBookingRevisionList());
		booking.setChangedBy("nafisa.ujloomwale");
		booking.setChangedDate(new Timestamp(2020,4,20,4,25,39,69));
		booking.setTeam(createTeam());
		return booking;
	}
	private ApprovalStatusDm createApprovalStatusDm() {
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalDescription("null");
		approvalStatusDm.setApprovalName("Draft");
		approvalStatusDm.setApprovalStatusId(1001l);
		approvalStatusDm.setChangedBy("null");
		approvalStatusDm.setChangedDate(new Timestamp(2020,4,20,4,25,39,69));
		return approvalStatusDm;
	}
	private List<BookingRevision> createBookingRevisionList(){
		
		List<BookingRevision> bookingRevisionList = new ArrayList<>();
		bookingRevisionList.add(createBookingRevision());
		return bookingRevisionList;
	}
	private BookingRevision createBookingRevision() {
		
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setAgreementDocumentId("null");
		bookingRevision.setAgreementId("null");
		bookingRevision.setApprovalStatus(createApprovalStatusDm());
		bookingRevision.setApproverComments("null");
		bookingRevision.setBooking(createBooking());
		bookingRevision.setBookingRevisionId(4065l);
		bookingRevision.setBookingWorkTasks(createBookingWorkTaskList());
		bookingRevision.setChangedBy("nafisa.ujloomwale");
		bookingRevision.setChangedDate(new Timestamp(2020,4,20,4,24,48,253));
		bookingRevision.setCommisioningOffice(createOfficeDm());
		bookingRevision.setCommOffRegion(createRegion());
		bookingRevision.setContractAmountAftertax(new BigDecimal(1760));
		bookingRevision.setContractAmountBeforetax(new BigDecimal(2000));
		bookingRevision.setContractedFromDate(new Timestamp(2020,4,20,00,00,00,0));
		bookingRevision.setContractedToDate(new Timestamp(2020,4,30,00,00,00,0));
		bookingRevision.setContractEmployee(createContractorEmployee());
		bookingRevision.setContractor(createContractor());
		bookingRevision.setContractorSignedDate(null);
		bookingRevision.setContractorTotalAvailableDays(11l);
		bookingRevision.setContractorTotalWorkingDays(10l);
		bookingRevision.setContractorWorkRegion(createRegion());
		bookingRevision.setContractorWorkSites(createContractorWorkSiteList());
		bookingRevision.setContractWorkLocation(createOfficeDm());
		bookingRevision.setEmployerTaxPercent(new BigDecimal(12));
		bookingRevision.setInsideIr35("true");
		bookingRevision.setJobDeptName("");
		bookingRevision.setJobname("RRMC Geneva AS 20 Press Conf - Production");
		bookingRevision.setJobNumber("100204205-02");
		bookingRevision.setRevisionNumber(5l);
		bookingRevision.setMonthlyWorkDays(createContractorMonthlyWorkDaysList());
		bookingRevision.setRate(new BigDecimal(200));
		bookingRevision.setReasonForRecruiting(createRecruiting());
		bookingRevision.setRole(createRoleDm());
		bookingRevision.setSupplierType(createSupplierTypeDm());
		bookingRevision.setTeam(createTeam());
		bookingRevision.setCurrency(createCurrencyDm());
		
		return bookingRevision;
	}
	public OfficeDm createOfficeDm() {
		OfficeDm officeDm = new OfficeDm();
		officeDm.setRegion(createRegion());
		officeDm.setOfficeId(new Long(8000));
		officeDm.setOfficeName("Melbourne");
		officeDm.setOfficeDescription("Melbourne");
		return officeDm;
	}
	private Region createRegion() {
		
		Region region = new Region();
		region.setRegionDescription("Europe & Middle East");
		region.setRegionId(1l);
		region.setRegionName("EMEA");
		return region;
	}
	private List<ContractorWorkSite> createContractorWorkSiteList(){
		List<ContractorWorkSite> contractorWorkSiteList = new ArrayList<>();
		ContractorWorkSite contractorWorkSite1 = new ContractorWorkSite();
		contractorWorkSite1.setId(296l);
		contractorWorkSite1.setSiteOptions(createSiteOptions());
		contractorWorkSiteList.add(contractorWorkSite1);
		return contractorWorkSiteList;
	}
	@SuppressWarnings("deprecation")
	private List<BookingWorkTask> createBookingWorkTaskList(){
		
		BookingWorkTask bookingWorkTask = new BookingWorkTask();
		//bookingWorkTask.setBookingRevision(createBookingRevision());
		bookingWorkTask.setBookingWorkId(526l);
		bookingWorkTask.setChangedBy("ramesh.suryaneni");
		bookingWorkTask.setChangedDate(new Timestamp(2020,4,20,5,9,12,603));
		bookingWorkTask.setTaskDateRate(200d);
		bookingWorkTask.setTaskDeliveryDate(new Date(2020,5,30));
		bookingWorkTask.setTaskId(200l);
		bookingWorkTask.setTaskName("new task");
		bookingWorkTask.setTaskTotalAmount(2000d);
		bookingWorkTask.setTaskTotalDays(10l);
		
		List<BookingWorkTask> bookingWorkTaskList = new ArrayList<>();
		bookingWorkTaskList.add(bookingWorkTask);
		return bookingWorkTaskList;
	}
	private SiteOptions createSiteOptions() {
		
		SiteOptions siteOptions = new SiteOptions();
		siteOptions.setId(1l);
		siteOptions.setName("Clients premises");
		return siteOptions;
	}
	private CurrencyDm createCurrencyDm(){
		
		CurrencyDm currencyDm = new CurrencyDm();
		currencyDm.setCurrencyName("Euros");
		currencyDm.setCurrencyId(103l);
		currencyDm.setCurrencyCode("EUR");
		return currencyDm;
	}
	private Team createTeam(){
		Team team = new Team();
		team.setChangedBy("david.harman");
		team.setChangedDate(new Timestamp(2020,3,9,16,5,12,367));
		team.setTeamId(1003l);
		team.setTeamName("ADM");
		return team;
	}
	private SupplierTypeDm createSupplierTypeDm(){
		SupplierTypeDm supplierType = new SupplierTypeDm();
		supplierType.setId(1l);
		supplierType.setName("Yash");
		supplierType.setDescription("test data ");
		return supplierType;
	}
	public RoleDm createRoleDm(){
		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		roleDm.setRoleId(3214l);
		roleDm.setRoleDescription("2D");
		roleDm.setInsideIr35("false");
		roleDm.setDiscipline(createDiscipline());
		roleDm.setChangedDate(new Timestamp(2020,3,30,16,16,55,5));
		roleDm.setChangedBy("Akshay");
		return roleDm;
	}
	private Discipline createDiscipline(){
		Discipline discipline = new Discipline();
		discipline.setDisciplineId(8000l);
		discipline.setDisciplineName("Creative");
		discipline.setDisciplineDescription("This is Creative");
		return discipline;
	}
	public ReasonsForRecruiting  createRecruiting() {
		ReasonsForRecruiting recruiting = new ReasonsForRecruiting();
		recruiting.setReasonId(1l);
		recruiting.setReasonName("Specific skills required");
		recruiting.setReasonDescription("Internal resource not available");
		return recruiting;
	}
	public List<ContractorMonthlyWorkDay> createContractorMonthlyWorkDaysList() {
		List<ContractorMonthlyWorkDay> createContractorMonthlyWorkDaysList = new ArrayList<>();
		ContractorMonthlyWorkDay contractorMonthlyWorkDay = new ContractorMonthlyWorkDay();
		//contractorMonthlyWorkDay.setBookingRevision(createBookingRevision());
		contractorMonthlyWorkDay.setChangedBy("nafisa.ujloomwale");
		contractorMonthlyWorkDay.setChangedDatetime(new Timestamp(2020,4,20,4,25,32,357));
		contractorMonthlyWorkDay.setMonthName("April");
		contractorMonthlyWorkDay.setMonthWorkingDays(10l);
		contractorMonthlyWorkDay.setWorkDaysId(678l);
		
		createContractorMonthlyWorkDaysList.add(contractorMonthlyWorkDay);
		return createContractorMonthlyWorkDaysList;
	}
	private ContractorEmployee createContractorEmployee() {
		ContractorEmployee contractorEmployee  = new ContractorEmployee();
		contractorEmployee.setChangedBy("admin");
		contractorEmployee.setChangedDate(new Timestamp(2020,3,9,15,59,9,0));
		contractorEmployee.setContactDetails("1111-111-11");
		contractorEmployee.setContractorEmployeeId(6000l);
		contractorEmployee.setContractorEmployeeName("Alex");
	
		contractorEmployee.setKnownAs("Aliase1");
		contractorEmployee.setStatus("Status1");
		
		return contractorEmployee;
	}

	private Contractor createContractor()
	{
		Contractor contractor = new Contractor();
		contractor.setContractorId(6000);
		contractor.setContractorName("Yash");
		return contractor;
	}
}
