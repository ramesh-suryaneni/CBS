package com.imagination.cbs.service.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imagination.cbs.constant.EmailConstants;
import com.imagination.cbs.constant.SecurityConstants;
import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.EmployeePermissions;
import com.imagination.cbs.domain.Permission;
import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.repository.ApproverRepository;
import com.imagination.cbs.repository.EmployeeMappingRepository;
import com.imagination.cbs.repository.EmployeePermissionsRepository;
import com.imagination.cbs.service.EmailService;

/**
 * @author pravin.budage
 *
 */
@Component("emailHelper")
public class EmailHelper {

	private static final String APPROVE_SUBJECT_LINE = "Please Approve: Contractor Booking request # from ";

	@Autowired
	private EmployeePermissionsRepository employeePermissionsRepository;

	@Autowired
	private EmployeeMappingRepository employeeMappingRepository;

	@Autowired
	private ApproverRepository approverRepository;

	@Autowired
	private EmailService emailService;

	public void prepareMailAndSendToHR(BookingRevision latestRevision) {
		Permission permission = new Permission();
		permission.setPermissionId(SecurityConstants.ROLE_CONTRACT_MGT_ID.getRoleDetails());
		List<EmployeePermissions> employeePermissions = employeePermissionsRepository.findAllByPermission(permission);
		List<String> emails = new ArrayList<>();

		for (EmployeePermissions employeePermission : employeePermissions) {
			Optional<EmployeeMapping> employee = employeeMappingRepository
					.findById(employeePermission.getEmployeeMapping().getEmployeeId());
			emails.add(employee.isPresent()
					? employee.get().getGoogleAccount() + EmailConstants.DOMAIN.getConstantString() : "");

		}

		String[] toEmail = emails.stream().toArray(n -> new String[n]);
		MailRequest request = new MailRequest();
		request.setMailTo(toEmail);
		request.setSubject(APPROVE_SUBJECT_LINE.replace("#", "#" + latestRevision.getBooking().getBookingId())
				+ latestRevision.getJobname() + "-" + latestRevision.getChangedBy());
		request.setMailFrom(EmailConstants.FROM_EMAIL.getConstantString());
		emailService.sendEmailForBookingApproval(request, latestRevision,
				EmailConstants.BOOKING_REQUEST_TEMPLATE.getConstantString());

	}

	public void prepareMailAndSend(Booking booking, BookingRevision latestRevision, Long nextApproverOrder) {
		List<Approver> approvers = approverRepository.findAllByTeamAndApproverOrder(booking.getTeam(),
				nextApproverOrder);
		List<String> emails = new ArrayList<>();
		for (Approver approver : approvers) {
			EmployeeMapping employee = approver.getEmployee();
			emails.add(employee.getGoogleAccount() + EmailConstants.DOMAIN.getConstantString());
		}
		String[] toEmail = emails.stream().toArray(n -> new String[n]);

		MailRequest request = new MailRequest();
		request.setMailTo(toEmail);
		request.setSubject(APPROVE_SUBJECT_LINE.replace("#", "#" + booking.getBookingId()) + latestRevision.getJobname()
				+ "-" + latestRevision.getChangedBy());
		request.setMailFrom(EmailConstants.FROM_EMAIL.getConstantString());
		emailService.sendEmailForBookingApproval(request, latestRevision,
				EmailConstants.BOOKING_REQUEST_TEMPLATE.getConstantString());
	}

}
