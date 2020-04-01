package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.Booking;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
