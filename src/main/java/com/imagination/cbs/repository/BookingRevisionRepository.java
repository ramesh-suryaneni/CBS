package com.imagination.cbs.repository;

import java.util.List;
import java.util.Optional;

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
	
	@Query(value ="with temp as (select *, row_number() over(partition by booking_id order by booking_revision_id desc) rownum from "
			+ " cbs.booking_revision br where br.approval_status_id in (select approval_status_id from cbs.approval_status_dm "
			+ " where approval_name in (:status))) "
			+ " select asd.approval_name as status, temp.booking_id as bookingId, temp.job_name as jobName, rd.role_name as roleName,"
			+ " temp.contracted_from_date as contractedFromDate, temp.contracted_to_date as contractedToDate, temp.changed_by as changedBy,"
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName, temp.booking_revision_id as bookingRevisionId, "
			+ " bo.changed_by as bookingCreater  "
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
			+ " temp.changed_date as changedDate, con.contractor_name as contractorName, temp.booking_revision_id as bookingRevisionId,"
			+ " bo.changed_by as bookingCreater "
			+ " from temp join cbs.booking bo on temp.booking_id=bo.booking_id "
			+ " and bo.status_id=temp.approval_status_id"
			+ " join cbs.role_dm rd on rd.role_id = temp.role_id"
			+ " join cbs.approval_status_dm asd on asd.approval_status_id=temp.approval_status_id"
			+ " left join cbs.contractor con on temp.contractor_id=con.contractor_id"
			+ " where temp.rownum=1 and bo.changed_by =:loggedInUser order by temp.changed_date DESC", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForSubmitted(@Param("loggedInUser") String loggedInUser, Pageable pageable);
	
	@Query(value ="with bookingRevesionTemp as (select br.booking_id as bookingId, br.job_name as jobName,br.contracted_from_date as contractedFromDate,"
			+ " br.contracted_to_date as contractedToDate,br.changed_by as changedBy, bo.changed_by as bookingCreater, br.changed_date as changedDate,br.booking_revision_id as bookingRevisionId,"
			+ " br.contractor_id as contractorId, asd.approval_name as status, rd.role_name as roleName, br.completed_agreement_pdf as completedAgreementPdf from cbs.booking_revision br,"
			+ " cbs.employee_mapping emp,cbs.approver_override_jobs aoj,cbs.booking bo,cbs.approval_status_dm asd, cbs.role_dm rd"
			+ " where bo.booking_id = br.booking_id and bo.status_id=br.approval_status_id and asd.approval_status_id=br.approval_status_id"
			+ " and rd.role_id = br.role_id and aoj.employee_id=emp.employee_id and br.job_number=aoj.job_number"
			+ " and br.approval_status_id = 1002 and emp.employee_id =:employeeId)select bookingRevesionTemp.*, con.contractor_name contractorName"
			+ " from bookingRevesionTemp left join cbs.contractor con on con.contractor_id = bookingRevesionTemp.contractorId "
			+ "order by bookingRevesionTemp.changedDate desc", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForWaitingForApprovalByJobNumber(@Param("employeeId") Long employeeId, Pageable pageable);
	
	
	@Query(value = "with bookingRevesionTemp as (select br.booking_id as bookingId, br.job_name as jobName, rd.role_name as roleName,"
			+ " br.contracted_from_date as contractedFromDate, br.contracted_to_date as contractedToDate, br.changed_by as changedBy, bo.changed_by as bookingCreater, "
			+ " br.changed_date as changedDate,br.booking_revision_id as bookingRevisionId,br.contractor_id as contractorId, asd.approval_name as status,"
			+ " br.completed_agreement_pdf as completedAgreementPdf "
			+ " from cbs.booking_revision br, cbs.approver approver, cbs.role_dm rd, cbs.approval_status_dm asd,cbs.booking bo where"
			+ " case when br.approval_status_id = 1002 then 1 when br.approval_status_id = 1003 then 2"
			+ " when br.approval_status_id = 1004 then 3 "
			+ " end = approver.approver_order and br.team_id = approver.team_id and rd.role_id=br.role_id and br.approval_status_id=asd.approval_status_id"
			+ "	and bo.booking_id= br.booking_id and bo.status_id=br.approval_status_id"
			+ " and approver.employee_id=:employeeId) select bookingRevesionTemp.*, con.contractor_name contractorName from bookingRevesionTemp"
			+ " left join cbs.contractor con on con.contractor_id = bookingRevesionTemp.contractorId order "
			+ " by bookingRevesionTemp.changedDate desc /*#pageable*/ ", nativeQuery = true)
	public List<Tuple> retrieveBookingRevisionForWaitingForApprovalByEmployeeId(@Param("employeeId") long  employeeId,Pageable pageable);
	
	@Query(value ="with bookingRevesionTemp as (select br.booking_id as bookingId, br.job_name as jobName,br.contracted_from_date as contractedFromDate,"
			+ " br.contracted_to_date as contractedToDate,br.changed_by as changedBy, bo.changed_by as bookingCreater, br.changed_date as changedDate,br.booking_revision_id"
			+ " as bookingRevisionId,br.contractor_id as contractorId, asd.approval_name as status, rd.role_name as roleName, br.completed_agreement_pdf as completedAgreementPdf"
			+ " from cbs.booking_revision br,"
			+ " cbs.employee_mapping emp, cbs.booking bo,cbs.approval_status_dm asd ,cbs.role_dm rd	where bo.booking_id = br.booking_id"
			+ " and bo.status_id = br.approval_status_id and br.approval_status_id = 1005 and asd.approval_status_id=br.approval_status_id"
			+ " and rd.role_id = br.role_id	and emp.employee_id=:employeeId)select bookingRevesionTemp.*, con.contractor_name contractorName"
			+ " from bookingRevesionTemp left join cbs.contractor con on con.contractor_id = bookingRevesionTemp.contractorId order by "
			+ " bookingRevesionTemp.changedDate desc", nativeQuery = true)
			public List<Tuple> retrieveBookingRevisionForWaitingForHRApproval(@Param("employeeId") Long employeeId, Pageable pageable);
		
    Optional<BookingRevision> findByAgreementId(String agreementId);
    
	@Query("SELECT DISTINCT(br.jobname) FROM BookingRevision br where br.contractEmployee.contractorEmployeeId=:contractorEmployeeId and br.approvalStatus.approvalStatusId=:statusId")
	List<String> findByContractEmployeeId(@Param("contractorEmployeeId") Long contractorEmployeeId, @Param("statusId") Long statusId);

}
