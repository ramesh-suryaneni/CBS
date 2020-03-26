/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.exception.CBSValidationException;
import com.imagination.cbs.repository.BookingRevisionRepository;
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
	
	@Autowired
	private BookingRevisionRepository bookingRevisionRepository;


	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> addBookingDetails(@RequestBody BookingDto booking) {
		BookingDto draftBooking = bookingServiceImpl.addBookingDetails(booking);
		return new ResponseEntity<BookingDto>(draftBooking, HttpStatus.CREATED);
	}

	@PatchMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> updateBookingDetails(@PathVariable("booking_id") Long bookingId,
			@RequestBody BookingDto booking) {
		BookingDto updatedBooking = bookingServiceImpl.updateBookingDetails(bookingId, booking);
		return new ResponseEntity<BookingDto>(updatedBooking, HttpStatus.OK);
	}

	@PutMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> processBookingDetails(@PathVariable("booking_id") Long bookingId,
			@Valid @RequestBody BookingDto booking, BindingResult result) {
		if (result.hasErrors()) {
			throw new CBSValidationException(result.getFieldError().getDefaultMessage());
		}
		BookingDto processedBooking = bookingServiceImpl.processBookingDetails(bookingId, booking);
		return new ResponseEntity<BookingDto>(processedBooking, HttpStatus.OK);
	}
	
	//aashoo and akshay will work
	//give proper method name
	@GetMapping("/test")
	public void getDashBoard(){
		
		List<Tuple> test = bookingRevisionRepository.test();
		
		System.out.println("ttttt "+test);
		
		//[1028, Draft, JLR Experience Center, 2020-04-02 20:48:05.123, 2020-03-11 20:48:05.123, Pappu, 3215, 2D Senior]
		
		//[1028, Draft, JLR Experience Center, 2020-04-02 20:48:05.123, 2020-03-11 20:48:05.123, Pappu, 3215, 2D Senior, 2020-03-11 20:48:05.123, Pappu]
	}

}


