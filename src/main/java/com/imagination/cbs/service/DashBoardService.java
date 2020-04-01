package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.dto.BookingDashBoardDto;

/**
 * @author Pappu Rout
 *
 */

public interface DashBoardService {
	
	 Page<BookingDashBoardDto> getDashBoardBookingsStatusDetails(String status, int pageNo, int pageSize);

}
