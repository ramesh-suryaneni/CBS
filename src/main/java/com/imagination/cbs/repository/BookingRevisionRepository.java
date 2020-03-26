package com.imagination.cbs.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imagination.cbs.domain.BookingRevision;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingRevisionRepository extends JpaRepository<BookingRevision, Long> {

	@Query("select br.booking.bookingId, asd.approvalName, br.jobname, br.contractedToDate, "
			+ "br.contractedFromDate, " + " br.contractorName, rd.roleId, rd.roleName, br.changedDate, br.changedBy"
			+ " from BookingRevision br , ApprovalStatusDm asd , RoleDm rd "
			// +" join ApprovalStatusDm asd on
			// br.approvalStatusId=asd.approvalStatusId "
			+ " where br.approvalStatusId=asd.approvalStatusId "

			// +" join RoleDm rd on br.contractorEmployeeRoleId=rd.roleId "
			+ " and br.contractorEmployeeRoleId=rd.roleId "
			+ " and asd.approvalName='Draft'and br.changedBy='Pappu' order by br.changedBy DESC ")

	public List<Tuple> test();

	@Query("SELECT br FROM BookingRevision br WHERE br.booking.bookingId=:bookingId and br.revisionNumber ="
			+ " (SELECT MAX(br.revisionNumber) FROM BookingRevision where br.booking.bookingId=:bookingId)")
	public BookingRevision fetchBookingRevisionByBookingId(Long bookingId);
}
