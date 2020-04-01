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

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.Region;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.domain.SupplierWorkLocationTypeDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.repository.ContractorEmployeeRepository;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.repository.CurrencyRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.repository.RegionRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.repository.SupplierTypeRepository;
import com.imagination.cbs.repository.SupplierWorkLocationTypeRepository;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.util.DateUtils;

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
	private BookingRevisionRepository bookingRevisionRepository;

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
	private SupplierWorkLocationTypeRepository supplierWorkLocationTypeRepository;

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
	private LoggedInUserService loggedInUserService;

	@Autowired
	private TeamMapper teamMapper;

	@Autowired
	private DisciplineMapper disciplineMapper;

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
		Booking newBookingDomain = populateBooking(bookingRequest, revisionNumber, false);
		newBookingDomain.setBookingId(bookingId);
		bookingRepository.save(newBookingDomain);
		return retrieveBookingDetails(bookingId);
	}

	@Transactional
	@Override
	public BookingDto submitBookingDetails(Long bookingId, BookingRequest bookingRequest) {
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		Booking newBookingDomain = populateBooking(bookingRequest, revisionNumber, true);
		newBookingDomain.setBookingId(bookingId);
		ApprovalStatusDm status = new ApprovalStatusDm();
		status.setApprovalStatusId(1002L);
		newBookingDomain.setApprovalStatus(status);
		bookingRepository.save(newBookingDomain);
		return retrieveBookingDetails(bookingId);

	}

	private Booking populateBooking(BookingRequest bookingRequest, Long revisionNumber, boolean isSubmit) {
		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
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
		status.setApprovalStatusId(1001L);
		bookingDomain.setApprovalStatus(status);
		bookingRevision.setApprovalStatusId(1001L);
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
					throw e;
				}
			}
		}

		bookingRevision.setContractedFromDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(loggedInUser);
		bookingRevision.setRevisionNumber(++revisionNumber);
		// Contractor Details
		if (!StringUtils.isEmpty(bookingRequest.getContractorId())) {
			Contractor contractor = contractorRepository.findById(Long.parseLong(bookingRequest.getContractorId()))
					.get();
			bookingRevision.setContractor(contractor);
			bookingRevision.setContractorName(contractor.getContractorName());
			bookingRevision.setContractorType(contractor.getCompanyType());
			bookingRevision.setContractorContactDetails(contractor.getContactDetails());
		}
		// Employee details
		if (!StringUtils.isEmpty(bookingRequest.getContractEmployeeId())) {
			ContractorEmployee contractorEmployee = contractorEmployeeRepository
					.findById(Long.valueOf(bookingRequest.getContractEmployeeId())).get();
			bookingRevision.setContractEmployee(contractorEmployee);
			bookingRevision.setContractorEmployeeName(contractorEmployee.getEmployeeName());
			bookingRevision.setContractorContactDetails(contractorEmployee.getContactDetails());
			bookingRevision.setKnownAs(contractorEmployee.getKnownAs());
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
		// Contractor Work Location Type
		if (!StringUtils.isEmpty(bookingRequest.getSupplierWorkLocationType())) {
			SupplierWorkLocationTypeDm supplierWorkLocationType = supplierWorkLocationTypeRepository
					.findById(Long.valueOf(bookingRequest.getSupplierWorkLocationType())).get();
			bookingRevision.setSupplierWorkLocationType(supplierWorkLocationType);
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
	public Page<BookingDashBoardDto> getDraftOrCancelledBookings(String status, int pageNo, int pageSize) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();

		Pageable pageable = createPageable(pageNo, pageSize, "br.changed_date", "DESC");
		List<Tuple> bookingRevisions = null;

		if (status.equalsIgnoreCase("Submitted")) {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForSubmitted(loggedInUser, pageable);
		} else {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForDraftOrCancelled(loggedInUser,
					status, pageable);
		}

		List<BookingDashBoardDto> bookingDashBoardDtos = toPagedBookingDashBoardDtoFromTuple(bookingRevisions);

		return new PageImpl<>(bookingDashBoardDtos, pageable, bookingDashBoardDtos.size());
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

	private List<BookingDashBoardDto> toPagedBookingDashBoardDtoFromTuple(List<Tuple> bookingRevisions) {
		List<BookingDashBoardDto> bookingDashboradDtos = new ArrayList<>();
		bookingRevisions.forEach((bookingRevision) -> {
			BookingDashBoardDto bookingDashBoardDto = new BookingDashBoardDto();

			bookingDashBoardDto.setStatus(bookingRevision.get("status", String.class));
			bookingDashBoardDto.setJobname(bookingRevision.get("jobName", String.class));
			bookingDashBoardDto.setRoleName(bookingRevision.get("role", String.class));// contractorEmployeeRole.getRoleDm().getRoleName());
			bookingDashBoardDto.setContractorName(bookingRevision.get("contractor", String.class));
			bookingDashBoardDto.setContractedFromDate(bookingRevision.get("startDate", Timestamp.class));
			bookingDashBoardDto.setContractedToDate(bookingRevision.get("endDate", Timestamp.class));
			bookingDashBoardDto.setChangedBy(bookingRevision.get("changedBy", String.class));
			bookingDashBoardDto.setChangedDate(bookingRevision.get("changedDate", Timestamp.class));

			bookingDashboradDtos.add(bookingDashBoardDto);
		});

		return bookingDashboradDtos;
	}
}
