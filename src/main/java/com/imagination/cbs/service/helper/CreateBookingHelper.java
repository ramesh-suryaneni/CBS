package com.imagination.cbs.service.helper;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.ContractorWorkSite;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SiteOptions;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.exception.CBSApplicationException;
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
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.CBSDateUtils;

/**
 * @author pravin.budage
 *
 */
@Service("createBookingHelper")
public class CreateBookingHelper {

	@Autowired
	private MaconomyService maconomyService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private ContractorRepository contractorRepository;

	@Autowired
	private SiteOptionsRepository siteOptionsRepository;

	@Autowired
	private SupplierTypeRepository supplierTypeRepository;

	@Autowired
	private RecruitingRepository recruitingRepository;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private ContractorEmployeeRepository contractorEmployeeRepository;

	@Autowired
	private BookingMapper bookingMapper;

	@Autowired
	private LoggedInUserService loggedInUserService;

	public Booking populateBooking(BookingRequest bookingRequest, Long revisionNumber, boolean isSubmit) {

		CBSUser user = loggedInUserService.getLoggedInUserDetails();
		String loggedInUser = user.getDisplayName();
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy(loggedInUser);
		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(bookingRequest);
		bookingDomain.setChangedBy(loggedInUser);
		bookingDomain.setBookingDescription(bookingRequest.getBookingDescription());
		// Adding Role
		checkRole(bookingRequest, bookingRevision);
		// Booking Status
		checkStatus(isSubmit, bookingRevision, bookingDomain);
		// This is for booking job number
		checkJobNumber(bookingRequest, isSubmit, bookingRevision, bookingDomain);

		bookingRevision
				.setContractedFromDate(CBSDateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(CBSDateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(loggedInUser);
		bookingRevision.setRevisionNumber(revisionNumber);
		// Contractor Details
		checkContractor(bookingRequest, bookingRevision);
		// Employee details
		checkEmployee(bookingRequest, bookingRevision);
		// Office Details
		checkOffice(bookingRequest, bookingRevision);
		// Work Location
		checkWorkLocation(bookingRequest, bookingRevision);
		// Supplier Type
		checkSupplierType(bookingRequest, bookingRevision);
		// Reason for recruting details
		checkReasonForRecruiting(bookingRequest, bookingRevision);
		if (!StringUtils.isEmpty(bookingRequest.getContractorTotalAvailableDays())) {
			bookingRevision
					.setContractorTotalAvailableDays(Long.valueOf(bookingRequest.getContractorTotalAvailableDays()));
		}
		if (!StringUtils.isEmpty(bookingRequest.getContractorTotalWorkingDays())) {
			bookingRevision.setContractorTotalWorkingDays(Long.valueOf(bookingRequest.getContractorTotalWorkingDays()));
		}
		if (!StringUtils.isEmpty(bookingRequest.getRate())) {
			bookingRevision.setRate(new BigDecimal(bookingRequest.getRate()));
		}
		if (!StringUtils.isEmpty(bookingRequest.getContractAmountBeforetax())) {
			bookingRevision.setContractAmountBeforetax(new BigDecimal(bookingRequest.getContractAmountBeforetax()));
		}
		if (!StringUtils.isEmpty(bookingRequest.getContractAmountAftertax())) {
			bookingRevision.setContractAmountAftertax(new BigDecimal(bookingRequest.getContractAmountAftertax()));
		}
		if (!StringUtils.isEmpty(bookingRequest.getEmployerTaxPercent())) {
			bookingRevision.setEmployerTaxPercent(new BigDecimal(bookingRequest.getEmployerTaxPercent()));
		} else {
			bookingRevision.setEmployerTaxPercent(new BigDecimal(12));
		}
		// Currency Details
		checkCurrency(bookingRequest, bookingRevision);
		
		// Comoff Regions
		if (!StringUtils.isEmpty(bookingRequest.getCommOffRegion())) {
			Optional<Region> commOffRegion = regionRepository.findById(Long.valueOf(bookingRequest.getCommOffRegion()));
			bookingRevision.setCommOffRegion(commOffRegion.isPresent() ? commOffRegion.get() : null);
		}
		// ContractorWorkRegion
		if (!StringUtils.isEmpty(bookingRequest.getContractorWorkRegion())) {
			Optional<Region> contractorWorkRegion = regionRepository
					.findById(Long.valueOf(bookingRequest.getContractorWorkRegion()));
			bookingRevision
					.setContractorWorkRegion(contractorWorkRegion.isPresent() ? contractorWorkRegion.get() : null);
		}
		// Work Days
		populateMonthlyWorkDays(bookingRequest, bookingRevision);
		// Work Tasks
		populateWorkTasks(bookingRequest, bookingRevision);
		// Site Options
		populateSiteOptions(bookingRequest, bookingRevision);
		bookingDomain.addBookingRevision(bookingRevision);
		return bookingDomain;
	}

	private void checkCurrency(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getCurrencyId())) {
			Optional<CurrencyDm> currency = currencyRepository.findById(Long.valueOf(bookingRequest.getCurrencyId()));
			bookingRevision.setCurrency(currency.isPresent() ? currency.get() : null);
		}
	}

	private void checkReasonForRecruiting(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getReasonForRecruiting())) {
			Optional<ReasonsForRecruiting> reasonsForRecruiting = recruitingRepository
					.findById(Long.valueOf(bookingRequest.getReasonForRecruiting()));
			bookingRevision
					.setReasonForRecruiting(reasonsForRecruiting.isPresent() ? reasonsForRecruiting.get() : null);
		}
	}

	private void checkSupplierType(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getSupplierTypeId())) {
			Optional<SupplierTypeDm> supplierType = supplierTypeRepository
					.findById(Long.valueOf(bookingRequest.getSupplierTypeId()));
			bookingRevision.setSupplierType(supplierType.isPresent() ? supplierType.get() : null);
		}
	}

	private void checkWorkLocation(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getContractWorkLocation())) {
			Optional<OfficeDm> workLocation = officeRepository
					.findById(Long.valueOf(bookingRequest.getContractWorkLocation()));
			bookingRevision.setContractWorkLocation(workLocation.isPresent() ? workLocation.get() : null);
		}
	}

	private void checkOffice(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getCommisioningOffice())) {
			Optional<OfficeDm> commisioningOffice = officeRepository
					.findById(Long.valueOf(bookingRequest.getCommisioningOffice()));
			bookingRevision.setCommisioningOffice(commisioningOffice.isPresent() ? commisioningOffice.get() : null);
		}
	}

	private void checkEmployee(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getContractEmployeeId())) {
			Optional<ContractorEmployee> contractorEmployee = contractorEmployeeRepository
					.findById(Long.valueOf(bookingRequest.getContractEmployeeId()));
			bookingRevision.setContractEmployee(contractorEmployee.isPresent() ? contractorEmployee.get() : null);
		}
	}

	private void checkContractor(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getContractorId())) {
			Optional<Contractor> contractor = contractorRepository
					.findById(Long.parseLong(bookingRequest.getContractorId()));
			bookingRevision.setContractor(contractor.isPresent() ? contractor.get() : null);
		}
	}

	private void checkJobNumber(BookingRequest bookingRequest, boolean isSubmit, BookingRevision bookingRevision,
			Booking bookingDomain) {
		if (bookingRequest.getJobNumber() != null) {
			try {
				JobDataDto jobDetails = maconomyService.getMaconomyJobNumberAndDepartmentsDetails(
						bookingRequest.getJobNumber(), new JobDataDto(),
						MaconomyConstant.MACANOMY_JOB_NUMBER.getMacanomy(), "");
				String deptName = jobDetails.getData().getText3();
				bookingRevision.setJobDeptName(deptName);
				bookingRevision.setJobname(jobDetails.getData().getJobName());
				bookingRevision.setJobNumber(jobDetails.getData().getJobNumber());

				ApproverTeamDto approverTeamDetails = maconomyService.getMaconomyJobNumberAndDepartmentsDetails("",
						new ApproverTeamDto(), MaconomyConstant.MACANOMY_DEPARTMENT_NAME.getMacanomy(), deptName);

				String remark3 = approverTeamDetails.getData().getRemark3();
				Team teamOne = teamRepository.findByTeamName(remark3);
				bookingDomain.setTeam(teamOne);
				bookingRevision.setTeam(teamOne);
			} catch (Exception e) {
				if (isSubmit) {
					throw new CBSApplicationException("Invalid Job Number");
				}
			}
		}
	}

	private void checkStatus(boolean isSubmit, BookingRevision bookingRevision, Booking bookingDomain) {
		ApprovalStatusDm status = new ApprovalStatusDm();
		if (isSubmit) {
			status.setApprovalStatusId(ApprovalStatusConstant.APPROVAL_1.getApprovalStatusId());
		} else {
			status.setApprovalStatusId(ApprovalStatusConstant.APPROVAL_DRAFT.getApprovalStatusId());
		}
		bookingDomain.setApprovalStatus(status);
		bookingRevision.setApprovalStatus(status);
	}

	private void checkRole(BookingRequest bookingRequest, BookingRevision bookingRevision) {
		if (!StringUtils.isEmpty(bookingRequest.getRoleId())) {
			Optional<RoleDm> roleDm = roleRepository.findById(Long.parseLong(bookingRequest.getRoleId()));
			if (roleDm.isPresent()) {
				bookingRevision.setRole(roleDm.get());
				bookingRevision.setInsideIr35(roleDm.get().getInsideIr35());
			}
		}
	}

	private void populateSiteOptions(BookingRequest bookingRequest, BookingRevision savedBookingRevision) {
		if (bookingRequest.getSiteOptions() != null) {
			List<ContractorWorkSite> list = new ArrayList<>();
			bookingRequest.getSiteOptions().forEach(siteId -> {
				SiteOptions siteOptions = siteOptionsRepository.findById(Long.valueOf(siteId)).get();
				ContractorWorkSite contractorWorkSite = new ContractorWorkSite();
				contractorWorkSite.setBookingRevision(savedBookingRevision);
				contractorWorkSite.setSiteOptions(siteOptions);
				list.add(contractorWorkSite);
			});
			savedBookingRevision.setContractorWorkSites(list);
		}
	}

	private void populateMonthlyWorkDays(BookingRequest bookingRequest, BookingRevision savedBookingRevision) {
		if (bookingRequest.getWorkDays() != null) {
			List<ContractorMonthlyWorkDay> monthlyWorkDays = bookingRequest.getWorkDays().stream().map(work -> {
				ContractorMonthlyWorkDay monthlyWorkday = new ContractorMonthlyWorkDay();
				monthlyWorkday.setMonthName(work.getMonthName());
				monthlyWorkday.setMonthWorkingDays(Long.parseLong(work.getMonthWorkingDays()));
				monthlyWorkday.setChangedBy(savedBookingRevision.getChangedBy());
				monthlyWorkday.setBookingRevision(savedBookingRevision);
				return monthlyWorkday;
			}).collect(toList());
			savedBookingRevision.setMonthlyWorkDays(monthlyWorkDays);
		}

	}

	private void populateWorkTasks(BookingRequest bookingRequest, BookingRevision savedBookingRevision) {
		if (bookingRequest.getWorkTasks() != null) {
			List<BookingWorkTask> bookingWorkTasks = bookingRequest.getWorkTasks().stream().map(work -> {
				BookingWorkTask bookingWorkTask = new BookingWorkTask();
				bookingWorkTask.setTaskDeliveryDate(CBSDateUtils.convertStringToDate(work.getTaskDeliveryDate()));
				bookingWorkTask.setTaskName(work.getTaskName());
				bookingWorkTask.setTaskDateRate(Double.parseDouble(work.getTaskDateRate()));
				bookingWorkTask.setTaskTotalDays(Long.parseLong(work.getTaskTotalDays()));
				bookingWorkTask.setTaskTotalAmount(Double.parseDouble(work.getTaskTotalAmount()));
				bookingWorkTask.setChangedBy(savedBookingRevision.getChangedBy());
				bookingWorkTask.setBookingRevision(savedBookingRevision);
				return bookingWorkTask;
			}).collect(toList());
			savedBookingRevision.setBookingWorkTasks(bookingWorkTasks);
		}
	}
}
