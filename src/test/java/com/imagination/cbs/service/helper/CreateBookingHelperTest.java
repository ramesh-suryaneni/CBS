package com.imagination.cbs.service.helper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

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
	public void test() {
		fail("Not yet implemented");
	}

}
