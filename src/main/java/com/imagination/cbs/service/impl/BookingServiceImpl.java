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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.ApproverOverrides;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.ContractorWorkSite;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.EmployeeMapping;
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
import com.imagination.cbs.exception.CBSUnAuthorizedException;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.ApproverOverridesRepository;
import com.imagination.cbs.repository.ApproverRepository;
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

	@Autowired
	private ApproverRepository approverRepository;

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
		ApprovalStatusDm status = new ApprovalStatusDm();
		if (isSubmit) {
			status.setApprovalStatusId(ApprovalStatusConstant.APPROVAL_1.getApprovalStatusId());
		} else {
			status.setApprovalStatusId(ApprovalStatusConstant.APPROVAL_DRAFT.getApprovalStatusId());
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

	@Transactional
	@Override
	public BookingDto approveBooking(ApproveRequest request) throws Exception {

		CBSUser user = loggedInUserService.getLoggedInUserDetails();

		try {

			Booking booking = bookingRepository.findById(Long.valueOf(request.getBookingId())).get();

			if ("APPROVE".equalsIgnoreCase(request.getAction())) {

				approve(booking, user);

			} else if ("HRAPPROVE".equalsIgnoreCase(request.getAction())) {

				hrApprove(booking, user);

			} else {
				throw new CBSValidationException(
						"Request can't be processed, action should be anyone of APPROVE||HRAPPROVE");
			}
			return retrieveBookingDetails(Long.valueOf(request.getBookingId()));

		} catch (Exception ex) {
			throw new CBSValidationException("No Booking Available with this number :" + request.getBookingId());
		}
	}

	private void approve(Booking booking, CBSUser user) {

		BookingRevision latestRevision = getLatestRevision(booking);

		String jobNumber = latestRevision.getJobNumber();
		Team approverTeam = latestRevision.getTeam();

		Long currentApprovalStatus = latestRevision.getApprovalStatus().getApprovalStatusId();
		boolean isInApprovalStatus = isInApproverStatus(currentApprovalStatus.intValue());
		if (isInApprovalStatus) {
			// find next approval status based on current status and approval
			// order
			Long nextStatus = getNextApprovalStatus(currentApprovalStatus, approverTeam);

			// check if booking can be override.
			ApproverOverrides approverOverride = approverOverridesRepository
					.findByEmployeeIdAndJobNumber(user.getEmpId(), jobNumber);
			if (approverOverride != null) {

				// save new revision with next status
				saveBooking(booking, latestRevision, 1005L, user);

				// TODO:send mail to next approver based on status

			} else if (isUserCanApprove(approverTeam.getTeamId(), user.getEmpId(), currentApprovalStatus)) {

				// save new revision with next status
				saveBooking(booking, latestRevision, nextStatus, user);

				// TODO:send mail to next approver based on status

			} else {
				throw new CBSUnAuthorizedException(
						"Not Authorized to perform this operation; insufficient previllages");
			}

		} else {
			throw new CBSValidationException("Request can't be processed, Booking is not in approval status");
		}

	}

	private void hrApprove(Booking booking, CBSUser user) {

	}

	private Booking saveBooking(Booking booking, BookingRevision revision, Long nextStatus, CBSUser user) {
		ApprovalStatusDm nextApprovalStatus = approvalStatusDmRepository.findById(nextStatus).get();
		revision.setBookingRevisionId(null);
		revision.setApprovalStatus(nextApprovalStatus);
		revision.setRevisionNumber(revision.getRevisionNumber() + 1);
		revision.setChangedBy(user.getDisplayName());

		booking.setApprovalStatus(nextApprovalStatus);
		booking.addBookingRevision(revision);

		bookingRepository.save(booking);

		return booking;
	}

	private BookingRevision getLatestRevision(Booking booking) {

		return booking.getBookingRevisions().stream().max(Comparator.comparing(BookingRevision::getRevisionNumber))
				.get();

	}

	private boolean isUserCanApprove(Long teamId, Long empId, Long currentStatus) {
		Long order = 0L;
		Team team = new Team();
		team.setTeamId(teamId);

		EmployeeMapping employee = new EmployeeMapping();
		employee.setEmployeeId(empId);
		switch (currentStatus.intValue()) {
		case 1002:
			order = 1L;
			break;
		case 1003:
			order = 2L;
			break;
		case 1004:
			order = 3L;
			break;
		}

		Approver approver = approverRepository.findByTeamAndEmployeeAndApproverOrder(team, employee, order);
		return (approver != null);
	}

	private boolean isInApproverStatus(int status) {
		boolean result = false;
		switch (status) {
		case 10001: // Waiting for Approval 1
		case 10002: // Waiting for Approval 2
		case 10003: // Waiting for Approval 3
			result = true;
		}
		return result;
	}

	private Long getNextApprovalStatus(Long currentStatus, Team approverTeam) {

		List<Approver> approvers = approverRepository.findAllByTeam(approverTeam);
		Long maxApproverOrder = approvers.stream().max(Comparator.comparing(Approver::getApproverOrder)).get()
				.getApproverOrder();

		Long nextStatus = null;

		switch (currentStatus.intValue()) {

		case 1002: // current status - waiting for approval 1

			switch (maxApproverOrder.intValue()) {
			case 1:
				nextStatus = 1005L; // Sent to HR
				break;
			case 2:
				nextStatus = 1003L; // waiting for approval 2
				break;
			case 3:
				nextStatus = 1004L; // waiting for approval 3
				break;
			}
			break;

		case 1003: // current status - waiting for approval 2

			switch (maxApproverOrder.intValue()) {
			case 2:
				nextStatus = 1005L; // Sent to HR
				break;
			case 3:
				nextStatus = 1004L; // waiting for approval 3
				break;
			}
			break;

		case 1004: // current status - waiting for approval 3
			nextStatus = 1005L; // Sent to HR
			break;

		}
		return nextStatus;
	}

}
