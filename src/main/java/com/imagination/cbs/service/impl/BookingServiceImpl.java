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
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
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
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.repository.BookingWorkTaskRepository;
import com.imagination.cbs.repository.ContractorMonthlyWorkDayRepository;
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
	private LoggedInUserService loggedInUserService;

	@Autowired
	private TeamMapper teamMapper;

	@Autowired
	private ApprovalStatusDmMapper approvalStatusDmMapper;

	@Transactional
	@Override
	public BookingDto addBookingDetails(BookingRequest bookingRequest) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy(loggedInUser);

		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(bookingRequest);
		bookingDomain.setChangedBy(loggedInUser);

		ContractorRoleDto cestResponse = roleService.getCESToutcome(Long.parseLong(bookingRequest.getRoleId()));

		String jobNo = bookingRequest.getJobNumber();
		bookingRevision.setJobNumber(jobNo);
		if (jobNo != null) {
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
		bookingRevision.setContractedFromDate(DateUtils.convertDateToTimeStamp(bookingRequest.getStartDate()));
		bookingRevision.setContractedToDate(DateUtils.convertDateToTimeStamp(bookingRequest.getEndDate()));
		bookingRevision.setChangedBy(loggedInUser);
		bookingRevision.setRevisionNumber(1L);
		bookingDomain.addBookingRevision(bookingRevision);
		bookingDomain.setStatusId(1001L);
		bookingRevision.setContractorEmployeeRoleId(Long.parseLong(bookingRequest.getRoleId()));
		bookingRevision.setInsideIr35(Boolean.valueOf(cestResponse.isInsideIr35()).toString());

		Booking savedBooking = bookingRepository.save(bookingDomain);

		BookingDto bookingDto = bookingMapper
				.toBookingDtoFromBookingRevision(savedBooking.getBookingRevisions().get(0));
		bookingDto.setBookingId(savedBooking.getBookingId().toString());
		bookingDto.setTeamId(String.valueOf(savedBooking.getTeamId()));
		bookingDto.setApprovalStatusId(savedBooking.getStatusId().toString());
		bookingDto.setChangedBy(savedBooking.getChangedBy());
		bookingDto.setChangedDate(savedBooking.getChangedDate().toString());
		bookingDto.setBookingDescription(savedBooking.getBookingDescription());
		return bookingDto;
	}

	@Transactional
	@Override
	public BookingDto updateBookingDetails(Long bookingId, BookingRequest bookingRequest) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();

		Booking bookingDetails = bookingRepository.findById(bookingId).get();

		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(bookingRequest);
		bookingRevision.setChangedBy(loggedInUser);
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();

		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		bookingRevision.setRevisionNumber(++revisionNumber);
		bookingRevision.setContractorId(Long.parseLong(bookingRequest.getContractorId()));
		bookingRevision.setContractedFromDate(
				BookingMapper.stringToTimeStampConverter(bookingRequest.getContractedFromDate()));
		bookingRevision
				.setContractedToDate(BookingMapper.stringToTimeStampConverter(bookingRequest.getContractedToDate()));
		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);

		List<BookingWorkTask> bookingWorkTasks = toWorkTaskDomainFromWorkTasksDto(bookingRequest, savedBookingRevision);

		List<BookingWorkTask> savedBookingWorkTasks = bookingWorkTaskRepository.saveAll(bookingWorkTasks);

		List<WorkTasksDto> workTasks = toWorkTasksDtoFromWorkTaskDomain(savedBookingWorkTasks);

		List<ContractorMonthlyWorkDay> monthlyWorkDays = toMonthlyWorkDaysDomainFromWorkDaysDto(bookingRequest,
				savedBookingRevision);

		List<ContractorMonthlyWorkDay> savedMonthlyWorkDays = contractorMonthlyWorkDayRepository
				.saveAll(monthlyWorkDays);

		List<WorkDaysDto> monthlyWorkdays = toWorkDaysDtoFromMonthlyWorkDaysDomain(savedMonthlyWorkDays);

		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(savedBookingRevision);

		bookingDto.setWorkTasks(workTasks);
		bookingDto.setWorkDays(monthlyWorkdays);
		bookingDto.setBookingId(bookingDetails.getBookingId().toString());
		bookingDto.setTeamId(String.valueOf(bookingDetails.getTeamId()));
		bookingDto.setApprovalStatusId(bookingDetails.getStatusId().toString());
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
		bookingRevision.setContractedFromDate(
				BookingMapper.stringToTimeStampConverter(bookingRequest.getContractedFromDate()));
		bookingRevision
				.setContractedToDate(BookingMapper.stringToTimeStampConverter(bookingRequest.getContractedToDate()));
		bookingRevision.setChangedBy(bookingDetails.getChangedBy());
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		bookingRevision.setRevisionNumber(++revisionNumber);

		bookingRepository.updateStatusOfBooking(bookingId, 1002L);

		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);

		List<BookingWorkTask> bookingWorkTasks = toWorkTaskDomainFromWorkTasksDto(bookingRequest, savedBookingRevision);

		List<BookingWorkTask> savedBookingWorkTasks = bookingWorkTaskRepository.saveAll(bookingWorkTasks);

		List<WorkTasksDto> workTasks = toWorkTasksDtoFromWorkTaskDomain(savedBookingWorkTasks);

		List<ContractorMonthlyWorkDay> monthlyWorkDays = toMonthlyWorkDaysDomainFromWorkDaysDto(bookingRequest,
				savedBookingRevision);

		List<ContractorMonthlyWorkDay> savedMonthlyWorkDays = contractorMonthlyWorkDayRepository
				.saveAll(monthlyWorkDays);

		List<WorkDaysDto> monthlyWorkdays = toWorkDaysDtoFromMonthlyWorkDaysDomain(savedMonthlyWorkDays);

		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(savedBookingRevision);

		bookingDto.setWorkTasks(workTasks);
		bookingDto.setWorkDays(monthlyWorkdays);
		bookingDto.setBookingId(bookingDetails.getBookingId().toString());
		bookingDto.setTeamId(String.valueOf(bookingDetails.getTeamId()));
		bookingDto.setApprovalStatusId(bookingDetails.getStatusId().toString());
		bookingDto.setChangedBy(bookingDetails.getChangedBy());
		bookingDto.setChangedDate(bookingDetails.getChangedDate().toString());
		bookingDto.setBookingDescription(bookingDetails.getBookingDescription());
		return bookingDto;
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
		List<Tuple> bookingRevisions = bookingRevisionRepository
				.retrieveBookingRevisionForDraftOrCancelled(loggedInUser, status, pageable);
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

		List<WorkTasksDto> workTasks = toWorkTasksDtoFromWorkTaskDomain(bookingWorkTasks);

		List<WorkDaysDto> monthlyWorkDays = toWorkDaysDtoFromMonthlyWorkDaysDomain(contractorMonthlyWorkDays);

		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(bookingRevision);
		bookingDto.setBookingId(booking.getBookingId().toString());
		bookingDto.setBookingDescription(booking.getBookingDescription());
		bookingDto.setTeamId(booking.getTeamId().toString());
		bookingDto.setApprovalStatusId(booking.getStatusId().toString());
		bookingDto.setChangedBy(booking.getChangedBy());
		bookingDto.setChangedDate(booking.getChangedDate().toString());

		bookingDto.setTeam(teamMapper.toTeamDtoFromTeamDomain(team));
		bookingDto.setApprovalStatusDm(
				approvalStatusDmMapper.toApprovalStatusDmDtoFromApprovalStatusDmDomain(approvalStatusDm));
		bookingDto.setWorkDays(monthlyWorkDays);
		bookingDto.setWorkTasks(workTasks);
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
