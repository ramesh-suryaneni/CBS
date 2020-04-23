package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

	@InjectMocks
	private ContractorServiceImpl contractorServiceImpl;

	@Test
	public void geContractorEmployeeDetailsIfExistsInDB() {

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Direction.ASC, "roleId"));
		when(contractorEmployeeSearchRepository.findAll(pageable)).thenReturn(createContractorEmployeePagedData());
		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl.geContractorEmployeeDetails(0,
				5, "roleId", "ASC");

		verify(contractorEmployeeSearchRepository, times(1)).findAll(pageable);
		String role = contractorEmployeeDto.getContent().get(0).getRole();
		assertEquals("2D", role);
	}

	@Test
	public void geContractorEmployeeDetailsByRoleIdIfRoleIdExistsInDB() {

		when(contractorEmployeeSearchRepository.findByRoleId(Mockito.anyLong(), Mockito.any()))
				.thenReturn(createContractorEmployeePagedData());
		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl
				.geContractorEmployeeDetailsByRoleId(1000L, 0, 5, "roleId", "ASC");

		verify(contractorEmployeeSearchRepository, times(1)).findByRoleId(1000L,
				PageRequest.of(0, 5, Sort.by(Direction.ASC, "roleId")));
		String role = contractorEmployeeDto.getContent().get(0).getRole();
		assertEquals("2D", role);
	}

	@Test
	public void geContractorEmployeeDetailsByRoleIdIfRoleIdNotExistsInDB() {

		Page<ContractorEmployeeSearch> contractorEmployeeSearch = new PageImpl<>(
				new ArrayList<ContractorEmployeeSearch>(), PageRequest.of(0, 5), 0);
		when(contractorEmployeeSearchRepository.findByRoleId(Mockito.anyLong(), Mockito.any()))
				.thenReturn(contractorEmployeeSearch);

		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl
				.geContractorEmployeeDetailsByRoleId(1001L, 0, 5, "roleId", "DESC");

		verify(contractorEmployeeSearchRepository, times(1)).findByRoleId(1001L,
				PageRequest.of(0, 5, Sort.by(Direction.DESC, "roleId")));
		assertTrue(contractorEmployeeDto.getContent().isEmpty());
	}

	@Test
	public void geContractorEmployeeDetailsByRoleIdAndNameIfNameExistsInDB() {

		when(contractorEmployeeSearchRepository.findByRoleIdAndContractorEmployeeNameContains(Mockito.anyLong(),
				Mockito.anyString(), Mockito.any())).thenReturn(createContractorEmployeeSearchPagedDataForName());

		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl
				.geContractorEmployeeDetailsByRoleIdAndName(1000L, "Alex", 0, 5, "dayRate", "ASC");

		verify(contractorEmployeeSearchRepository, times(1)).findByRoleIdAndContractorEmployeeNameContains(1000L,
				"Alex", PageRequest.of(0, 5, Sort.by(Direction.ASC, "dayRate")));
		String contractorEmployeeName = contractorEmployeeDto.getContent().get(0).getContractorEmployeeName();
		assertEquals("Alex", contractorEmployeeName);
	}

	@Test
	public void geContractorEmployeeDetailsByRoleIdAndNameIfNameNotExistsInDB() {

		Page<ContractorEmployeeSearch> contractorEmployeeSearch = new PageImpl<>(
				new ArrayList<ContractorEmployeeSearch>(), PageRequest.of(0, 5), 0);
		when(contractorEmployeeSearchRepository.findByRoleIdAndContractorEmployeeNameContains(Mockito.anyLong(),
				Mockito.anyString(), Mockito.any())).thenReturn(contractorEmployeeSearch);

		Page<ContractorEmployeeSearchDto> contractorEmployeeDto = contractorServiceImpl
				.geContractorEmployeeDetailsByRoleIdAndName(1000L, "Test", 0, 5, "dayRate", "ASC");
		verify(contractorEmployeeSearchRepository, times(1)).findByRoleIdAndContractorEmployeeNameContains(1000L,
				"Test", PageRequest.of(0, 5, Sort.by(Direction.ASC, "dayRate")));
		assertTrue(contractorEmployeeDto.getContent().isEmpty());
	}

	@Test
	public void getContractorEmployeeDetailsIfPresentInDB() {

		when(contractorRepository.findAll(PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName"))))
				.thenReturn(createContractorPagedData());

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatils(0, 5, "contractorName", "ASC");
		verify(contractorRepository, times(1)).findAll(PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName")));
		String contractorName = contractorDto.getContent().get(0).getContractorName();
		assertEquals("Imagination", contractorName);
	}

	@Test
	public void getContractorEmployeeDetailsIfNotPresentInDB() {

		Page<Contractor> contractorPage = new PageImpl<>(new ArrayList<Contractor>(), PageRequest.of(0, 5), 0);
		when(contractorRepository.findAll(PageRequest.of(0, 5, Sort.by(Direction.DESC, "contractorName"))))
				.thenReturn(contractorPage);

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatils(0, 5, "contractorName", "DESC");
		verify(contractorRepository, times(1)).findAll(PageRequest.of(0, 5, Sort.by(Direction.DESC, "contractorName")));
		assertTrue(contractorDto.getContent().isEmpty());
	}

	@Test
	public void getContractorDeatilsContainingNameIfNameExistsInDB() {

		when(contractorRepository.findByContractorNameContains("Im",
				PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName"))))
						.thenReturn(createContractorPagedData());

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatilsContainingName("Im", 0, 5,
				"contractorName", "ASC");
		verify(contractorRepository, times(1)).findByContractorNameContains("Im",
				PageRequest.of(0, 5, Sort.by(Direction.ASC, "contractorName")));
		String contractorName = contractorDto.getContent().get(0).getContractorName();
		assertEquals("Imagination", contractorName);
	}

	@Test
	public void getContractorDeatilsContainingNameIfNameNotExistsInDB() {

		Page<Contractor> contractorPage = new PageImpl<>(new ArrayList<Contractor>(), PageRequest.of(0, 5), 0);
		when(contractorRepository.findByContractorNameContains("Test",
				PageRequest.of(0, 5, Sort.by(Direction.DESC, "contractorName")))).thenReturn(contractorPage);

		Page<ContractorDto> contractorDto = contractorServiceImpl.getContractorDeatilsContainingName("Test", 0, 5,
				"contractorName", "DESC");
		verify(contractorRepository, times(1)).findByContractorNameContains("Test",
				PageRequest.of(0, 5, Sort.by(Direction.DESC, "contractorName")));
		assertTrue(contractorDto.getContent().isEmpty());
	}

	@Test
	public void getContractorByContractorIdIfIdExistInDB() {
		Contractor contractor = Mockito.mock(Contractor.class);// new
																// Contractor();

		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(1001);
		contractorDto.setContractorName("Imagination");
		contractorDto.setCompanyType("LTD");
		contractorDto.setContactDetails("12341234");
		contractorDto.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractorDto.setChangedBy("Test");

		when(contractorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contractor));
		when(contractorMapper.toContractorDtoFromContractorDomain(contractor)).thenReturn(contractorDto);

		ContractorDto response = contractorServiceImpl.getContractorByContractorId(1001L);

		assertEquals("Imagination", response.getContractorName());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getContractorByContractorIdIfIdNotExistInDB() {

		when(contractorRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		contractorServiceImpl.getContractorByContractorId(1001L);
	}

	@Test
	public void getContractorEmployeeByContractorIdAndEmployeeIdIfExistInDB() {
		ContractorEmployee contractorEmployee = Mockito.mock(ContractorEmployee.class);
		ContractorEmployeeDefaultRate employeeDefaultRate = Mockito.mock(ContractorEmployeeDefaultRate.class);
		
		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setContractorEmployeeId("2001");
		contractorEmployeeDto.setEmployeeId("3001");
		contractorEmployeeDto.setContractorEmployeeName("John");
		contractorEmployeeDto.setKnownAs("Alias");
		
		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);
		when(contractorEmployee.getContractorEmployeeDefaultRate()).thenReturn(employeeDefaultRate);
		when(employeeDefaultRate.getRate()).thenReturn(new BigDecimal(1000));
		List<String> projects = new ArrayList<>();
		projects.add("Project1"); projects.add("Project2");
		when(bookingRevisionRepository.findByContractEmployeeId(Mockito.anyLong())).thenReturn(projects);
		ContractorEmployeeDto response = contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L,
				3001L);
		assertEquals("John", response.getContractorEmployeeName());
	}

	@Test
	public void getContractorEmployeeByContractorIdAndEmployeeIdIfNotExistInDB() {
		ContractorEmployee contractorEmployee = null;
		ContractorEmployeeDto contractorEmployeeDto = Mockito.mock(ContractorEmployeeDto.class);
		
		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);

		contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeRepository, times(1)).findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeMapper, times(1)).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
	}

	@Test
	public void getContractorEmployeeByContractorIdAndEmployeeIdIfRateNotExistInDB() {
		ContractorEmployee contractorEmployee = Mockito.mock(ContractorEmployee.class);
		ContractorEmployeeDto contractorEmployeeDto = Mockito.mock(ContractorEmployeeDto.class);
		
		when(contractorEmployeeRepository.findContractorEmployeeByContractorIdAndEmployeeId(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(contractorEmployee);
		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee))
				.thenReturn(contractorEmployeeDto);
		when(contractorEmployee.getContractorEmployeeDefaultRate()).thenReturn(null);
		
		List<String> projects = new ArrayList<>();
		when(bookingRevisionRepository.findByContractEmployeeId(Mockito.anyLong())).thenReturn(projects);
		
		contractorServiceImpl.getContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeRepository, times(1)).findContractorEmployeeByContractorIdAndEmployeeId(2001L, 3001L);
		verify(contractorEmployeeMapper, times(1)).toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
	}

	@Test
	public void addContractorDetailsSucess() {
		ContractorRequest contractorRequest = new ContractorRequest();
		contractorRequest.setContractorName("Imagination");
		contractorRequest.setServiceProvided("LTD");
		contractorRequest.setContactDetails("1234512345");

		ContractorDto contractorDto = new ContractorDto();
		contractorDto.setContractorId(1001);
		contractorDto.setContractorName("Imagination");
		contractorDto.setCompanyType("LTD");
		contractorDto.setContactDetails("12341234");
		contractorDto.setChangedDate(new Timestamp(System.currentTimeMillis()));
		contractorDto.setChangedBy("Test");

		CBSUser cbsUser = Mockito.mock(CBSUser.class);
		Contractor contractor = Mockito.mock(Contractor.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorMapper.toContractorDomainFromContractorRequest(Mockito.any())).thenReturn(contractor);
		when(contractorRepository.save(contractor)).thenReturn(contractor);
		when(contractorMapper.toContractorDtoFromContractorDomain(contractor)).thenReturn(contractorDto);

		ContractorDto response = contractorServiceImpl.addContractorDetails(contractorRequest);

		assertEquals("Imagination", response.getContractorName());
		assertEquals("LTD", response.getCompanyType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void addContractorDetailsWithWrongInput() {
		ContractorRequest contractorRequest = new ContractorRequest();
		contractorRequest.setContractorName("Imagination");
		contractorRequest.setServiceProvided("");
		contractorRequest.setContactDetails("1234512345");

		CBSUser cbsUser = Mockito.mock(CBSUser.class);
		Contractor contractor = Mockito.mock(Contractor.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorMapper.toContractorDomainFromContractorRequest(Mockito.any())).thenReturn(contractor);
		when(contractorRepository.save(contractor)).thenThrow(new IllegalArgumentException());

		// ContractorDto response =
		contractorServiceImpl.addContractorDetails(contractorRequest);

	}

	@Test
	public void addContractorEmployeeSucess() {
		ContractorEmployeeRequest contractorEmployeeRequest = new ContractorEmployeeRequest();
		contractorEmployeeRequest.setContractorEmployeeName("Alex");
		contractorEmployeeRequest.setKnownAs("Alias 1");
		contractorEmployeeRequest.setRoleId(201L);
		contractorEmployeeRequest.setDayRate(new BigDecimal(500));
		contractorEmployeeRequest.setCurrencyId(101L);

		ContractorEmployeeDto contractorEmployeeDto = new ContractorEmployeeDto();
		contractorEmployeeDto.setContractorEmployeeId("1001");
		contractorEmployeeDto.setEmployeeId("101");
		contractorEmployeeDto.setContractorEmployeeName("Alex");
		contractorEmployeeDto.setKnownAs("Alias 1");
		contractorEmployeeDto.setChangedBy("Test");

		CBSUser cbsUser = Mockito.mock(CBSUser.class);
		Contractor contractor = Mockito.mock(Contractor.class);
		RoleDm roleDm = Mockito.mock(RoleDm.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contractor));
		when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(roleDm));

		when(contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(Mockito.any()))
				.thenReturn(contractorEmployeeDto);

		ContractorEmployeeDto response = contractorServiceImpl.addContractorEmployee(2001L, contractorEmployeeRequest);

		assertEquals("Alex", response.getContractorEmployeeName());
		assertEquals("1001", response.getContractorEmployeeId());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void addContractorEmployeeIfContractorNotFound() {
		CBSUser cbsUser = Mockito.mock(CBSUser.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		contractorServiceImpl.addContractorEmployee(2001L, new ContractorEmployeeRequest());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void addContractorEmployeeIfRoleIdNotFound() {
		CBSUser cbsUser = Mockito.mock(CBSUser.class);
		Contractor contractor = Mockito.mock(Contractor.class);

		when(loggedInUserService.getLoggedInUserDetails()).thenReturn(cbsUser);
		when(contractorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contractor));
		when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ContractorEmployeeRequest contractorEmployeeRequest = new ContractorEmployeeRequest();
		contractorEmployeeRequest.setRoleId(101L);
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

}