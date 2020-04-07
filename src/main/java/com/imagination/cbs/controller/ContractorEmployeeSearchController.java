/**
 * 
 */
package com.imagination.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorEmployeeSearchDto;
import com.imagination.cbs.service.ContractorService;

/**
 * @author Ramesh.Suryaneni
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/contractor-employees")
public class ContractorEmployeeSearchController {
	
	@Autowired
	private ContractorService contractorService;

	@GetMapping()
	public Page<ContractorEmployeeSearchDto> searchContractorEmployees(
			@RequestParam (defaultValue = "0") Long role,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "roleId") String sortingBy,
			@RequestParam(defaultValue = "ASC") String sortingOrder){
	
		if(!name.equals("")){
			return contractorService.geContractorEmployeeDetailsByRoleIdAndName(role, name, pageNo, pageSize, sortingBy, sortingOrder);
		}if(role!=0) {
			System.out.println("Role Id");
			return contractorService.geContractorEmployeeDetailsByRoleId(role, pageNo, pageSize, sortingBy, sortingOrder);			
		}
		return contractorService.geContractorEmployeeDetails(pageNo, pageSize, sortingBy, sortingOrder);
	}

}
