package com.imagination.cbs.service.helper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imagination.cbs.constant.ApprovalStatusConstant;
import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.ApproverOverrides;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.Team;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.CBSUnAuthorizedException;
import com.imagination.cbs.repository.ApproverOverridesRepository;
import com.imagination.cbs.repository.ApproverRepository;
import com.imagination.cbs.security.CBSUser;

/**
 * @author pravin.budage
 *
 */
@Component("bookingApproveHelper")
public class BookingApproveHelper {
	
	@Autowired
	private ApproverRepository approverRepository;

	@Autowired
	private ApproverOverridesRepository approverOverridesRepository;

	@Autowired
	private BookingSaveHelper bookingSaveHelper;

	@Autowired
	private EmailHelper emailHelper;

	public void approve(Booking booking, CBSUser user) {

		BookingRevision latestRevision = bookingSaveHelper.getLatestRevision(booking);

		String jobNumber = latestRevision.getJobNumber();
		Team approverTeam = latestRevision.getTeam();

		Long currentApprovalStatus = latestRevision.getApprovalStatus().getApprovalStatusId();
		boolean isInApprovalStatus = isInApproverStatus(currentApprovalStatus.intValue());

		if (isInApprovalStatus) {
			// find next approval status based on current status and approval
			Long nextStatus = getNextApprovalStatus(currentApprovalStatus, approverTeam);

			// check if booking can be override.
			ApproverOverrides approverOverride = approverOverridesRepository
					.findByEmployeeIdAndJobNumber(user.getEmpId(), jobNumber);
			if (approverOverride != null) {
				// save new revision with next status
				bookingSaveHelper.saveBooking(booking, latestRevision,
						ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId(), user);
				// send mail to HR approver
				emailHelper.prepareMailAndSendToHR(latestRevision);

			} else if (isUserCanApprove(approverTeam.getTeamId(), user.getEmpId(), currentApprovalStatus)) {

				// save new revision with next status
				bookingSaveHelper.saveBooking(booking, latestRevision, nextStatus, user);

				Long order = null;
				switch (nextStatus.intValue()) {
				case 1002:
					order = 1L; // approver order#1
					break;
				case 1003:
					order = 2L; // approver order#2
					break;
				case 1004:
					order = 3L; // approver order#3
					break;
				case 1005:
					order = 5L; // HR Approver
					break;
				default:
					break;
				}
				if (5L != order) {
					// send mail to next approver based on status
					emailHelper.prepareMailAndSend(booking, latestRevision, order);
				} else {
					emailHelper.prepareMailAndSendToHR(latestRevision);
				}

			} else {
				throw new CBSUnAuthorizedException(
						"Not Authorized to perform this operation; insufficient previllages");
			}

		} else {
			throw new CBSApplicationException("Request can't be processed, Booking is not in approval status");
		}

	}

	private boolean isInApproverStatus(int status) {
		boolean result = false;
		switch (status) {
		case 1002: // Waiting for Approval 1
		case 1003: // Waiting for Approval 2
		case 1004: // Waiting for Approval 3
			result = true;
			break;
		default:
			break;
		}
		return result;
	}

	private Long getNextApprovalStatus(Long currentStatus, Team approverTeam) {

		List<Approver> approvers = approverRepository.findAllByTeam(approverTeam);
		Optional<Approver> max = approvers.stream().max(Comparator.comparing(Approver::getApproverOrder));
		Long maxApproverOrder = -1L;
		if (max.isPresent()) {
			maxApproverOrder = max.get().getApproverOrder();
		}

		Long nextStatus = -1L;

		switch (currentStatus.intValue()) {
		case 1002: // current status - waiting for approval 1
			switch (maxApproverOrder.intValue()) {
			case 1: // approver order 1, Sent to HR
				nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId();
				break;
			case 2: // approver order 2
			case 3: // approver order 3, waiting for approval #2
				nextStatus = ApprovalStatusConstant.APPROVAL_2.getApprovalStatusId();
				break;
			default:
				break;
			}
			break;

		case 1003: // current status - waiting for approval 2
			switch (maxApproverOrder.intValue()) {
			case 2: // Sent to HR
				nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId();
				break;
			case 3: // waiting for approval#3
				nextStatus = ApprovalStatusConstant.APPROVAL_3.getApprovalStatusId();
				break;
			default:
				break;
			}
			break;

		case 1004: // current status - waiting for approval 3 and Sent to HR
			nextStatus = ApprovalStatusConstant.APPROVAL_SENT_TO_HR.getApprovalStatusId();
			break;
		default:
			break;
		}
		return nextStatus;
	}

	private boolean isUserCanApprove(Long teamId, Long empId, Long currentStatus) {
		Long order = 0L;
		Team team = new Team();
		team.setTeamId(teamId);

		EmployeeMapping employee = new EmployeeMapping();
		employee.setEmployeeId(empId);
		switch (currentStatus.intValue()) {
		case 1002:
			order = 1L; // approver order#1
			break;
		case 1003:
			order = 2L; // approver order#2
			break;
		case 1004:
			order = 3L; // approver order#3
			break;
		default:
			break;
		}
		Approver approver = approverRepository.findByTeamAndEmployeeAndApproverOrder(team, employee, order);
		return (approver != null);
	}
}
