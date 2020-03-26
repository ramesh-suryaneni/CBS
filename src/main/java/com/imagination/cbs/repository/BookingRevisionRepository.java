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
	
	@Query("SELECT r.roleName as rname,b.booking.bookingId as id,b.jobname AS job,b.contractorName AS cname, " 
			+"b.contractedToDate AS enddate,b.contractedFromDate AS strtdate,"
			+"b.changedBy AS change,b.changedDate AS cdate "
			+"FROM BookingRevision b,RoleDm r,ContractorEmployeeRole cer "
			+"WHERE b.contractorEmployeeRoleId=cer.contractorEmployeeRoleId and "
			+"r.roleId=cer.roleDm.roleId and b.changedBy=?2 and b.approvalStatusId =("
			+"SELECT a.approvalStatusId FROM ApprovalStatusDm a "
			+"WHERE a.approvalName=?1)"
			+"order by b.changedDate DESC")
	
		public List<Tuple> getAllDraftBookings(String status,String logInUser);
	
	
}
