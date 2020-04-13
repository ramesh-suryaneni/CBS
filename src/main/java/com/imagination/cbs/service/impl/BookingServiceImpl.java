/**
 * 
 */
package com.imagination.cbs.service.impl;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.constant.MaconomyConstant;
import com.imagination.cbs.constant.UserActionConstant;
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
import com.imagination.cbs.domain.EmployeePermissions;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.Permission;
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
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.CBSUnAuthorizedException;
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
import com.imagination.cbs.repository.EmployeeMappingRepository;
import com.imagination.cbs.repository.EmployeePermissionsRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.repository.RegionRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.repository.SiteOptionsRepository;
import com.imagination.cbs.repository.SupplierTypeRepository;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.security.CBSUser;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.EmailService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.CBSDateUtils;
import com.imagination.cbs.util.EmailConstants;
import com.imagination.cbs.util.SecurityConstants;

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

	@Autowired
	private EmailService emailService;

	private static final String FROM_EMAIL = "CBS@imagination.com";

	private static final String APPROVE_SUBJECT_LINE = "Please Approve: Contractor Booking request # from ";

	private static final String DECLINE_SUBJECT_LINE = "Declined : Contractor Booking request # from ";

	private static final String CANCELED_SUBJECT_LINE = "Cancelled: Contractor Booking request # from ";

	@Autowired
	private EmployeeMappingRepository employeeMappingRepository;

	@Autowired
	private EmployeePermissionsRepository employeePermissionsRepository;

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
		BookingRevision latestRevision = getLatestRevision(newBookingDomain);
		prepareMailAndSend(newBookingDomain, latestRevision);
		return retrieveBookingDetails(bookingId);
	}

	private void prepareMailAndSend(Booking booking, BookingRevision latestRevision) {
		Approver approver = approverRepository.findByTeamAndApproverOrder(booking.getTeam(), 1L);
		EmployeeMapping employee = approver.getEmployee();
		MailRequest request = new MailRequest();
		String[] toEmail = new String[] { employee.getGoogleAccount() + EmailConstants.DOMAIN };
		request.setMailTo(toEmail);
		request.setSubject(APPROVE_SUBJECT_LINE.replace("#", "#" + booking.getBookingId()) + latestRevision.getJobname()
				+ "-" + latestRevision.getChangedBy());
		request.setMailFrom(FROM_EMAIL);
		emailService.sendEmailForBookingApproval(request, latestRevision);
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
				// JobDataDto jobDetails =
				// maconomyService.getJobDetails(bookingRequest.getJobNumber());
				JobDataDto jobDetails = maconomyService.getMaconomyJobNumberAndDepartmentsDetails(
						bookingRequest.getJobNumber(), new JobDataDto(),
						MaconomyConstant.MACANOMY_JOB_NUMBER.getMacanomy(), "");
				String deptName = jobDetails.getData().getText3();
				bookingRevision.setJobDeptName(deptName);
				bookingRevision.setJobname(jobDetails.getData().getJobName());
				bookingRevision.setJobNumber(jobDetails.getData().getJobNumber());
				// ApproverTeamDto approverTeamDetails =
				// maconomyApproverTeamService.getApproverTeamDetails(deptName);
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
		bookingDto.setInsideIr35(bookingRevision.getRole().getInsideIr35());
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

	@Override
	@Transactional
	public BookingDto cancelBooking(Long bookingId) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		Booking booking = bookingRepository.findById(bookingId).get();

		if (booking.getChangedBy().equalsIgnoreCase(loggedInUser) && ApprovalStatusConstant.APPROVAL_DRAFT
				.getApprovalStatusId().equals(booking.getApprovalStatus().getApprovalStatusId())) {

			bookingRepository.delete(booking);

		} else if (booking.getChangedBy().equalsIgnoreCase(loggedInUser)) {

			BookingRevision latestBookingRevision = getLatestRevision(booking);

			saveBooking(booking, latestBookingRevision, ApprovalStatusConstant.APPROVAL_CANCELLED.getApprovalStatusId(),
					loggedInUserService.getLoggedInUserDetails());

			return retrieveBookingDetails(bookingId);

		}

		return new BookingDto();
	}

	@Transactional
	@Override
	public BookingDto approveBooking(ApproveRequest request) throws Exception {

		CBSUser user = loggedInUserService.getLoggedInUserDetails();
		Booking booking = null;
		try {
			booking = bookingRepository.findById(Long.valueOf(request.getBookingId())).get();

		} catch (Exception ex) {
			throw new CBSApplicationException("No Booking Available with this number :" + request.getBookingId());
		}

		if (UserActionConstant.APPROVE.getAction().equalsIgnoreCase(request.getAction())) {

			approve(booking, user);

		} else if (UserActionConstant.HRAPPROVE.getAction().equalsIgnoreCase(request.getAction())) {

			hrApprove(booking, user);

		} else if (UserActionConstant.DECLINE.getAction().equalsIgnoreCase(request.getAction())) {
			decline(booking, user);
		} else {
			throw new CBSApplicationException(
					"Request can't be processed, action should be anyone of APPROVE||HRAPPROVE||DECLINE");
		}
		return retrieveBookingDetails(Long.valueOf(request.getBookingId()));

	}

	private void approve(Booking booking, CBSUser user) {

		BookingRevision latestRevision = getLatestRevision(booking);

		String jobNumber = latestRevision.getJobNumber();
		Team approverTeam = latestRevision.getTeam();

		Long currentApprovalStatus = latestRevision.getApprovalStatus().getApprovalStatusId();
		boolean isInApprovalStatus = isInApproverStatus(currentApprovalStatus.intValue());
		if (isInApprovalStatus) {
			// find next approval status based on current status and approval
			Long nextStatus = getNextApprovalStatus(currentApprovalStatus, approverTeam);

			// check if booking can be override.
			ApproverOverrides approverOverride = approverOverridesRepository
					.findByEmployeeIdAndJobNumber(user.getEmpId(), jobNumber);

			if (approverOverride != null) {

				// save new revision with next status
				saveBooking(booking, latestRevision, ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId(),
						user);

				// TODO:send mail to HR approver
				prepareMailAndSendToHR(latestRevision);

			} else if (isUserCanApprove(approverTeam.getTeamId(), user.getEmpId(), currentApprovalStatus)) {

				// save new revision with next status
				saveBooking(booking, latestRevision, nextStatus, user);

				// TODO:send mail to next approver based on status
				Long order = null;
				switch (nextStatus.intValue()) {
				case 1002:
					order = 1L; // approver order#1
					break;
				case 1003:
					order = 2L; // approver order#2
					break;
				case 1004:
					order = 3L; // approver order#3
					break;
				case 1005:
					order = 5L; // HR Approver
					break;
				}
				if (5L != order) {
					prepareMailAndSend(booking, latestRevision);
				} else {
					prepareMailAndSendToHR(latestRevision);
				}

			} else {
				throw new CBSUnAuthorizedException(
						"Not Authorized to perform this operation; insufficient previllages");
			}

		} else {
			throw new CBSApplicationException("Request can't be processed, Booking is not in approval status");
		}

	}

	private void prepareMailAndSendToHR(BookingRevision latestRevision) {
		Permission permission = new Permission();
		permission.setPermissionId(SecurityConstants.ROLE_CONTRACT_MGT_ID);
		List<EmployeePermissions> employeePermissions = employeePermissionsRepository.findAllByPermission(permission);
		List<String> emails = new ArrayList<>();
		
		for (EmployeePermissions employeePermission : employeePermissions) {
			EmployeeMapping employee = employeeMappingRepository
					.findById(employeePermission.getEmployeeMapping().getEmployeeId()).get();
			emails.add(employee.getGoogleAccount() + EmailConstants.DOMAIN);
			
		}
		
		String[] toEmail = emails.stream().toArray(n -> new String[n]);
		MailRequest request = new MailRequest();
		request.setMailTo(toEmail);
		request.setSubject(APPROVE_SUBJECT_LINE.replace("#", "#" + latestRevision.getBooking().getBookingId())
				+ latestRevision.getJobname() + "-" + latestRevision.getChangedBy());
		request.setMailFrom(FROM_EMAIL);
		emailService.sendEmailForBookingApproval(request, latestRevision);

	}

	private void hrApprove(Booking booking, CBSUser user) {

		if (loggedInUserService.isCurrentUserInHRRole()) {
			Long currentStatus = booking.getApprovalStatus().getApprovalStatusId();
			if(ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId().equals(currentStatus)) {
				Long nextStatus = ApprovalStatusConstant.APPROVAL_SENT_FOR_CONTRACTOR.getApprovalStatusId();

				// TODO:Generate PDF.
				// TODO:integrate Adobe - upload, create agreement
				// TODO:populate document id and agreement id to revision
				BookingRevision latestRevision = getLatestRevision(booking);
				saveBooking(booking, latestRevision, nextStatus, loggedInUserService.getLoggedInUserDetails());
				// send Email to creator - need to confirm
				prepareMailAndSendToHR(latestRevision);
			}else {
				throw new CBSApplicationException("Booking already approved or not in approval status");
			}
			
		} else {
			throw new CBSUnAuthorizedException("Not Authorized to perform this operation; insufficient previllages");
		}
	}

	private void decline(Booking booking, CBSUser user) {

		Long nextStatus = ApprovalStatusConstant.APPROVAL_REJECTED.getApprovalStatusId();

		BookingRevision latestRevision = getLatestRevision(booking);
		saveBooking(booking, latestRevision, nextStatus, loggedInUserService.getLoggedInUserDetails());
		// TODO:send mail to creator
		MailRequest request = new MailRequest();
		request.setMailTo(new String[] { booking.getChangedBy()+ EmailConstants.DOMAIN });
		request.setSubject(DECLINE_SUBJECT_LINE.replace("#", "#" + booking.getBookingId()) + latestRevision.getJobname()
				+ "-" + latestRevision.getChangedBy());
		request.setMailFrom(FROM_EMAIL);
		emailService.sendEmailForBookingApproval(request, latestRevision);

	}

	private Booking saveBooking(Booking booking, BookingRevision revision, Long nextStatus, CBSUser user) {
		ApprovalStatusDm nextApprovalStatus = approvalStatusDmRepository.findById(nextStatus).get();
		// revision.setBookingRevisionId(null);
		BookingRevision newObject = new BookingRevision(revision);
		newObject.setApprovalStatus(nextApprovalStatus);
		newObject.setRevisionNumber(revision.getRevisionNumber() + 1);
		newObject.setChangedBy(user.getDisplayName());

		booking.setApprovalStatus(nextApprovalStatus);
		booking.addBookingRevision(newObject);

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
			order = 1L; // approver order#1
			break;
		case 1003:
			order = 2L; // approver order#2
			break;
		case 1004:
			order = 3L; // approver order#3
			break;
		}

		Approver approver = approverRepository.findByTeamAndEmployeeAndApproverOrder(team, employee, order);
		return (approver != null);
	}

	private boolean isInApproverStatus(int status) {
		boolean result = false;
		switch (status) {
		case 1002: // Waiting for Approval 1
		case 1003: // Waiting for Approval 2
		case 1004: // Waiting for Approval 3
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
				nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId(); // Sent
																								// to
																								// HR
				break;
			case 2:
				nextStatus = ApprovalStatusConstant.APPROVAL_2.getApprovalStatusId(); // waiting
																						// for
																						// approval
																						// 2
				break;
			case 3:
				nextStatus = ApprovalStatusConstant.APPROVAL_3.getApprovalStatusId(); // waiting
																						// for
																						// approval
																						// 3
				break;
			}
			break;

		case 1003: // current status - waiting for approval 2

			switch (maxApproverOrder.intValue()) {
			case 2:
				nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId(); // Sent
																								// to
																								// HR
				break;
			case 3:
				nextStatus = ApprovalStatusConstant.APPROVAL_3.getApprovalStatusId(); // waiting
																						// for
																						// approval
																						// 3
				break;
			}
			break;

		case 1004: // current status - waiting for approval 3
			nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId(); // Sent
																							// to
																							// HR
			break;

		}
		return nextStatus;
	}

	@Override
	public void updateContract(String contractor, String date) {
		// TODO:update contract signed details to booking
		// TODO:download agreement from adobe
		// TODO:upload agreement to azure
		// TODO:send email to creator/HR/?
	}

}
