package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.dto.CurrencyDto;
import com.imagination.cbs.mapper.CurrencyMapper;
import com.imagination.cbs.repository.CurrencyRepository;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

	@InjectMocks
	private CurrencyServiceImpl currencyServiceImpl;
	@Mock
	private CurrencyRepository currencyRepository;

	@Mock
	private CurrencyMapper currencyMapper;

	@Test
	public void shouldReturnListOfCurrencyDM() {

		List<CurrencyDm> listOfCurrencyDm = getListOfCurrencyDm();
		when(currencyRepository.findAll()).thenReturn(listOfCurrencyDm);
		when(currencyMapper.convertToList(listOfCurrencyDm)).thenReturn(getListOfCurrencyDto());

		List<CurrencyDto> actualListOfCurrencies = currencyServiceImpl.getAllCurrencies();
		assertEquals("103", actualListOfCurrencies.get(0).getCurrencyId());
		assertEquals("EUR", actualListOfCurrencies.get(0).getCurrencyCode());
		assertEquals("Euros", actualListOfCurrencies.get(0).getCurrencyName());

	}

	private List<CurrencyDm> getListOfCurrencyDm() {

		List<CurrencyDm> list = new ArrayList<>();

		CurrencyDm currencyDm = new CurrencyDm();
		currencyDm.setCurrencyId(2L);
		currencyDm.setCurrencyCode("EUR");
		currencyDm.setCurrencyName("Euros");

		list.add(currencyDm);

		return list;
	}

	private List<CurrencyDto> getListOfCurrencyDto() {

		CurrencyDto currencyDto = new CurrencyDto();
		List<CurrencyDto> currencyDtoList = new ArrayList<>();

		currencyDto.setCurrencyId("103");
		currencyDto.setCurrencyCode("EUR");
		currencyDto.setCurrencyName("Euros");

		currencyDtoList.add(currencyDto);

		return currencyDtoList;

	}

}
