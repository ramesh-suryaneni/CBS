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
	
	public static final String BOOKING_REVISION_DETAILS_QUERY = "SELECT asd.approval_name as status, br.booking_id as bookingId, br.job_name as jobName, rd.role_name as roleName,con.contractor_name as contractorName,"
			+ " br.contracted_from_date as contractedFromDate, br.contracted_to_date as contractedToDate, br.changed_by as changedBy, br.changed_date as changedDate, br.booking_revision_id as bookingRevisionId"
			+ " FROM cbs.approval_status_dm asd, cbs.role_dm rd,"
			+ " (Select booking_id, Max(revision_number) as maxRevision from cbs.booking_revision WHERE changed_by = :loggedInUser";

	/*@Query(value = BOOKING_REVISION_DETAILS_QUERY
			+ " and approval_status_id = (SELECT approval_status_id from cbs.approval_status_dm where approval_name = :status)"
			+ " group by booking_id) as irn INNER JOIN cbs.booking_revision br on br.booking_id=irn.booking_id and "
			+ " br.revision_number=irn.maxRevision"
			+ " left join cbs.contractor con on br.contractor_id=con.contractor_id"
			+ " WHERE br.role_id = rd.role_id and br.approval_status_id = asd.approval_status_id", nativeQuery = true)*/
	
	@Query(value ="with temp as (select *, row_number() over(partition by booking_id order by booking_revision_id desc) rownum from "
			+ " cbs.booking_revision br where br.approval_status_id in (select approval_status_id from cbs.approval_status_dm "
			+ " where approval_name in (:status))) "
			+ " select asd.approval_name as status, temp.booking_id as bookingId, temp.job_name as jobName, rd.role_name as roleName,"
			+ " temp.contracted_from_date as contractedFromDate, temp.contracted_to_date as contractedToDate, temp.changed_by as changedBy,"
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName, temp.booking_revision_id as bookingRevisionId"
			+ " from temp join cbs.booking bo on temp.booking_id=bo.booking_id "
			+ " and bo.status_id=temp.approval_status_id"
			+ " join cbs.role_dm rd on rd.role_id = temp.role_id"
			+ " join cbs.approval_status_dm asd on asd.approval_status_id=temp.approval_status_id"
			+ " left join cbs.contractor con on temp.contractor_id=con.contractor_id"
			+ " where temp.rownum=1 and bo.changed_by =:loggedInUser order by temp.changed_date DESC", nativeQuery = true)
			
	public List<Tuple> retrieveBookingRevisionForDraftOrCancelled(@Param("loggedInUser") String loggedInUser,
			@Param("status") String status, Pageable pageable);

	@Query(value = "SELECT * FROM cbs.booking_revision br where br.booking_id=:bookingId AND br.revision_number="
			+ "(SELECT MAX(revision_number) FROM cbs.booking_revision br where br.booking_Id=:bookingId)", nativeQuery = true)
	public BookingRevision fetchBookingRevisionByBookingId(@Param("bookingId") Long bookingId);
	
	@Query(value ="with temp as (select *, row_number() over(partition by booking_id order by booking_revision_id desc) rownum from "
			+ " cbs.booking_revision br where br.approval_status_id in (select approval_status_id from cbs.approval_status_dm "
			+ " where approval_name not in ('Draft', 'Cancelled'))) "
			+ " select asd.approval_name as status, temp.booking_id as bookingId, temp.job_name as jobName, rd.role_name as roleName,"
			+ " temp.contracted_from_date as contractedFromDate, temp.contracted_to_date as contractedToDate, temp.changed_by as changedBy,"
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName, temp.booking_revision_id as bookingRevisionId"
			+ " from temp join cbs.booking bo on temp.booking_id=bo.booking_id "
			+ " and bo.status_id=temp.approval_status_id"
			+ " join cbs.role_dm rd on rd.role_id = temp.role_id"
			+ " join cbs.approval_status_dm asd on asd.approval_status_id=temp.approval_status_id"
			+ " left join cbs.contractor con on temp.contractor_id=con.contractor_id"
			+ " where temp.rownum=1 and bo.changed_by =:loggedInUser order by temp.changed_date DESC", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForSubmitted(@Param("loggedInUser") String loggedInUser, Pageable pageable);
	
	@Query(value ="with temp as (select *, (select Max(approver_order) from cbs.approver where employee_id=:employeeId) as approverOrder, row_number() over(partition by booking_id order by booking_revision_id desc) rownum from "
			+ " cbs.booking_revision br where br.approval_status_id in (select approval_status_id from cbs.approval_status_dm "
			+ " where approval_name in ('Waiting on Approval 1','Waiting on Approval 2','Waiting on Approval 3'))) "
			+ " select asd.approval_name as status, temp.booking_id as bookingId, temp.job_name as jobName, rd.role_name as roleName,"
			+ " temp.contracted_from_date as contractedFromDate, temp.contracted_to_date as contractedToDate, temp.changed_by as changedBy,"
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName, temp.booking_revision_id as bookingRevisionId"
			+ " from temp join cbs.booking bo on temp.booking_id=bo.booking_id "
			+ " and bo.status_id=temp.approval_status_id"
			+ " join cbs.role_dm rd on rd.role_id = temp.role_id"
			+ " join cbs.approval_status_dm asd on asd.approval_status_id=temp.approval_status_id"
			+ " join cbs.approver_override_jobs aoj on  temp.job_number = aoj.job_number"
			+ " left join cbs.contractor con on temp.contractor_id=con.contractor_id"
			+ " where temp.rownum=1 and aoj.employee_Id = :employeeId"
			+ " and (CASE WHEN asd.approval_name = 'Waiting on Approval 1'  THEN 1"
			+ " WHEN asd.approval_name = 'Waiting on Approval 2'  THEN 2"
			+ " WHEN asd.approval_name = 'Waiting on Approval 3'  THEN 3"
			+ " end) <=temp.approverOrder"
			+ " order by temp.changed_date DESC", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForWaitingForApprovalByJobNumber(@Param("employeeId") Long employeeId, Pageable pageable);
	
	
	@Query(value ="with temp as (select *, (select Max(approver_order) from cbs.approver where employee_id=:employeeId) as approverOrder, row_number() over(partition by booking_id order by booking_revision_id desc) rownum from "
			+ " cbs.booking_revision br where br.approval_status_id in (select approval_status_id from cbs.approval_status_dm "
			+ " where approval_name in ('Waiting on Approval 1','Waiting on Approval 2','Waiting on Approval 3'))) "
			+ " select asd.approval_name as status, temp.booking_id as bookingId, temp.job_name as jobName, rd.role_name as roleName,"
			+ " temp.contracted_from_date as contractedFromDate, temp.contracted_to_date as contractedToDate, temp.changed_by as changedBy,"
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName,temp.booking_revision_id as bookingRevisionId"
			+ " from temp join cbs.booking bo on temp.booking_id=bo.booking_id "
			+ " and bo.status_id=temp.approval_status_id"
			+ " join cbs.role_dm rd on rd.role_id = temp.role_id"
			+ " join cbs.approval_status_dm asd on asd.approval_status_id=temp.approval_status_id"
			+ " left join cbs.contractor con on temp.contractor_id=con.contractor_id"
			+ " where temp.rownum=1 and temp.team_id in(select team_id from cbs.approver where employee_id=:employeeId and approver_order in (1,2,3))"
			+ " and (CASE WHEN asd.approval_name = 'Waiting on Approval 1'  THEN 1"
			+ " WHEN asd.approval_name = 'Waiting on Approval 2'  THEN 2"
			+ " WHEN asd.approval_name = 'Waiting on Approval 3'  THEN 3"
			+ " end) <=temp.approverOrder"
			+ " order by temp.changed_date DESC", nativeQuery = true)
	
	public List<Tuple> retrieveBookingRevisionForWaitingForApprovalByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);
	
	
	
}
