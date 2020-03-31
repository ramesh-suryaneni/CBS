/**
 * 
 */
package com.imagination.cbs.service.impl;

import static java.util.stream.Collectors.toList;

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

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.domain.ContractorEmployee;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.OfficeDm;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.SupplierTypeDm;
import com.imagination.cbs.domain.SupplierWorkLocationTypeDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.mapper.ApprovalStatusDmMapper;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.mapper.ContractorEmployeeMapper;
import com.imagination.cbs.mapper.ContractorMapper;
import com.imagination.cbs.mapper.CurrencyMapper;
import com.imagination.cbs.mapper.DisciplineMapper;
import com.imagination.cbs.mapper.OfficeMapper;
import com.imagination.cbs.mapper.RecruitingMapper;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.mapper.SupplierTypeMapper;
import com.imagination.cbs.mapper.SupplierWorkLocationTypeMapper;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.repository.BookingWorkTaskRepository;
import com.imagination.cbs.repository.ContractorEmployeeRepository;
import com.imagination.cbs.repository.ContractorMonthlyWorkDayRepository;
import com.imagination.cbs.repository.ContractorRepository;
import com.imagination.cbs.repository.CurrencyRepository;
import com.imagination.cbs.repository.DisciplineRepository;
import com.imagination.cbs.repository.OfficeRepository;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.repository.SupplierTypeRepository;
import com.imagination.cbs.repository.SupplierWorkLocationTypeRepository;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.service.RoleService;
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
	private RoleService roleService;

	@Autowired
	private BookingRevisionRepository bookingRevisionRepository;

	@Autowired
	private BookingWorkTaskRepository bookingWorkTaskRepository;

	@Autowired
	private MaconomyService maconomyService;

	@Autowired
	private ContractorMonthlyWorkDayRepository contractorMonthlyWorkDayRepository;

	@Autowired
	private MaconomyApproverTeamServiceImpl maconomyApproverTeamService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DisciplineRepository disciplineRepository;

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
	private ApprovalStatusDmMapper approvalStatusDmMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private DisciplineMapper disciplineMapper;

	@Autowired
	private ContractorMapper contractorMapper;

	@Autowired
	private SupplierWorkLocationTypeMapper supplierWorkLocationTypeMapper;

	@Autowired
	private SupplierTypeMapper supplierTypeMapper;

	@Autowired
	private RecruitingMapper recruitingMapper;

	@Autowired
	private OfficeMapper officeMapper;

	@Autowired
	private CurrencyMapper currencyMapper;

	@Autowired
	private ContractorEmployeeMapper contractorEmployeeMapper;

	@Transactional
	@Override
	public BookingDto addBookingDetails(BookingRequest bookingRequest) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy(loggedInUser);

		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(bookingRequest);
		bookingDomain.setChangedBy(loggedInUser);

		Long roleId = Long.parseLong(bookingRequest.getContractorEmployeeRoleId());
		ContractorRoleDto cestResponse = roleService.getCESToutcome(roleId);

		if (bookingRequest.getJobNumber() != null) {
			try {
				JobDataDto jobDetails = maconomyService.getJobDetails(bookingRequest.getJobNumber());
				String deptName = jobDetails.getData().getText3();
				bookingRevision.setJobDeptName(deptName);
				ApproverTeamDto approverTeamDetails = maconomyApproverTeamService.getApproverTeamDetails(deptName);

				String remark3 = approverTeamDetails.getData().getRemark3();
				Team teamOne = teamRepository.findByTeamName(remark3);
				bookingDomain.setTeamId(teamOne.getTeamId());
				bookingRevision.setTeamId(teamOne.getTeamId());

			} catch (Exception e) {
				bookingDomain.setTeamId(null);
			}
		}
		bookingRevision.setContractedFromDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(loggedInUser);
		bookingRevision.setRevisionNumber(1L);
		bookingDomain.addBookingRevision(bookingRevision);
		bookingDomain.setStatusId(1001L);
		bookingRevision.setContractorEmployeeRoleId(roleId);
		bookingRevision.setInsideIr35(Boolean.valueOf(cestResponse.isInsideIr35()).toString());

		Booking savedBooking = bookingRepository.save(bookingDomain);

		BookingDto bookingDto = bookingMapper
				.toBookingDtoFromBookingRevision(savedBooking.getBookingRevisions().get(0));
		bookingDto.setBookingId(savedBooking.getBookingId().toString());
		Team team = new Team();
		team.setTeamId(savedBooking.getTeamId());
		bookingDto.setTeam(teamMapper.toTeamDtoFromTeamDomain(team));
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalStatusId(savedBooking.getStatusId());
		bookingDto.setApprovalStatusDm(
				approvalStatusDmMapper.toApprovalStatusDmDtoFromApprovalStatusDmDomain(approvalStatusDm));
		bookingDto.setChangedBy(savedBooking.getChangedBy());
		bookingDto.setChangedDate(savedBooking.getChangedDate().toString());
		bookingDto.setBookingDescription(savedBooking.getBookingDescription());
		RoleDm roleDm = roleRepository.findById(roleId).get();
		Discipline discipline = new Discipline();
		discipline.setDisciplineId(roleDm.getDiscipline().getDisciplineId());
		bookingDto.setDiscipline(disciplineMapper.toDisciplineDtoFromDisciplineDomain(discipline));
		return bookingDto;
	}

	@Transactional
	@Override
	public BookingDto updateBookingDetails(Long bookingId, BookingRequest bookingRequest) {

		String loggedInUser =	 loggedInUserService.getLoggedInUserDetails().getDisplayName();
		Booking bookingDetails = bookingRepository.findById(bookingId).get();

		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(bookingRequest);
		bookingRevision.setChangedBy(loggedInUser);
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();

		if (bookingRequest.getJobNumber() != null) {
			try {
				JobDataDto jobDetails = maconomyService.getJobDetails(bookingRequest.getJobNumber());
				String deptName = jobDetails.getData().getText3();
				bookingRevision.setJobDeptName(deptName);
				ApproverTeamDto approverTeamDetails = maconomyApproverTeamService.getApproverTeamDetails(deptName);

				String remark3 = approverTeamDetails.getData().getRemark3();
				Team teamOne = teamRepository.findByTeamName(remark3);
				bookingRevision.setTeamId(teamOne.getTeamId());
			} catch (Exception e) {
				bookingRevision.setTeamId(null);
			}
		}
		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		bookingRevision.setRevisionNumber(++revisionNumber);
		bookingRevision.setContractorId(Long.parseLong(bookingRequest.getContractorId()));
		bookingRevision.setContractedFromDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);
		List<WorkTasksDto> workTasks = null;
		if (bookingRequest.getWorkTasks() != null) {
			List<BookingWorkTask> bookingWorkTasks = toWorkTaskDomainFromWorkTasksDto(bookingRequest,
					savedBookingRevision);
			List<BookingWorkTask> savedBookingWorkTasks = bookingWorkTaskRepository.saveAll(bookingWorkTasks);
			workTasks = toWorkTasksDtoFromWorkTaskDomain(savedBookingWorkTasks);
		}
		List<WorkDaysDto> monthlyWorkdays = null;
		if (bookingRequest.getWorkDays() != null) {
			List<ContractorMonthlyWorkDay> monthlyWorkDays = toMonthlyWorkDaysDomainFromWorkDaysDto(bookingRequest,
					savedBookingRevision);

			List<ContractorMonthlyWorkDay> savedMonthlyWorkDays = contractorMonthlyWorkDayRepository
					.saveAll(monthlyWorkDays);

			monthlyWorkdays = toWorkDaysDtoFromMonthlyWorkDaysDomain(savedMonthlyWorkDays);

		}
		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(savedBookingRevision);

		bookingDto.setWorkTasks(workTasks);
		bookingDto.setWorkDays(monthlyWorkdays);
		bookingDto.setBookingId(bookingDetails.getBookingId().toString());
		Team team = new Team();
		team.setTeamId(bookingDetails.getTeamId());
		bookingDto.setTeam(teamMapper.toTeamDtoFromTeamDomain(team));
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		approvalStatusDm.setApprovalStatusId(bookingDetails.getStatusId());
		bookingDto.setApprovalStatusDm(
				approvalStatusDmMapper.toApprovalStatusDmDtoFromApprovalStatusDmDomain(approvalStatusDm));
		bookingDto.setChangedBy(bookingDetails.getChangedBy());
		bookingDto.setChangedDate(bookingDetails.getChangedDate().toString());
		bookingDto.setBookingDescription(bookingDetails.getBookingDescription());
		return bookingDto;
	}

	@Transactional
	@Override
	public BookingDto processBookingDetails(Long bookingId, BookingRequest bookingRequest) {
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(bookingRequest);

		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		bookingRevision.setContractorId(Long.parseLong(bookingRequest.getContractorId()));
		bookingRevision.setContractedFromDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedFromDate()));
		bookingRevision.setContractedToDate(DateUtils.convertDateToTimeStamp(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(bookingDetails.getChangedBy());
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		bookingRevision.setRevisionNumber(++revisionNumber);

		bookingRepository.updateStatusOfBooking(bookingId, 1002L);

		if (bookingRequest.getJobNumber() != null) {
			JobDataDto jobDetails = maconomyService.getJobDetails(bookingRequest.getJobNumber());
			String deptName = jobDetails.getData().getText3();
			bookingRevision.setJobDeptName(deptName);
			ApproverTeamDto approverTeamDetails = maconomyApproverTeamService.getApproverTeamDetails(deptName);

			String remark3 = approverTeamDetails.getData().getRemark3();
			Team teamOne = teamRepository.findByTeamName(remark3);
			bookingRevision.setTeamId(teamOne.getTeamId());
		}

		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);
		if (bookingRequest.getWorkTasks() != null) {
			List<BookingWorkTask> bookingWorkTasks = toWorkTaskDomainFromWorkTasksDto(bookingRequest,
					savedBookingRevision);

			bookingWorkTaskRepository.saveAll(bookingWorkTasks);
		}
		if (bookingRequest.getWorkDays() != null) {
			List<ContractorMonthlyWorkDay> monthlyWorkDays = toMonthlyWorkDaysDomainFromWorkDaysDto(bookingRequest,
					savedBookingRevision);

			contractorMonthlyWorkDayRepository.saveAll(monthlyWorkDays);
		}

		return retrieveBookingDetails(bookingId);
	}

	private List<ContractorMonthlyWorkDay> toMonthlyWorkDaysDomainFromWorkDaysDto(BookingRequest bookingRequest,
			BookingRevision savedBookingRevision) {
		List<ContractorMonthlyWorkDay> monthlyWorkDays = bookingRequest.getWorkDays().stream().map(work -> {
			ContractorMonthlyWorkDay monthlyWorkday = new ContractorMonthlyWorkDay();
			monthlyWorkday.setMonthName(work.getMonthName());
			monthlyWorkday.setMonthWorkingDays(Long.parseLong(work.getMonthWorkingDays()));
			monthlyWorkday.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			monthlyWorkday.setChangedBy(savedBookingRevision.getChangedBy());
			return monthlyWorkday;
		}).collect(toList());
		return monthlyWorkDays;
	}

	private List<BookingWorkTask> toWorkTaskDomainFromWorkTasksDto(BookingRequest bookingRequest,
			BookingRevision savedBookingRevision) {
		List<BookingWorkTask> bookingWorkTasks = bookingRequest.getWorkTasks().stream().map(work -> {
			BookingWorkTask bookingWorkTask = new BookingWorkTask();
			bookingWorkTask.setTaskDeliveryDate(Date.valueOf(work.getTaskDeliveryDate()));
			bookingWorkTask.setTaskName(work.getTaskName());
			bookingWorkTask.setTaskDateRate(Double.parseDouble(work.getTaskDateRate()));
			bookingWorkTask.setTaskTotalDays(Long.parseLong(work.getTaskTotalDays()));
			bookingWorkTask.setTaskTotalAmount(Double.parseDouble(work.getTaskTotalAmount()));
			bookingWorkTask.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			bookingWorkTask.setChangedBy(savedBookingRevision.getChangedBy());
			return bookingWorkTask;
		}).collect(toList());
		return bookingWorkTasks;
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

	@Override
	public BookingDto retrieveBookingDetails(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId).get();

		BookingRevision bookingRevision = bookingRevisionRepository
				.fetchBookingRevisionByBookingId(booking.getBookingId());

		Team team = teamRepository.findById(booking.getTeamId()).get();

		ApprovalStatusDm approvalStatusDm = approvalStatusDmRepository.findById(booking.getStatusId()).get();

		List<BookingWorkTask> bookingWorkTasks = bookingWorkTaskRepository
				.findByBookingRevisionId(bookingRevision.getBookingRevisionId());

		List<ContractorMonthlyWorkDay> contractorMonthlyWorkDays = contractorMonthlyWorkDayRepository
				.findByBookingRevisionId(bookingRevision.getBookingRevisionId());

		RoleDm roleDm = roleRepository.findById(bookingRevision.getContractorEmployeeRoleId()).get();

		Discipline discipline = disciplineRepository.findById(roleDm.getDiscipline().getDisciplineId()).get();

		Contractor contractor = contractorRepository.findById(bookingRevision.getContractorId()).get();

		SupplierWorkLocationTypeDm supplierWorkLocationTypeDm = supplierWorkLocationTypeRepository
				.findById(bookingRevision.getSupplierWorkLocationType()).get();
		SupplierTypeDm supplierTypeDm = supplierTypeRepository.findById(bookingRevision.getSupplierTypeId()).get();

		ReasonsForRecruiting reasonsForRecruiting = recruitingRepository
				.findById(bookingRevision.getReasonForRecruiting()).get();

		OfficeDm officeDm = officeRepository.findById(bookingRevision.getCommisioningOffice()).get();

		CurrencyDm currencyDm = currencyRepository.findById(bookingRevision.getCurrencyId()).get();

		ContractorEmployee contractorEmployee = contractorEmployeeRepository
				.findById(bookingRevision.getContractEmployeeId()).get();

		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(bookingRevision);
		bookingDto.setBookingId(booking.getBookingId().toString());
		bookingDto.setBookingDescription(booking.getBookingDescription());
		bookingDto.setChangedBy(booking.getChangedBy());
		bookingDto.setChangedDate(booking.getChangedDate().toString());

		bookingDto.setTeam(teamMapper.toTeamDtoFromTeamDomain(team));
		bookingDto.setApprovalStatusDm(
				approvalStatusDmMapper.toApprovalStatusDmDtoFromApprovalStatusDmDomain(approvalStatusDm));
		bookingDto.setWorkDays(toWorkDaysDtoFromMonthlyWorkDaysDomain(contractorMonthlyWorkDays));
		bookingDto.setWorkTasks(toWorkTasksDtoFromWorkTaskDomain(bookingWorkTasks));
		bookingDto.setContractorRole(roleMapper.toRoleDTO(roleDm));
		bookingDto.setDiscipline(disciplineMapper.toDisciplineDtoFromDisciplineDomain(discipline));
		bookingDto.setContractor(contractorMapper.toContractorDtoFromContractorDomain(contractor));
		bookingDto.setSupplierWorkLocation(supplierWorkLocationTypeMapper
				.toSupplierWorkLocationTypeDtoFromSupplierWorkLocationTypeDomain(supplierWorkLocationTypeDm));
		bookingDto.setSupplierType(supplierTypeMapper.toSupplierTypeDtoFromSupplierTypeDomain(supplierTypeDm));
		bookingDto.setRecruitingReason(
				recruitingMapper.toRecruitingDtoFromReasonsForRecruitingDomain(reasonsForRecruiting));
		bookingDto.setOffice(officeMapper.toOfficeDtoFromOfficeDomain(officeDm));
		bookingDto.setCurrency(currencyMapper.toCurrencyDtoFromCurrencyDm(currencyDm));
		bookingDto.setContractorEmployee(
				contractorEmployeeMapper.toContractorEmployeeDtoFromContractorEmployee(contractorEmployee));
		bookingDto.setContractWorkLocation(officeDm.getOfficeId().toString());
		return bookingDto;
	}

	private List<WorkTasksDto> toWorkTasksDtoFromWorkTaskDomain(List<BookingWorkTask> bookingWorkTasks) {
		List<WorkTasksDto> workTasks = bookingWorkTasks.stream().map(work -> {
			WorkTasksDto workTasksDto = new WorkTasksDto();
			workTasksDto.setTaskName(work.getTaskName());
			workTasksDto.setTaskDeliveryDate(work.getTaskDeliveryDate().toString());
			workTasksDto.setTaskDateRate(work.getTaskDateRate().toString());
			workTasksDto.setTaskTotalDays(work.getTaskTotalDays().toString());
			workTasksDto.setTaskTotalAmount(work.getTaskTotalAmount().toString());
			workTasksDto.setChangedBy(work.getChangedBy());
			workTasksDto.setChangedDate(work.getChangedDate().toString());
			workTasksDto.setBookingRevisionId(work.getBookingRevisionId().toString());
			return workTasksDto;
		}).collect(toList());
		return workTasks;
	}

	private List<WorkDaysDto> toWorkDaysDtoFromMonthlyWorkDaysDomain(
			List<ContractorMonthlyWorkDay> contractorMonthlyWorkDays) {
		List<WorkDaysDto> monthlyWorkDays = contractorMonthlyWorkDays.stream().map(work -> {
			WorkDaysDto workDaysDto = new WorkDaysDto();
			workDaysDto.setMonthName(work.getMonthName());
			workDaysDto.setMonthWorkingDays(work.getMonthWorkingDays().toString());
			workDaysDto.setChangedBy(work.getChangedBy());
			workDaysDto.setChangedDate(work.getChangedDatetime().toString());
			workDaysDto.setBookingRevisionId(work.getBookingRevisionId().toString());
			return workDaysDto;
		}).collect(toList());
		return monthlyWorkDays;
	}
}
