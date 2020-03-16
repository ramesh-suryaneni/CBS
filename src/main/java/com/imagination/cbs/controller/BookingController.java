/**
 * 
 */
package com.imagination.cbs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.service.BookingService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingServiceImpl;

	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Booking> addBookingDetails(@Valid @RequestBody BookingDto booking, BindingResult result) {
		if (result.hasErrors()) {
			throw new CBSValidationException(result.getFieldError().getDefaultMessage());
		}
		Booking storedBooking = bookingServiceImpl.addBookingDetails(booking);
		return new ResponseEntity<Booking>(storedBooking, HttpStatus.CREATED);
	}
}
