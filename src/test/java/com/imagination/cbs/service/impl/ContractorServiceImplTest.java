package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import java.math.BigDecimal;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorEmployeeDefaultRate;
import com.imagination.cbs.domain.ContractorEmployeeRole;
import com.imagination.cbs.domain.ContractorEmployeeSearch;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
import com.imagination.cbs.dto.ContractorEmployeeRequest;
import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.dto.ContractorRequest;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.mapper.ContractorEmployeeMapper;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.repository.ContractorEmployeeRepository;
import com.imagination.cbs.repository.ContractorEmployeeSearchRepository;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;

@RunWith(MockitoJUnitRunner.class)
public class ContractorServiceImplTest {

	@InjectMocks
	private ContractorServiceImpl contractorServiceImpl;
	
	@Mock
	private ContractorRepository contractorRepository;

	@Mock
	private ContractorEmployeeSearchRepository contractorEmployeeSearchRepository;

	@Mock
	private ContractorEmployeeRepository contractorEmployeeRepository;

	@Mock
	private BookingRevisionRepository bookingRevisionRepository;

	@Mock
	private ContractorMapper contractorMapper;

	@Mock
	private ContractorEmployeeMapper contractorEmployeeMapper;

	@Mock
	private LoggedInUserService loggedInUserService;

	@Mock
	private RoleRepository roleRepository;
	

	@Test
	public void shouldReturnPaginatedContractorEmployeeDetails() {

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Direction.ASC, "roleId"));
		
		when(contractorEmployeeSearchRepository.findAll(pageable)).thenReturn(createContractorEmployeePagedData());
		
		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl.getContractorEmployeeDetails(0,
				5, "roleId", "ASC");

		verify(contractorEmployeeSearchRepository).findAll(pageable);

		assertEquals("2D", contractorEmployeeDto.getContent().get(0).getRole());
	}

	@Test
	public void shouldReturnPaginatedContractorEmployeeDetailsByNameOrRoleName() {

		when(contractorEmployeeSearchRepository.findByContractorEmployeeNameOrRoleNameContains("Al", "Al", 
				PageRequest.of(0, 5, Sort.by(Direction.DESC, "dayRate")))).thenReturn(createContractorEmployeeSearchPagedDataForName());

		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl
				.getContractorEmployeeDetailsByNameOrRoleName("Al", 0, 5, "dayRate", "DESC");

		verify(contractorEmployeeSearchRepository).findByContractorEmployeeNameOrRoleNameContains("Al",
				"Al", PageRequest.of(0, 5, Sort.by(Direction.DESC, "dayRate")));
		
		assertEquals("Alex", contractorEmployeeDto.getContent().get(0).getContractorEmployeeName());
	}

	@Test
	public void shouldReturnPaginatedContractorDetails() {

		when(contractorRepository.findAll(PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName"))))
				.thenReturn(createContractorPagedData());

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatils(0, 5, "contractorName", "ASC");
		
		verify(contractorRepository).findAll(PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName")));

		assertEquals("Imagination", contractorDto.getContent().get(0).getContractorName());
	}

	@Test
	public void shouldReturnPaginatedContractorDetailsForContainingName() {

		when(contractorRepository.findByContractorNameContains("Im",
				PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName"))))
						.thenReturn(createContractorPagedData());

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatilsContainingName("Im", 0, 5,
				"contractorName", "ASC");
		
		verify(contractorRepository).findByContractorNameContains("Im",	PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName")));
		
		assertEquals("Imagination", contractorDto.getContent().get(0).getContractorName());
	}

	@Test
	public void shouldReturnContractorDetailsByContractorIdWhenContractorIdIsPresentInDB() {
		
		Contractor contractor = Mockito.mock(Contractor.class);
		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(1001);
		contractorDto.setContractorName("Imagination");
		contractorDto.setCompanyType("LTD");
		contractorDto.setContactDetails("12341234");
		contractorDto.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractorDto.setChangedBy("Test");

		when(contractorRepository.findById(1001L)).thenReturn(Optional.of(contractor));
		when(contractorMapper.toContractorDtoFromContractorDomain(contractor)).thenReturn(contractorDto);

		ContractorDto response = contractorServiceImpl.getContractorByContractorId(1001L);

		verify(contractorRepository).findById(1001L);
		verify(contractorMapper).toContractorDtoFromContractorDomain(contractor);

		assertEquals("Imagination", response.getContractorName());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenContractorIdNotPresentInDB() {

		when(contractorRepository.findById(1001L)).thenReturn(Optional.empty());
		
		contractorServiceImpl.getContractorByContractorId(1001L);
	}

	@Test
	public void shouldReturnContractorEmployeeByContractorIdAndContractorEmployeeIdIfBothPresentInDB() {
		
		ContractorEmployee contractorEmployee = mock(ContractorEmployee.class);
		ContractorEmployeeDefaultRate employeeDefaultRate = mock(ContractorEmployeeDefaultRate.class);
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setContractorEmployeeId("2001");
		contractorEmployeeDto.setEmployeeId("3001");
		contractorEmployeeDto.setContractorEmployeeName("John");
		contractorEmployeeDto.setKnownAs("Alias");

		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L)).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);
		when(contractorEmployee.getContractorEmployeeDefaultRate()).thenReturn(employeeDefaultRate);
		when(employeeDefaultRate.getRate()).thenReturn(new BigDecimal(1000));
		
		ContractorEmployeeDto response = contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L,
				3001L);
		
		verify(contractorEmployeeRepository).findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeMapper).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
		
		assertEquals("John", response.getContractorEmployeeName());
	}

	@Test
	public void shouldReturnEmptyContractorEmployeeByContractorIdAndEmployeeIdWhenBothNotPresentInDB() {

		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L)).thenReturn(null);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(null))
				.thenReturn(new ContractorEmployeeDto());

		ContractorEmployeeDto contractorEmployeeDto = contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		
		verify(contractorEmployeeRepository).findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeMapper).toContractorEmployeeDtoFromContractorEmployee(null);
		
		assertEquals(null, contractorEmployeeDto.getContractorEmployeeName());
	}

	@Test
	public void shouldReturnContractorEmployeeByContractorIdAndEmployeeIdWhenRateNotPresentInDB() {
		ContractorEmployee contractorEmployee = Mockito.mock(ContractorEmployee.class);

		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L)).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(Mockito.mock(ContractorEmployeeDto.class));
		when(contractorEmployee.getContractorEmployeeDefaultRate()).thenReturn(null);

		ContractorEmployeeDto contractorEmployeeDto = contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);

		verify(contractorEmployeeRepository).findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeMapper).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
		
		assertEquals(null, contractorEmployeeDto.getRate());
	}

	@Test
	public void shouldAddContractorDetails() {

		CBSUser cbsUser = Mockito.mock(CBSUser.class);
		Contractor contractor = Mockito.mock(Contractor.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorMapper.toContractorDomainFromContractorRequest(createContractorRequest())).thenReturn(contractor);
		when(contractorRepository.save(contractor)).thenReturn(contractor);
		when(contractorMapper.toContractorDtoFromContractorDomain(contractor)).thenReturn(createContractorDto());

		ContractorDto response = contractorServiceImpl.addContractorDetails(createContractorRequest());

		verify(loggedInUserService).getLoggedInUserDetails();
		verify(contractorMapper).toContractorDomainFromContractorRequest(createContractorRequest());
		verify(contractorRepository).save(contractor);
		verify(contractorMapper).toContractorDtoFromContractorDomain(contractor);

		assertEquals("Imagination", response.getContractorName());
		assertEquals("LTD", response.getCompanyType());
	}

	@Test
	public void shouldAddContractorEmployee() {
		
		ContractorEmployee contractorEmployee = createContractorEmployeeDomain();
		ContractorEmployeeDto contractorEmployeeDto = createContractorEmployeeDto();

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(new CBSUser("pappu"));
		when(contractorRepository.findById(2001L)).thenReturn(Optional.of(new Contractor()));
		when(roleRepository.findById(201L)).thenReturn(Optional.of(new RoleDm()));
		when(contractorEmployeeRepository.save(any(ContractorEmployee.class))).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);

		ContractorEmployeeDto response = contractorServiceImpl.addContractorEmployee(2001L, createContractorEmployeeRequest());

		verify(loggedInUserService).getLoggedInUserDetails();
		verify(contractorRepository).findById(2001L);
		verify(roleRepository).findById(201L);
		verify(contractorEmployeeRepository).save(any(ContractorEmployee.class));
		verify(contractorEmployeeMapper).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
		
		assertEquals("Alex", response.getContractorEmployeeName());
		assertEquals("1001", response.getContractorEmployeeId());
	}

	@Test
	public void shouldAddContractorEmployeeIfRoleIdIsNull() {
		
		ContractorEmployeeRequest contractorEmployeeRequest = createContractorEmployeeRequest();
		contractorEmployeeRequest.setRoleId("");
		
		ContractorEmployee contractorEmployee = createContractorEmployeeDomain();
		ContractorEmployeeDto contractorEmployeeDto = createContractorEmployeeDto();

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(new CBSUser("pappu"));
		when(contractorRepository.findById(2001L)).thenReturn(Optional.of(new Contractor()));
		when(contractorEmployeeRepository.save(any(ContractorEmployee.class))).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);

		ContractorEmployeeDto response = contractorServiceImpl.addContractorEmployee(2001L, contractorEmployeeRequest);

		verify(loggedInUserService).getLoggedInUserDetails();
		verify(contractorRepository).findById(2001L);
		verify(contractorEmployeeRepository).save(any(ContractorEmployee.class));
		verify(contractorEmployeeMapper).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);

		assertEquals("Alex", response.getContractorEmployeeName());
		assertEquals("1001", response.getContractorEmployeeId());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenContractorNotPresentInDB_addContractorEmployee() {
		
		ContractorEmployeeRequest contractorEmployeeRequest = createContractorEmployeeRequest();

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(new CBSUser("pappu"));
		when(contractorRepository.findById(2001L)).thenReturn(Optional.empty());

		contractorServiceImpl.addContractorEmployee(2001L, contractorEmployeeRequest);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowResourceNotFoundExceptionWhenRoleIdNotPresentInDB_addContractorEmployee() {
		
		ContractorEmployeeRequest contractorEmployeeRequest = createContractorEmployeeRequest();

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(new CBSUser("pappu"));
		when(contractorRepository.findById(2001L)).thenReturn(Optional.of(new Contractor()));
		when(roleRepository.findById(201L)).thenReturn(Optional.empty());

		contractorServiceImpl.addContractorEmployee(2001L, contractorEmployeeRequest);
	}

	private Page<ContractorEmployeeSearch> createContractorEmployeePagedData() {
		List<ContractorEmployeeSearch> contractorEmployeeSearchedList = new ArrayList<>();

		ContractorEmployeeSearch ce1 = new ContractorEmployeeSearch();
		ce1.setContractorEmployeeName("Alex");
		ce1.setDayRate(new BigDecimal(100));
		ce1.setRole("2D");
		ce1.setCompany("Imagination");
		ce1.setNoOfBookingsInPast(3);
		contractorEmployeeSearchedList.add(ce1);

		ContractorEmployeeSearch ce2 = new ContractorEmployeeSearch();
		ce2.setContractorEmployeeName("Albert");
		ce2.setDayRate(new BigDecimal(200));
		ce2.setRole("2D Senior");
		ce2.setCompany("Imagination");
		ce2.setNoOfBookingsInPast(2);
		contractorEmployeeSearchedList.add(ce2);

		return new PageImpl<>(contractorEmployeeSearchedList, PageRequest.of(0, 5), 2);
	}

	private Page<ContractorEmployeeSearch> createContractorEmployeeSearchPagedDataForName() {
		List<ContractorEmployeeSearch> contractorEmployeeSearchedList = new ArrayList<>();

		ContractorEmployeeSearch ce1 = new ContractorEmployeeSearch();
		ce1.setContractorEmployeeName("Alex");
		ce1.setDayRate(new BigDecimal(100));
		ce1.setRole("2D");
		ce1.setCompany("Imagination");
		ce1.setNoOfBookingsInPast(3);
		contractorEmployeeSearchedList.add(ce1);

		return new PageImpl<>(contractorEmployeeSearchedList, PageRequest.of(0, 5), 1);
	}

	private Page<Contractor> createContractorPagedData() {
		List<Contractor> contractorList = new ArrayList<>();

		Contractor contractor1 = new Contractor();
		contractor1.setContractorId(1001);
		contractor1.setContractorName("Imagination");
		contractor1.setCompanyType("LTD");
		contractor1.setContactDetails("12341234");
		contractor1.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractor1.setChangedBy("Test");
		contractorList.add(contractor1);

		Contractor contractor2 = new Contractor();
		contractor2.setContractorId(1001);
		contractor2.setContractorName("Imagination");
		contractor2.setCompanyType("LTD");
		contractor2.setContactDetails("12341234");
		contractor2.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractor2.setChangedBy("Test");
		contractorList.add(contractor2);

		return new PageImpl<Contractor>(contractorList, PageRequest.of(0, 5), contractorList.size());
	}

	private ContractorEmployeeRequest createContractorEmployeeRequest() {
		ContractorEmployeeRequest contractorEmployeeRequest = new ContractorEmployeeRequest();
		contractorEmployeeRequest.setContractorEmployeeName("Alex");
		contractorEmployeeRequest.setKnownAs("Alias 1");
		contractorEmployeeRequest.setRoleId("201");
		contractorEmployeeRequest.setDayRate("500");
		contractorEmployeeRequest.setCurrencyId("101");

		return contractorEmployeeRequest;
	}
	
	private ContractorRequest createContractorRequest() {
		ContractorRequest contractorRequest = new ContractorRequest();
		contractorRequest.setContractorName("Imagination");
		contractorRequest.setServiceProvided("LTD");
		contractorRequest.setContactDetails("1234512345");
		
		return contractorRequest;
	}
	
	private ContractorDto createContractorDto() {
		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(1001);
		contractorDto.setContractorName("Imagination");
		contractorDto.setCompanyType("LTD");
		contractorDto.setContactDetails("12341234");
		contractorDto.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractorDto.setChangedBy("Test");

		return contractorDto;
	}
	
	private ContractorEmployeeDto createContractorEmployeeDto() {
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setContractorEmployeeId("1001");
		contractorEmployeeDto.setEmployeeId("101");
		contractorEmployeeDto.setContractorEmployeeName("Alex");
		contractorEmployeeDto.setKnownAs("Alias 1");
		contractorEmployeeDto.setChangedBy("Test");

		return contractorEmployeeDto;
	}
	
	private ContractorEmployee createContractorEmployeeDomain() {

		ContractorEmployee contractorEmpDomain = new ContractorEmployee();

		contractorEmpDomain.setContractorEmployeeName("Alex");
		contractorEmpDomain.setKnownAs("Alias 1");
		contractorEmpDomain.setChangedBy("loggedInUser");
		
		contractorEmpDomain.setContractor(new Contractor());

		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		ContractorEmployeeRole contractorEmployeeRole = new ContractorEmployeeRole();
		contractorEmployeeRole.setRoleDm(new RoleDm());
		contractorEmployeeRole.setDateFrom(currentTimeStamp);
		contractorEmployeeRole.setChangedBy("loggedInUser");
		contractorEmployeeRole.setContractorEmployee(contractorEmpDomain);
		contractorEmpDomain.setContractorEmployeeRole(contractorEmployeeRole);

		ContractorEmployeeDefaultRate contractorEmployeeDefaultRate = new ContractorEmployeeDefaultRate();
		contractorEmployeeDefaultRate.setCurrencyId(101L);
		contractorEmployeeDefaultRate.setRate(new BigDecimal(500));
		contractorEmployeeDefaultRate.setDateFrom(currentTimeStamp);
		contractorEmployeeDefaultRate.setChangedBy("loggedInUser");
		contractorEmployeeDefaultRate.setContractorEmployee(contractorEmpDomain);
		contractorEmpDomain.setContractorEmployeeDefaultRate(contractorEmployeeDefaultRate);

		return contractorEmpDomain;
	}
}