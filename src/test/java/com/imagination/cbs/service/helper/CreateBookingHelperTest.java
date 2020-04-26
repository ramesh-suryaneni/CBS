/*package com.imagination.cbs.service.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.BookingRequest;
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
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;

@RunWith(MockitoJUnitRunner.class)
public class CreateBookingHelperTest {
	
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

	@InjectMocks
	private CreateBookingHelper createBookingHelper;
	
	@Test
	public void shouldSetRoleDmInBookingRevision_CheckRole() {
		
		RoleDm roleDm = createRoleDm();
		BookingRequest bookingRequest =  createBookingRequest();
		 BookingRevision bookingRevision =  createBookingRevision(); 
		when(roleRepository.findById(3214L)).thenReturn(Optional.of(roleDm));
		createBookingHelper.checkRole(bookingRequest,bookingRevision);
	}
	private BookingRequest createBookingRequest(){
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
	public RoleDm createRoleDm(){
		RoleDm roleDm = new RoleDm();
		roleDm.setRoleName("2D");
		roleDm.setRoleId(3214l);
		roleDm.setInsideIr35("true");
		//roleDm.setDiscipline(createDiscipline());
		return roleDm;
	}

}
*/