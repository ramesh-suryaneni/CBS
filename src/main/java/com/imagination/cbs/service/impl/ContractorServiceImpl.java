package com.imagination.cbs.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.imagination.cbs.constant.ApprovalStatusConstant;
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
import com.imagination.cbs.service.ContractorService;
import com.imagination.cbs.service.LoggedInUserService;

@Service("contractorService")
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorRepository contractorRepository;

	@Autowired
	private ContractorEmployeeSearchRepository contractorEmployeeSearchRepository;

	@Autowired
	private ContractorEmployeeRepository contractorEmployeeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookingRevisionRepository bookingRevisionRepository;

	@Autowired
	private ContractorEmployeeMapper contractorEmployeeMapper;

	@Autowired
	private ContractorMapper contractorMapper;

	@Autowired
	private LoggedInUserService loggedInUserService;

	@Override
	public Page<ContractorDto> getContractorDeatils(int pageNo, int pageSize, String sortingField,
			String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		return toContractorDtoPage(contractorRepository.findAll(pageable));
	}

	@Override
	public Page<ContractorDto> getContractorDeatilsContainingName(String contractorName, int pageNo, int pageSize,
			String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		return toContractorDtoPage(contractorRepository.findByContractorNameContains(contractorName, pageable));
	}

	@Override
	public Page<ContractorEmployeeSearchDto> getContractorEmployeeDetails(int pageNo, int pageSize, String sortingField,
			String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findAll(pageable);

		return toContractorEmployeeDtoPage(contractorEmployeePage);
	}

	@Override
	public Page<ContractorEmployeeSearchDto> getContractorEmployeeDetailsByRoleName(String roleName, int pageNo, int pageSize,
			String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findByRoleContainingIgnoreCase(roleName,
				pageable);

		return toContractorEmployeeDtoPage(contractorEmployeePage);
	}

	@Override
	public Page<ContractorEmployeeSearchDto> getContractorEmployeeDetailsByName(String contractorEmployeeName, int pageNo, int pageSize,
			String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findByContractorEmployeeNameContainingIgnoreCase(contractorEmployeeName,
				pageable);

		return toContractorEmployeeDtoPage(contractorEmployeePage);
	}

	@Override
	public Page<ContractorEmployeeSearchDto> getContractorEmployeeDetailsByNameAndRoleName(String contractorEmployeeName, String roleName, int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Pageable pageable = createPageable(pageNo, pageSize, sortingField, sortingOrder);
		Page<ContractorEmployeeSearch> contractorEmployeePage = contractorEmployeeSearchRepository.findByContractorEmployeeNameAndRoleName(contractorEmployeeName, roleName, pageable);

		return toContractorEmployeeDtoPage(contractorEmployeePage);
	}

	@Override
	public ContractorDto getContractorByContractorId(Long id) {

		Optional<Contractor> optionalContractor = contractorRepository.findById(id);

		if (!optionalContractor.isPresent()) {
			throw new ResourceNotFoundException("Contactor Not Found with Id:- " + id);
		}

		return contractorMapper.toContractorDtoFromContractorDomain(optionalContractor.get());
	}

	@Override
	public ContractorEmployeeDto getContractorEmployeeByContractorIdAndEmployeeId(Long contractorId, Long employeeId) {

		ContractorEmployee contractorEmployee = contractorEmployeeRepository
				.findContractorEmployeeByContractorIdAndEmployeeId(contractorId, employeeId);
		ContractorEmployeeDto contractorEmployeeDto = contractorEmployeeMapper
				.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee);
		if (null != contractorEmployee) {
			if (null != contractorEmployee.getContractorEmployeeDefaultRate()) {
				contractorEmployeeDto.setRate(contractorEmployee.getContractorEmployeeDefaultRate().getRate());
			}
			List<String> bookingRevisions = bookingRevisionRepository.findByContractEmployeeId(employeeId,
					ApprovalStatusConstant.APPROVAL_COMPLETED.getApprovalStatusId());
			contractorEmployeeDto.setProjects(bookingRevisions);
		}

		return contractorEmployeeDto;
	}

	@Transactional
	@Override
	public ContractorDto addContractorDetails(ContractorRequest contractorRequest) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();

		Contractor contractorDomain = contractorMapper.toContractorDomainFromContractorRequest(contractorRequest);
		contractorDomain.setChangedBy(loggedInUser);

		Contractor savedContractor = contractorRepository.save(contractorDomain);

		return contractorMapper.toContractorDtoFromContractorDomain(savedContractor);
	}

	@Override
	public ContractorEmployeeDto addContractorEmployee(Long contractorId, ContractorEmployeeRequest request) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		ContractorEmployee contractorEmpDomain = new ContractorEmployee();
		contractorEmpDomain.setContractorEmployeeName(request.getContractorEmployeeName());
		contractorEmpDomain.setKnownAs(request.getKnownAs());
		contractorEmpDomain.setChangedBy(loggedInUser);

		Optional<Contractor> optionalContractor = contractorRepository.findById(contractorId);
		if (!optionalContractor.isPresent()) {
			throw new ResourceNotFoundException("Contactor Not Found with Id:- " + contractorId);
		}
		contractorEmpDomain.setContractor(optionalContractor.get());

		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		if (!StringUtils.isEmpty(request.getRoleId())) {
			Optional<RoleDm> optionalRoleDm = roleRepository.findById(Long.parseLong(request.getRoleId()));
			if (!optionalRoleDm.isPresent()) {
				throw new ResourceNotFoundException("Role Not Found with Id:- " + request.getRoleId());
			}
			ContractorEmployeeRole contractorEmployeeRole = new ContractorEmployeeRole();
			contractorEmployeeRole.setRoleDm(optionalRoleDm.get());
			contractorEmployeeRole.setDateFrom(currentTimeStamp);
			contractorEmployeeRole.setChangedBy(loggedInUser);
			contractorEmployeeRole.setContractorEmployee(contractorEmpDomain);
			contractorEmpDomain.setContractorEmployeeRole(contractorEmployeeRole);
		}

		ContractorEmployeeDefaultRate contractorEmployeeDefaultRate = new ContractorEmployeeDefaultRate();
		contractorEmployeeDefaultRate.setCurrencyId(Long.parseLong(request.getCurrencyId()));
		contractorEmployeeDefaultRate.setRate(new BigDecimal(request.getDayRate()));
		contractorEmployeeDefaultRate.setDateFrom(currentTimeStamp);
		contractorEmployeeDefaultRate.setChangedBy(loggedInUser);
		contractorEmployeeDefaultRate.setContractorEmployee(contractorEmpDomain);
		contractorEmpDomain.setContractorEmployeeDefaultRate(contractorEmployeeDefaultRate);

		ContractorEmployee savedcontractorEmployee = contractorEmployeeRepository.save(contractorEmpDomain);
		return contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(savedcontractorEmployee);
	}

	private Pageable createPageable(int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Sort sort = null;
		if (sortingOrder.equals("ASC")) {
			sort = Sort.by(Direction.ASC, sortingField);
		}
		if (sortingOrder.equals("DESC")) {
			sort = Sort.by(Direction.DESC, sortingField);
		}

		return PageRequest.of(pageNo, pageSize, sort);
	}

	private Page<ContractorEmployeeSearchDto> toContractorEmployeeDtoPage(
			Page<ContractorEmployeeSearch> contractorEmployeePage) {
		return contractorEmployeePage.map(contractorEmployeeSearched -> {
			ContractorEmployeeSearchDto contractorEmployeeDto = new ContractorEmployeeSearchDto();
			contractorEmployeeDto.setContractorEmployeeId(contractorEmployeeSearched.getContractorEmployeeId());
			contractorEmployeeDto.setContractorEmployeeName(contractorEmployeeSearched.getContractorEmployeeName());
			contractorEmployeeDto.setDayRate(contractorEmployeeSearched.getDayRate());
			contractorEmployeeDto.setRoleId(contractorEmployeeSearched.getRoleId());
			contractorEmployeeDto.setRole(contractorEmployeeSearched.getRole());
			contractorEmployeeDto.setContractorId(contractorEmployeeSearched.getContractorId());
			contractorEmployeeDto.setCompany(contractorEmployeeSearched.getCompany());
			contractorEmployeeDto.setNoOfBookingsInPast(contractorEmployeeSearched.getNoOfBookingsInPast());
			contractorEmployeeDto.setCurrencyId(String.valueOf(contractorEmployeeSearched.getCurrencyId()));
			contractorEmployeeDto.setCurrencyName(contractorEmployeeSearched.getCurrencyName());
			
			return contractorEmployeeDto;
		});
	}

	private Page<ContractorDto> toContractorDtoPage(Page<Contractor> contractorPage) {
		return contractorPage.map(contractor -> {
			ContractorDto contractorDto = new ContractorDto();

			contractorDto.setContractorId(contractor.getContractorId());
			contractorDto.setContractorName(contractor.getContractorName());
			contractorDto.setCompanyType(contractor.getCompanyType());
			contractorDto.setContactDetails(contractor.getContactDetails());
			contractorDto.setChangedDate(contractor.getChangedDate());
			contractorDto.setChangedBy(contractor.getChangedBy());
			contractorDto.setStatus(contractor.getStatus());
			contractorDto.setMaconomyVendorNumber(contractor.getMaconomyVendorNumber());
			contractorDto.setAddressLine1(contractor.getAddressLine1());
			contractorDto.setAddresLine2(contractor.getAddresLine2());
			contractorDto.setAddresLine3(contractor.getAddresLine3());
			contractorDto.setPostalDistrict(contractor.getPostalDistrict());
			contractorDto.setPostalCode(contractor.getPostalCode());
			contractorDto.setCountry(contractor.getCountry());
			contractorDto.setAttention(contractor.getAttention());
			contractorDto.setEmail(contractor.getEmail());
			contractorDto.setOnPreferredSupplierList(contractor.getOnPreferredSupplierList());

			return contractorDto;
		});
	}

}
