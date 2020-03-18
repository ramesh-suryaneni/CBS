/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.service.BookingService;
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

	@Override
	public BookingDto addBookingDetails(BookingDto booking) {
		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(booking);
		BookingRevision bookingRevision = new BookingRevision();
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		Team team = new Team();

		ContractorRoleDto cestResponse = roleService.getCESToutcome(Long.parseLong(booking.getRoleId()));

		team.setTeamId(Long.parseLong(booking.getTeamId()));
		approvalStatusDm.setApprovalStatusId(Long.parseLong(booking.getApprovalStatusId()));
		bookingRevision.setContractedFromDate(BookingMapper.stringToTimeStampConverter(booking.getStartDate()));
		bookingRevision.setContractedToDate(BookingMapper.stringToTimeStampConverter(booking.getEndDate()));
		bookingRevision.setJobNumber(booking.getJobNumber());
		bookingRevision.setChangedBy(booking.getChangedBy());
		// bookingRevision.setChangedDate(BookingMapper.stringToTimeStampConverter(booking.getChangedDate()));
		bookingRevision.setRevisionNumber(1L);

		bookingDomain.addBookingRevision(bookingRevision);
		bookingDomain.setApprovalStatusDm(approvalStatusDm);
		bookingRevision.setContractorEmployeeRoleId(Long.parseLong(booking.getRoleId()));
		bookingRevision.setInsideIr35(Boolean.valueOf(cestResponse.isInsideIr35()).toString());
		bookingDomain.setTeam(team);

		Booking savedBooking = bookingRepository.save(bookingDomain);

		BookingDto bookingDto = bookingMapper
				.toBookingDtoFromBookingRevision(savedBooking.getBookingRevisions().get(0));
		bookingDto.setBookingId(savedBooking.getBookingId().toString());
		bookingDto.setTeamId(savedBooking.getTeam().getTeamId().toString());
		bookingDto.setApprovalStatusId(savedBooking.getApprovalStatusDm().getApprovalStatusId().toString());
		bookingDto.setChangedBy(savedBooking.getChangedBy());
		bookingDto.setChangedDate(savedBooking.getChangedDate().toString());
		bookingDto.setBookingDescription(savedBooking.getBookingDescription());
		return bookingDto;
	}

	@Override
	public BookingDto updateBookingDetails(Long bookingId, BookingDto booking) {

		Booking bookingDetails = bookingRepository.findById(bookingId).get();
		BookingRevision bookingRevision = bookingMapper.toBookingRevisionFromBookingDto(booking);
		Long revisionNumber = bookingDetails.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber)).get().getRevisionNumber();
		bookingRevision.setRevisionNumber(++revisionNumber);
		Booking book = new Booking();
		book.setBookingId(bookingId);
		bookingRevision.setBooking(book);
		BookingRevision savedBookingRevision = bookingRevisionRepository.save(bookingRevision);
		BookingDto bookingDto = bookingMapper.toBookingDtoFromBookingRevision(savedBookingRevision);

		bookingDto.setBookingId(bookingDetails.getBookingId().toString());
		bookingDto.setTeamId(bookingDetails.getTeam().getTeamId().toString());
		bookingDto.setApprovalStatusId(bookingDetails.getApprovalStatusDm().getApprovalStatusId().toString());
		bookingDto.setChangedBy(bookingDetails.getChangedBy());
		bookingDto.setChangedDate(bookingDetails.getChangedDate().toString());
		bookingDto.setBookingDescription(bookingDetails.getBookingDescription());
		return bookingDto;
	}

	@Override
	public BookingDto processBookingDetails(Long bookingId, BookingDto booking) {
		return null;
	}
}
