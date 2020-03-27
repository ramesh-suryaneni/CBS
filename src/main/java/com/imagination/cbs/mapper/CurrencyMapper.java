/**
 * 
 */
package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.dto.CurrencyDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Mapper(componentModel = "spring")
public interface CurrencyMapper {
	
	public List<CurrencyDto> convertToList(List<CurrencyDm> domains);

}
