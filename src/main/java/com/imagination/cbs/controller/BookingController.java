/**
 * 
 */
package com.imagination.cbs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ApproveRequest;
import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.service.BookingService;
import com.imagination.cbs.service.DashBoardService;
import com.imagination.cbs.util.BookingValidator;

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
	private BookingValidator bookingValidator;
	
	@Autowired
	private DashBoardService dashBoardService;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> addBookingDetails(@RequestBody BookingRequest booking) {
		BookingDto draftBooking = bookingServiceImpl.addBookingDetails(booking);
		return new ResponseEntity<BookingDto>(draftBooking, HttpStatus.CREATED);
	}

	@PatchMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> updateBookingDetails(@PathVariable("booking_id") Long bookingId,
			@RequestBody BookingRequest booking) {
		BookingDto updatedBooking = bookingServiceImpl.updateBookingDetails(bookingId, booking);
		return new ResponseEntity<BookingDto>(updatedBooking, HttpStatus.OK);
	}

	@PutMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> processBookingDetails(@PathVariable("booking_id") Long bookingId,
			@Valid @RequestBody BookingRequest booking, BindingResult result) throws MethodArgumentNotValidException {
		bookingValidator.validate(booking, result);
		if (result.hasErrors()) {
			throw new MethodArgumentNotValidException(null, result);
		}
		BookingDto processedBooking = bookingServiceImpl.submitBookingDetails(bookingId, booking);
		return new ResponseEntity<BookingDto>(processedBooking, HttpStatus.OK);
	}

	@GetMapping()
	public Page<BookingDashBoardDto> getDashBoardBookingsStatusDetails(@RequestParam String status,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "1000") Integer pageSize) {
		return dashBoardService.getDashBoardBookingsStatusDetails(status, pageNo, pageSize);
	}
	
	@PostMapping("/process-request")
	public BookingDto approveBooking(@RequestBody ApproveRequest request) throws Exception{
		
		return bookingServiceImpl.approveBooking(request);
		
	}
	
	@GetMapping("/{booking_id}")
	public BookingDto getBookingDetails(@PathVariable("booking_id") Long bookingId) {
		
		return bookingServiceImpl.retrieveBookingDetails(bookingId);
		
	}
	
	@DeleteMapping("/{booking_id}")
	public BookingDto canceBooking(@PathVariable("booking_id") Long bookingId) {
		
		return bookingServiceImpl.cancelooking(bookingId);
		
	}
}
