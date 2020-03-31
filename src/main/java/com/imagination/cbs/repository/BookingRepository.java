package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imagination.cbs.domain.Booking;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

//	@Modifying
//	@Query("update Booking b set b.statusId=:statusId where b.bookingId=:bookingId")
//	void updateStatusOfBooking(@Param("bookingId") Long bookingId, @Param("statusId") Long statusId);
}
