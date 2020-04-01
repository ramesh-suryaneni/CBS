/**
 * 
 */
package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorDto;
import com.imagination.cbs.dto.ContractorEmployeeDto;
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
	
	/**
	 * list contractors and search based on name if given
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping()
	public Page<ContractorDto> searchContractors(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "contractorName") String sortingBy,
			@RequestParam(defaultValue = "ASC") String sortingOrder){
		
		if(!name.equals("")) {
			return contractorService.getContractorDeatilsContainingName(name, pageNo, pageSize, sortingBy, sortingOrder);			
		}
		
		return contractorService.getContractorDeatils(pageNo, pageSize, sortingBy, sortingOrder);
		
	}
	
	@GetMapping("/{id}")
	public ContractorDto getContractor(@PathVariable("id") Long id){
		
		
		return contractorService.getContractorByContractorId(id);
	
		
	}
	
	
	@GetMapping("/{contractorId}/employees/{empId}")
	public ContractorEmployeeDto getContractor(@PathVariable("contractorId") Long contractorId,
			@PathVariable("empId") Long employeeId){
		
		return contractorService.getContractorEmployeeByContractorIdAndEmployeeId(contractorId, employeeId);
	
		
	}
	
	@PostMapping()
	public ContractorDto addNewContractor(ContractorDto request){
		
		//TODO:implement add new contractor
		
		return null;
	
		
	}
	
	@PostMapping("/{contractorId}/employees")
	public ContractorEmployeeDto addNewContractorEmployee(ContractorEmployeeDto request){
		
		//TODO:implement add new contractor
		
		return null;
	
		
	}
	
}