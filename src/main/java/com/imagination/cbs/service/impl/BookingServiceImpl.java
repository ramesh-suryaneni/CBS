/**
 * 
 */
package com.imagination.cbs.service.impl;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.ApproverOverrides;
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
import com.imagination.cbs.dto.ApproveRequest;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.ApproverOverridesRepository;
import com.imagination.cbs.repository.BookingRepository;
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
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.CBSDateUtils;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingMapper bookingMapper;

	@Autowired
	private MaconomyService maconomyService;

	@Autowired
	private MaconomyApproverTeamServiceImpl maconomyApproverTeamService;

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
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	@Autowired
	private LoggedInUserService loggedInUserService;

	@Autowired
	private TeamMapper teamMapper;

	@Autowired
	private DisciplineMapper disciplineMapper;

	@Autowired
	private ApproverOverridesRepository approverOverridesRepository;

	@Transactional
	@Override
	public BookingDto addBookingDetails(BookingRequest bookingRequest) {
		Booking bookingDomain = populateBooking(bookingRequest, 1L, false);
		Booking savedBooking = bookingRepository.save(bookingDomain);
		return retrieveBookingDetails(savedBooking.getBookingId());
	}

	@Transactional
	@Override
	public BookingDto updateBookingDetails(Long bookingId, BookingRequest bookingRequest) {
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		Booking newBookingDomain = populateBooking(bookingRequest, ++revisionNumber, false);
		newBookingDomain.setBookingId(bookingId);
		newBookingDomain.setBookingDescription(bookingDetails.getBookingDescription());
		newBookingDomain.setChangedDate(bookingDetails.getChangedDate());
		bookingRepository.save(newBookingDomain);
		return retrieveBookingDetails(bookingId);
	}

	@Transactional
	@Override
	public BookingDto submitBookingDetails(Long bookingId, BookingRequest bookingRequest) {
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		Booking newBookingDomain = populateBooking(bookingRequest, ++revisionNumber, true);
		newBookingDomain.setBookingId(bookingId);
		newBookingDomain.setBookingDescription(bookingDetails.getBookingDescription());
		newBookingDomain.setChangedDate(new Timestamp(System.currentTimeMillis()));
		bookingRepository.save(newBookingDomain);
		return retrieveBookingDetails(bookingId);
	}

	private Booking populateBooking(BookingRequest bookingRequest, Long revisionNumber, boolean isSubmit) {

		CBSUser user = loggedInUserService.getLoggedInUserDetails();
		String loggedInUser = user.getDisplayName();
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy(loggedInUser);

		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(bookingRequest);
		bookingDomain.setChangedBy(loggedInUser);
		bookingDomain.setBookingDescription(bookingRequest.getBookingDescription());
		// Adding Role
		if (!StringUtils.isEmpty(bookingRequest.getRoleId())) {
			RoleDm roleDm = roleRepository.findById(Long.parseLong(bookingRequest.getRoleId())).get();
			bookingRevision.setRole(roleDm);
			bookingRevision.setInsideIr35(roleDm.getInsideIr35());
		}
		// Booking Status
		ApprovalStatusDm status = null;
		if (isSubmit) {
			status = approvalStatusDmRepository.findByApprovalName(ApprovalStatusConstant.APPROVAL_1.getStatus());
		} else {
			status = approvalStatusDmRepository.findByApprovalName(ApprovalStatusConstant.APPROVAL_DRAFT.getStatus());
		}
		bookingDomain.setApprovalStatus(status);
		bookingRevision.setApprovalStatus(status);
		// This is for booking job number
		if (bookingRequest.getJobNumber() != null) {
			try {
				JobDataDto jobDetails = maconomyService.getJobDetails(bookingRequest.getJobNumber());
				String deptName = jobDetails.getData().getText3();
				bookingRevision.setJobDeptName(deptName);
				bookingRevision.setJobname(jobDetails.getData().getJobName());
				bookingRevision.setJobNumber(jobDetails.getData().getJobNumber());
				ApproverTeamDto approverTeamDetails = maconomyApproverTeamService.getApproverTeamDetails(deptName);

				String remark3 = approverTeamDetails.getData().getRemark3();
				Team teamOne = teamRepository.findByTeamName(remark3);
				bookingDomain.setTeam(teamOne);
			} catch (Exception e) {
				if (isSubmit) {
					throw new CBSValidationException("Invalid Job Number");
				}
			}
		}

		bookingRevision
				.setContractedFromDate(CBSDateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(CBSDateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(loggedInUser);
		bookingRevision.setRevisionNumber(revisionNumber);
		// Contractor Details
		if (!StringUtils.isEmpty(bookingRequest.getContractorId())) {
			Contractor contractor = contractorRepository.findById(Long.parseLong(bookingRequest.getContractorId()))
					.get();
			bookingRevision.setContractor(contractor);
		}
		// Employee details
		if (!StringUtils.isEmpty(bookingRequest.getContractEmployeeId())) {
			ContractorEmployee contractorEmployee = contractorEmployeeRepository
					.findById(Long.valueOf(bookingRequest.getContractEmployeeId())).get();
			bookingRevision.setContractEmployee(contractorEmployee);
		}
		// Office Details
		if (!StringUtils.isEmpty(bookingRequest.getCommisioningOffice())) {
			OfficeDm commisioningOffice = officeRepository
					.findById(Long.valueOf(bookingRequest.getCommisioningOffice())).get();
			bookingRevision.setCommisioningOffice(commisioningOffice);
		}
		// Work Location
		if (!StringUtils.isEmpty(bookingRequest.getContractWorkLocation())) {
			OfficeDm workLocation = officeRepository.findById(Long.valueOf(bookingRequest.getContractWorkLocation()))
					.get();
			bookingRevision.setContractWorkLocation(workLocation);
		}
		if (!StringUtils.isEmpty(bookingRequest.getSupplierTypeId())) {
			SupplierTypeDm supplierType = supplierTypeRepository
					.findById(Long.valueOf(bookingRequest.getSupplierTypeId())).get();
			bookingRevision.setSupplierType(supplierType);
		}
		// Reason for recruting details
		if (!StringUtils.isEmpty(bookingRequest.getReasonForRecruiting())) {
			ReasonsForRecruiting reasonsForRecruiting = recruitingRepository
					.findById(Long.valueOf(bookingRequest.getReasonForRecruiting())).get();
			bookingRevision.setReasonForRecruiting(reasonsForRecruiting);
		}
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
		if (!StringUtils.isEmpty(bookingRequest.getCurrencyId())) {
			CurrencyDm currency = currencyRepository.findById(Long.valueOf(bookingRequest.getCurrencyId())).get();
			bookingRevision.setCurrency(currency);
		}
		// Comoff Regions
		if (!StringUtils.isEmpty(bookingRequest.getCommOffRegion())) {
			Region commOffRegion = regionRepository.findById(Long.valueOf(bookingRequest.getCommOffRegion())).get();
			bookingRevision.setCommOffRegion(commOffRegion);
		}
		// ContractorWorkRegion
		if (!StringUtils.isEmpty(bookingRequest.getContractorWorkRegion())) {
			Region contractorWorkRegion = regionRepository
					.findById(Long.valueOf(bookingRequest.getContractorWorkRegion())).get();
			bookingRevision.setContractorWorkRegion(contractorWorkRegion);
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

	@Override
	public BookingDto retrieveBookingDetails(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId).get();
		BookingRevision bookingRevision = booking.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get();
		BookingDto bookingDto = bookingMapper.convertToDto(bookingRevision);
		bookingDto.setTeam(teamMapper.toTeamDtoFromTeamDomain(booking.getTeam()));
		bookingDto.setBookingId(String.valueOf(booking.getBookingId()));
		bookingDto.setBookingDescription(booking.getBookingDescription());
		bookingDto.setDiscipline(
				disciplineMapper.toDisciplineDtoFromDisciplineDomain(bookingRevision.getRole().getDiscipline()));
		return bookingDto;
	}

	private void populateSiteOptions(BookingRequest bookingRequest, BookingRevision savedBookingRevision) {
		if (bookingRequest.getSiteOptions() != null) {
			List<ContractorWorkSite> list = new ArrayList<ContractorWorkSite>();
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
				bookingWorkTask.setTaskDeliveryDate(Date.valueOf(work.getTaskDeliveryDate()));
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

	@Override
	public BookingDto cancelooking(Long bookingId) {

		// TODO: cancel booking

		return retrieveBookingDetails(bookingId);
	}

	@Override
	public BookingDto approveBooking(ApproveRequest request) {

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		Booking booking = bookingRepository.findById(Long.valueOf(request.getBookingId())).get();

		BookingRevision latestRevision = getLatestRevision(booking);

		String jobNumber = latestRevision.getJobNumber();
		Team approverTeam = latestRevision.getTeam();

		Long currentApprovalStatus = latestRevision.getApprovalStatus().getApprovalStatusId();
		boolean isInApprovalStatus = isInApproverStatus(currentApprovalStatus.intValue());

		if (isInApprovalStatus) {
			ApprovalStatusDm nextApprovalStatus = approvalStatusDmRepository.findById(1000L).get(); // pass
																									// next
																									// status
																									// id;

			switch (request.getAction()) {
			case "APPROVE":
				// check if booking can be overrided.
				ApproverOverrides approverOverride = approverOverridesRepository
						.findByEmployeeIdAndJobNumber(user.getEmpId(), jobNumber);
				if (approverOverride != null) {
					// TODO: process request
				} else if (isUserCanApprove(approverTeam.getTeamId(), user.getEmpId(), currentApprovalStatus)) {
					// TODO: process request

				} else {
					// TODO:throw 403 error
				}
				break;
			case "HRAPPROVE":

			}

			return retrieveBookingDetails(Long.valueOf(request.getBookingId()));
		}

		return retrieveBookingDetails(Long.valueOf(request.getBookingId()));
	}

	private BookingRevision getLatestRevision(Booking booking) {

		return booking.getBookingRevisions().stream().max(Comparator.comparing(BookingRevision::getRevisionNumber))
				.get();

	}

	private boolean isUserCanApprove(Long teamId, Long empId, Long currentStatus) {
		// TODO:check if user is part of approval process as per team
		return true;
	}

	private boolean isInApproverStatus(int status) {
		// TODO:implement switch to get status, with ENUM;
		/*
		 * switch(currentApprovalStatus.intValue()) { case
		 * ApprovalStatusConstant.APPROVAL_1: }
		 */
		boolean result = false;
		switch (status) {
		case 10001:
		case 10002:
		case 10003:
			result = true;
		}
		return result;
	}

}
