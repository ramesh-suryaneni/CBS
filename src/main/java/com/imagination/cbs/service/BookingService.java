/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

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
	
	//public List<BookingDashBoardDto> getDraftBookingQuery(String approvalName,String logInUser);
	public Page<BookingDashBoardDto> getDraftBooking(String status,String logInUser, int pageNo, int pageSize);
}
