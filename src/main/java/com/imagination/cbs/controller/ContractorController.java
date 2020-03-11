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

import com.imagination.cbs.domain.Contractor;
import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.service.ContractorService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/contractors")
public class ContractorController {

	@Autowired
	private ContractorService contractorService;

	@GetMapping("/{contractorName}")
	public List<ContractorDto> getContractorsContainingName(@PathVariable String contractorName){
		
		return contractorService.getContractorsContainingName(contractorName);
	}
}
