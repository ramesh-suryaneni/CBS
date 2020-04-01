/**
 * 
 */
package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.SupplierWorkLocationTypeDto;
import com.imagination.cbs.service.SupplierWorkLocationTypeService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/supplier-location-types")
public class SupplierWorkLocationTypeController {
	
	@Autowired
	private SupplierWorkLocationTypeService supplierWorkLocationTypeService;
	
	@GetMapping
	public List<SupplierWorkLocationTypeDto> getAllWorkLocationTypes() throws Exception{
		
		return supplierWorkLocationTypeService.getAllSupplierWorkLocationTypes();
		
	}

}
