package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.DashBoardBookingDto;

/**
 * @author Pappu Rout
 *
 */

public interface DashBoardService {
	
	 Page<DashBoardBookingDto> getDashBoardBookingsStatusDetails(String status, int pageNo, int pageSize);

}
