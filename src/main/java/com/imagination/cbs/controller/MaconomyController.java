/**
 * 
 */
package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.JobDataDto;
import com.imagination.cbs.service.MaconomyService;

/**
 * @author pappu.rout
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/macanomy")
public class MaconomyController {
	
	@Autowired
	private MaconomyService maconomyService;
	
	@GetMapping()
	public JobDataDto getJobDetails(@RequestParam String jobNumber){
		
		return maconomyService.getMaconomyJobNumberAndDepartmentsDetails(jobNumber, new JobDataDto(), "jobNumber", "");
	}
		
}
