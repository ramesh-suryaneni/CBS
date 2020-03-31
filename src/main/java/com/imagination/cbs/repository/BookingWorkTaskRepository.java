package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.imagination.cbs.domain.BookingWorkTask;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingWorkTaskRepository extends JpaRepository<BookingWorkTask, Long> {
	//List<BookingWorkTask> findByBookingRevisionId(@Param("bookingRevisionId") Long bookingRevisionId);
}
