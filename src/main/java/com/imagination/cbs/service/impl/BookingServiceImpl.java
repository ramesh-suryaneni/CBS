/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.ContractorMonthlyWorkDay;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.ApproverTeamDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.dto.WorkDaysDto;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.repository.BookingWorkTaskRepository;
import com.imagination.cbs.repository.ContractorMonthlyWorkDayRepository;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.service.MaconomyService;
import com.imagination.cbs.service.RoleService;

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
	private LoggedInUserService loggedInUserService;

	@Transactional
	@Override
	public BookingDto addBookingDetails(BookingDto booking) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		booking.setChangedBy(loggedInUser);
		BookingRevision bookingRevision = new BookingRevision();
		bookingRevision.setChangedBy(loggedInUser);

		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(booking);

		ContractorRoleDto cestResponse = roleService.getCESToutcome(Long.parseLong(booking.getRoleId()));

		String jobNo = booking.getJobNumber();
		bookingRevision.setJobNumber(jobNo);
		if (jobNo != null) {
			try {
				JobDataDto jobDetails = maconomyService.getJobDetails(booking.getJobNumber());
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
		bookingRevision.setContractedFromDate(BookingMapper.stringToTimeStampConverter(booking.getStartDate()));
		bookingRevision.setContractedToDate(BookingMapper.stringToTimeStampConverter(booking.getEndDate()));
		bookingRevision.setChangedBy(booking.getChangedBy());
		bookingRevision.setRevisionNumber(1L);
		bookingDomain.addBookingRevision(bookingRevision);
		bookingDomain.setStatusId(1001L);
		bookingRevision.setContractorEmployeeRoleId(Long.parseLong(booking.getRoleId()));
		bookingRevision.setInsideIr35(Boolean.valueOf(cestResponse.isInsideIr35()).toString());
		// bookingDomain.setTeam(team);

		Booking savedBooking = bookingRepository.save(bookingDomain);

		BookingDto bookingDto = bookingMapper
				.toBookingDtoFromBookingRevision(savedBooking.getBookingRevisions().get(0));
		bookingDto.setBookingId(savedBooking.getBookingId().toString());
		// bookingDto.setTeamId(String.valueOf(savedBooking.getTeam().getTeamId()));
		bookingDto.setTeamId(String.valueOf(savedBooking.getTeamId()));
		bookingDto.setApprovalStatusId(savedBooking.getStatusId().toString());
		bookingDto.setChangedBy(savedBooking.getChangedBy());
		bookingDto.setChangedDate(savedBooking.getChangedDate().toString());
		bookingDto.setBookingDescription(savedBooking.getBookingDescription());
		return bookingDto;
	}

	@Transactional
	@Override
	public BookingDto updateBookingDetails(Long bookingId, BookingDto booking) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(booking);
		bookingRevision.setChangedBy(loggedInUser);
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		bookingRevision.setRevisionNumber(++revisionNumber);
		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		bookingRevision.setContractorId(Long.parseLong(booking.getContractorId()));
		bookingRevision
				.setContractedFromDate(BookingMapper.stringToTimeStampConverter(booking.getContractedFromDate()));
		bookingRevision.setContractedToDate(BookingMapper.stringToTimeStampConverter(booking.getContractedToDate()));
		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);

		List<BookingWorkTask> bookingWorkTasks = booking.getWorkTasks().stream().map(work -> {
			BookingWorkTask bookingWorkTask = new BookingWorkTask();
			bookingWorkTask.setTaskDeliveryDate(Date.valueOf(work.getTaskDeliveryDate()));
			bookingWorkTask.setTaskName(work.getTaskName());
			bookingWorkTask.setTaskDateRate(Double.parseDouble(work.getTaskDateRate()));
			bookingWorkTask.setTaskTotalDays(Long.parseLong(work.getTaskTotalDays()));
			bookingWorkTask.setTaskTotalAmount(Double.parseDouble(work.getTaskTotalAmount()));
			bookingWorkTask.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			bookingWorkTask.setChangedBy(savedBookingRevision.getChangedBy());
			return bookingWorkTask;
		}).collect(Collectors.toList());

		List<BookingWorkTask> savedBookingWorkTasks = bookingWorkTaskRepository.saveAll(bookingWorkTasks);

		List<WorkTasksDto> workTasks = savedBookingWorkTasks.stream().map(work -> {
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
		}).collect(Collectors.toList());

		List<ContractorMonthlyWorkDay> monthlyWorkDays = booking.getWorkDays().stream().map(work -> {
			ContractorMonthlyWorkDay monthlyWorkday = new ContractorMonthlyWorkDay();
			monthlyWorkday.setMonthName(work.getMonthName());
			monthlyWorkday.setMonthWorkingDays(Long.parseLong(work.getMonthWorkingDays()));
			monthlyWorkday.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			monthlyWorkday.setChangedBy(savedBookingRevision.getChangedBy());
			return monthlyWorkday;
		}).collect(Collectors.toList());

		List<ContractorMonthlyWorkDay> savedMonthlyWorkDays = contractorMonthlyWorkDayRepository
				.saveAll(monthlyWorkDays);

		List<WorkDaysDto> monthlyWorkdays = savedMonthlyWorkDays.stream().map(work -> {
			WorkDaysDto workDaysDto = new WorkDaysDto();
			workDaysDto.setMonthName(work.getMonthName());
			workDaysDto.setMonthWorkingDays(work.getMonthWorkingDays().toString());
			workDaysDto.setChangedBy(work.getChangedBy());
			workDaysDto.setChangedDate(work.getChangedDatetime().toString());
			workDaysDto.setBookingRevisionId(work.getBookingRevisionId().toString());
			return workDaysDto;
		}).collect(Collectors.toList());

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
	public BookingDto processBookingDetails(Long bookingId, BookingDto booking) {
		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(booking);

		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		bookingRevision.setContractorId(Long.parseLong(booking.getContractorId()));
		bookingRevision
				.setContractedFromDate(BookingMapper.stringToTimeStampConverter(booking.getContractedFromDate()));
		bookingRevision.setContractedToDate(BookingMapper.stringToTimeStampConverter(booking.getContractedToDate()));
		bookingRevision.setChangedBy(bookingDetails.getChangedBy());
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		bookingRevision.setRevisionNumber(++revisionNumber);

		bookingRepository.updateStatusOfBooking(bookingId, 1002L);

		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);

		List<BookingWorkTask> bookingWorkTasks = booking.getWorkTasks().stream().map(work -> {
			BookingWorkTask bookingWorkTask = new BookingWorkTask();
			bookingWorkTask.setTaskDeliveryDate(Date.valueOf(work.getTaskDeliveryDate()));
			bookingWorkTask.setTaskName(work.getTaskName());
			bookingWorkTask.setTaskDateRate(Double.parseDouble(work.getTaskDateRate()));
			bookingWorkTask.setTaskTotalDays(Long.parseLong(work.getTaskTotalDays()));
			bookingWorkTask.setTaskTotalAmount(Double.parseDouble(work.getTaskTotalAmount()));
			bookingWorkTask.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			bookingWorkTask.setChangedBy(savedBookingRevision.getChangedBy());
			return bookingWorkTask;
		}).collect(Collectors.toList());

		List<BookingWorkTask> savedBookingWorkTasks = bookingWorkTaskRepository.saveAll(bookingWorkTasks);

		List<WorkTasksDto> workTasks = savedBookingWorkTasks.stream().map(work -> {
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
		}).collect(Collectors.toList());

		List<ContractorMonthlyWorkDay> monthlyWorkDays = booking.getWorkDays().stream().map(work -> {
			ContractorMonthlyWorkDay monthlyWorkday = new ContractorMonthlyWorkDay();
			monthlyWorkday.setMonthName(work.getMonthName());
			monthlyWorkday.setMonthWorkingDays(Long.parseLong(work.getMonthWorkingDays()));
			monthlyWorkday.setBookingRevisionId(savedBookingRevision.getBookingRevisionId());
			monthlyWorkday.setChangedBy(savedBookingRevision.getChangedBy());
			return monthlyWorkday;
		}).collect(Collectors.toList());

		List<ContractorMonthlyWorkDay> savedMonthlyWorkDays = contractorMonthlyWorkDayRepository
				.saveAll(monthlyWorkDays);

		List<WorkDaysDto> monthlyWorkdays = savedMonthlyWorkDays.stream().map(work -> {
			WorkDaysDto workDaysDto = new WorkDaysDto();
			workDaysDto.setMonthName(work.getMonthName());
			workDaysDto.setMonthWorkingDays(work.getMonthWorkingDays().toString());
			workDaysDto.setChangedBy(work.getChangedBy());
			workDaysDto.setChangedDate(work.getChangedDatetime().toString());
			workDaysDto.setBookingRevisionId(work.getBookingRevisionId().toString());
			return workDaysDto;
		}).collect(Collectors.toList());

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
}
