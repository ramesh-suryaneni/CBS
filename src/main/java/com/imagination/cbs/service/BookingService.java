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
	public Booking storeBookingDetails(BookingDto booking);

}
