/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;
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
	
	
	@GetMapping("/disciplines")
	public List<DisciplineDto> getAllDisciplines() {

		return bookingServiceImpl.getAllDisciplines();

	}
	
	@GetMapping("/contractorRoles/{disciplineId}")
	public List<ContractorRoleDto> findAllContractorRoles(@PathVariable Long disciplineId) {
		
		return bookingServiceImpl.getAllContractorRoles(disciplineId);

	}

}
