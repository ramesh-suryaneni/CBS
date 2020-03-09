/**
 * 
 */
package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.mapper.BookingMapper;
import com.imagination.cbs.repository.BookingRepository;
import com.imagination.cbs.service.BookingService;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingMapper bookingMapper;

	@Override
	public Booking storeBookingDetails(BookingDto booking) {
		return bookingRepository.save(bookingMapper.toBookingDomainFromBookingDto(booking));
	}
}
