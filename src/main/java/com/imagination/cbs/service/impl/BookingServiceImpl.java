/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.ContractorEmployeeRole;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.service.BookingService;

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

	@Override
	public Booking storeBookingDetails(BookingDto booking) {
		Booking bookingDomain = bookingMapper.toBookingDomainFromBookingDto(booking);
		RoleDm role = new RoleDm();
		ContractorEmployeeRole contractorEmployeeRole = new ContractorEmployeeRole();
		BookingRevision bookingRevision = new BookingRevision();
		ApprovalStatusDm approvalStatusDm = new ApprovalStatusDm();
		Team team = new Team();

		team.setTeamId(1001L);// Need to check how to find
		// role.setRoleId(booking.getRoleId());
		approvalStatusDm.setApprovalStatusId(1000L);// Need to check how to find
		// contractorEmployeeRole.setRoleDm(role);

		contractorEmployeeRole.setContractorEmployeeRoleId(booking.getRoleId());
		bookingRevision.setApprovalStatusDm(approvalStatusDm);
		bookingRevision.setContractorEmployeeRole(contractorEmployeeRole);
		bookingRevision.setContractedFromDate(BookingMapper.stringToTimeStampConverter(booking.getStartDate()));
		bookingRevision.setContractedToDate(BookingMapper.stringToTimeStampConverter(booking.getEndDate()));
		bookingRevision.setJobNumber(1111L);// Need to check how to find
		bookingRevision.setChangedBy(booking.getChangedBy());
		bookingRevision.setChangedDate(BookingMapper.stringToTimeStampConverter(booking.getChangedDate()));
		BigDecimal bg = new BigDecimal(13.0);
		bookingRevision.setRate(bg);
		bookingRevision.setRevisionNumber(1L);

		bookingDomain.addBookingRevision(bookingRevision);

		bookingDomain.setApprovalStatusDm(approvalStatusDm);
		bookingDomain.setTeam(team);

		return bookingRepository.save(bookingDomain);
	}
}
