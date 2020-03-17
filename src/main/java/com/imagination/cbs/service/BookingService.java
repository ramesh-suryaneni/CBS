/**
 * 
 */
package com.imagination.cbs.service;

import com.imagination.cbs.dto.BookingDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingService {
	public BookingDto addBookingDetails(BookingDto booking);

	public BookingDto updateBookingDetails(Long bookingId, BookingDto booking);

	public BookingDto processBookingDetails(Long bookingId, BookingDto booking);
}
