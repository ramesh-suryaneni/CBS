package com.imagination.cbs.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imagination.cbs.domain.BookingRevision;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingRevisionRepository extends JpaRepository<BookingRevision, Long> {
	
	public static final String BOOKING_REVISIONQU_DETAILS_QUERY = "SELECT asd.approval_name as status, br.job_name as jobName, rd.role_name as role, br.contractor_name as contractor,"
			+ " br.contracted_from_date as startDate, br.contracted_to_date as endDate, br.changed_by as changedBy, br.changed_date as changedDate"
			+ " FROM cbs.approval_status_dm asd, cbs.contractor_employee_role cer , cbs.role_dm rd,"
			+ " (Select booking_id, Max(revision_number) as maxRevision from cbs.booking_revision WHERE changed_by = :loggedInUser";

	@Query(value = BOOKING_REVISIONQU_DETAILS_QUERY
			+ " and approval_status_id = (SELECT approval_status_id from cbs.approval_status_dm where approval_name = :status)"
			+ " group by booking_id) as irn INNER JOIN cbs.booking_revision br on br.booking_id=irn.booking_id and br.revision_number=irn.maxRevision"
			+ " WHERE br.contractor_employee_role_id = cer.contractor_employee_role_id and cer.role_id = rd.role_id and br.approval_status_id = asd.approval_status_id", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForDraftOrCancelled(@Param("loggedInUser") String loggedInUser,
			@Param("status") String status, Pageable pageable);

	@Query(value = "SELECT * FROM cbs.booking_revision br where br.booking_id=:bookingId AND br.revision_number="
			+ "(SELECT MAX(revision_number) FROM cbs.booking_revision br where br.booking_Id=:bookingId)", nativeQuery = true)
	public BookingRevision fetchBookingRevisionByBookingId(@Param("bookingId") Long bookingId);
	
	@Query(value = BOOKING_REVISIONQU_DETAILS_QUERY
			+ " and approval_status_id in (SELECT approval_status_id from cbs.approval_status_dm where approval_name != 'Draft' AND approval_name != 'Cancelled')"
			+ " group by booking_id) as irn INNER JOIN cbs.booking_revision br on br.booking_id=irn.booking_id and br.revision_number=irn.maxRevision"
			+ " WHERE br.contractor_employee_role_id = cer.contractor_employee_role_id and cer.role_id = rd.role_id and br.approval_status_id = asd.approval_status_id", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForSubmitted(@Param("loggedInUser") String loggedInUser, Pageable pageable);
	
}
