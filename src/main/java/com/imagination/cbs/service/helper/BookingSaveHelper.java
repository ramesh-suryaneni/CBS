package com.imagination.cbs.service.helper;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.repository.ApprovalStatusDmRepository;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.security.CBSUser;

/**
 * @author pravin.budage
 *
 */
@Component("bookingSaveHelper")
public class BookingSaveHelper {

	@Autowired
	private ApprovalStatusDmRepository approvalStatusDmRepository;

	@Autowired
	private BookingRepository bookingRepository;

	public Booking saveBooking(Booking booking, BookingRevision revision, Long nextStatus, CBSUser user) {
		Optional<ApprovalStatusDm> approvalStatus = approvalStatusDmRepository.findById(nextStatus);
		ApprovalStatusDm nextApprovalStatus = null;
		if (approvalStatus.isPresent()) {
			nextApprovalStatus = approvalStatus.get();
		}
		BookingRevision newObject = new BookingRevision(revision);
		newObject.setApprovalStatus(nextApprovalStatus);
		newObject.setRevisionNumber(revision.getRevisionNumber() + 1);
		newObject.setChangedBy(user.getDisplayName());
		newObject.setChangedDate(new Timestamp(System.currentTimeMillis()));

		booking.setApprovalStatus(nextApprovalStatus);
		booking.addBookingRevision(newObject);

		bookingRepository.save(booking);

		return booking;
	}

	public BookingRevision getLatestRevision(Booking booking) {

		Optional<BookingRevision> max = booking.getBookingRevisions().stream()
				.max(Comparator.comparing(BookingRevision::getRevisionNumber));
		if (max.isPresent()) {
			return max.get();
		}
		return new BookingRevision();
	}
}
