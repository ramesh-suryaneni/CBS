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

import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.service.CurrencyService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/currencies")
public class CurrenciesController {
	
	@Autowired
	private CurrencyService currencyService;
	
	@GetMapping
	public List<CurrencyDto> getAllCurrencies(){
		
		return currencyService.getAllCurrencies();
		
	}

}
