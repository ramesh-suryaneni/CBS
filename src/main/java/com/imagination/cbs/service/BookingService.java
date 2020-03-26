/**
 * 
 */
package com.imagination.cbs.service;

import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingService {
	public BookingDto addBookingDetails(BookingRequest booking);

	public BookingDto updateBookingDetails(Long bookingId, BookingRequest booking);

	public BookingDto processBookingDetails(Long bookingId, BookingRequest booking);

	public BookingDto retrieveBookingDetails(Long bookingId);
}
