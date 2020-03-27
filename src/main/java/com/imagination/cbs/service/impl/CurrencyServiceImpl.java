/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.mapper.CurrencyMapper;
import com.imagination.cbs.repository.CurrencyRepository;
import com.imagination.cbs.service.CurrencyService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private CurrencyMapper currencyMapper;

	@Cacheable("currencies")
	@Override
	public List<CurrencyDto> getAllCurrencies() {
		return currencyMapper.convertToList(currencyRepository.findAll());
	}

}
