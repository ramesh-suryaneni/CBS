/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.DashBoardBookingDto;
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
		return new ResponseEntity<>(draftBooking, HttpStatus.CREATED);
	}

	@PatchMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> updateBookingDetails(@PathVariable("booking_id") Long bookingId,
			@RequestBody BookingRequest booking) {
		BookingDto updatedBooking = bookingServiceImpl.updateBookingDetails(bookingId, booking);
		return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
	}

	@PutMapping(value = "/{booking_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BookingDto> processBookingDetails(@PathVariable("booking_id") Long bookingId,
			@Valid @RequestBody BookingRequest booking, BindingResult result) throws MethodArgumentNotValidException {
		
		if (result.hasErrors()) {
			throw new MethodArgumentNotValidException(null, result);
		}
		
		bookingValidator.validate(booking, result);
		
		BookingDto processedBooking = bookingServiceImpl.submitBookingDetails(bookingId, booking);
		return new ResponseEntity<>(processedBooking, HttpStatus.OK);
	}

	@GetMapping()
	public Page<DashBoardBookingDto> getDashBoardBookingsStatusDetails(@RequestParam String status,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "1000") Integer pageSize) {
		return dashBoardService.getDashBoardBookingsStatusDetails(status, pageNo, pageSize);
	}

	@PostMapping("/process-request")
	public BookingDto approveBooking(@RequestBody ApproveRequest request) {

		return bookingServiceImpl.approveBooking(request);

	}

	@GetMapping("/{booking_id}")
	public BookingDto getBookingDetails(@PathVariable("booking_id") Long bookingId) {
		return bookingServiceImpl.retrieveBookingDetails(bookingId);
	}

	@DeleteMapping("/{booking_id}")
	public BookingDto canceBooking(@PathVariable("booking_id") Long bookingId) {
		return bookingServiceImpl.cancelBooking(bookingId);
	}

	@GetMapping("/{booking_id}/revisions")
	public List<BookingDto> getBookingRevisions(@PathVariable("booking_id") Long bookingId) {
		return bookingServiceImpl.retrieveBookingRevisions(bookingId);
	}

	@GetMapping("/{booking_id}/reminder")
	public ResponseEntity<Map<Integer, String>> sendBookingReminder(@PathVariable("booking_id") Long bookingId) {
		bookingServiceImpl.sendBookingReminder(bookingId);
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Reminder Mail Sent Sucessfully");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
