/**
 * 
 */
package com.imagination.cbs.service;

import org.springframework.data.domain.Page;
import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.dto.BookingDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingService {
	public BookingDto addBookingDetails(BookingDto booking);

	public BookingDto updateBookingDetails(Long bookingId, BookingDto booking);

	public BookingDto processBookingDetails(Long bookingId, BookingDto booking);
	
	public Page<BookingDashBoardDto> getAllBookingsForDraft(String status,String logInUser,Integer page,Integer limit);
	
}
