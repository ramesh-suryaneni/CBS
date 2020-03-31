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

	@GetMapping("/search")
	public Page<ContractorEmployeeDto> getContractorEmployeeDetails(
			@RequestParam Long role,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "roleId") String sortingField,
			@RequestParam(defaultValue = "ASC") String sortingOrder){
	
		if(!name.equals("")){
			return contractorService.geContractorEmployeeDetailsByRoleIdAndName(role, name, pageNo, pageSize, sortingField, sortingOrder);
		}
		return contractorService.geContractorEmployeeDetailsByRoleId(role, pageNo, pageSize, sortingField, sortingOrder);
	}

}