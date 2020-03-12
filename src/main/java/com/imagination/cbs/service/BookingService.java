/**
 * 
 */
package com.imagination.cbs.service;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.dto.BookingDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingService {
	public Booking addBookingDetails(BookingDto booking);

}
