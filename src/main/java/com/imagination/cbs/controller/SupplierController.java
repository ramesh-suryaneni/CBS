package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.SupplierDto;
import com.imagination.cbs.service.SupplierService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	/*
	 * @GetMapping("/{name}") public List<SupplierDto>
	 * getSuppliersBySupplierName(@PathVariable("name") String name) {
	 * 
	 * return supplierService.getSuppliersBySupplierName(name);
	 * 
	 * }
	 */

	@GetMapping
	public List<SupplierDto> getAllSupplierTypeDM() {
		return supplierService.getAllSupplierTypeDM();
	}

}
