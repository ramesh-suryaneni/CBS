/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorIndexDto;
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
	public List<ContractorDto> getContractorsByContractorName(@PathVariable("contractorName") String contractorName){
		
		return contractorService.getContractorsByContractorName(contractorName);
	}
	
	@GetMapping("/index")
	public Page<ContractorIndexDto> getContractorIndexDeatils(
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "contractorName") String sortingField,
            @RequestParam(defaultValue = "ASC") String sortingOrder){
		return contractorService.getContractorIndexDeatils(pageNo, pageSize, sortingField, sortingOrder);
	}
	
	@GetMapping("/index/{contractorName}")
	public Page<ContractorIndexDto> getContractorIndexDeatilsByContractor(@PathVariable("contractorName") String contractorName,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "contractorName") String sortingField,
            @RequestParam(defaultValue = "ASC") String sortingOrder){
		return contractorService.getContractorIndexDeatilsByContractorName(contractorName, pageNo, pageSize, sortingField, sortingOrder);
	}
	
}