package com.imagination.cbs.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import org.springframework.stereotype.Repository;


@Repository("bookingDashBoardCustomRepository")
public class BookingDashBoardCustomRepository {
	
	@PersistenceContext
	private EntityManager entityManager;


	public List<Tuple> getDraftBookings(String status,String logInUser)
	{
		StringBuilder draftBookingQuery = new StringBuilder();
		
		draftBookingQuery.append("SELECT r.roleName as rn,b.booking.bookingId as bid,b.jobname AS job,b.contractorName AS name, "); 
		draftBookingQuery.append("b.contractedToDate AS toDate,b.contractedFromDate AS fromDate,");
		draftBookingQuery.append("b.changedBy AS changeBy,b.changedDate AS chDate ");
		draftBookingQuery.append("FROM BookingRevision b,RoleDm r,ContractorEmployeeRole cer ");
		draftBookingQuery.append("WHERE b.contractorEmployeeRoleId=cer.contractorEmployeeRoleId and ");
		draftBookingQuery.append("r.roleId=cer.roleDm.roleId and b.changedBy=:log_InUser and b.booking.bookingId IN(");
		draftBookingQuery.append("SELECT bo.bookingId as id FROM Booking bo WHERE ");
		draftBookingQuery.append("bo.approvalStatusDm.approvalStatusId=(");
		draftBookingQuery.append("SELECT a.approvalStatusId FROM ApprovalStatusDm a ");
		draftBookingQuery.append("WHERE a.approvalName=:approval_Name))") ;
		draftBookingQuery.append("order by b.changedDate DESC");
		
		Query query = entityManager.createQuery(draftBookingQuery.toString(),Tuple.class);
		query.setParameter("approval_Name", status);
		query.setParameter("log_InUser", logInUser);
		return query.getResultList();
		
	}

}
