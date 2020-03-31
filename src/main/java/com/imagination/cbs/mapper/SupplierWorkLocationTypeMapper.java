/**
 * 
 */
package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.SupplierWorkLocationTypeDm;
import com.imagination.cbs.dto.SupplierWorkLocationTypeDto;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Mapper(componentModel = "spring")
public interface SupplierWorkLocationTypeMapper {

	public List<SupplierWorkLocationTypeDto> convertToList(List<SupplierWorkLocationTypeDm> domains);

	public SupplierWorkLocationTypeDto toSupplierWorkLocationTypeDtoFromSupplierWorkLocationTypeDomain(
			SupplierWorkLocationTypeDm SupplierWorkLocationTypeDm);

}
