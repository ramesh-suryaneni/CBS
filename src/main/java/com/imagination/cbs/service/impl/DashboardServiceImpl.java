package com.imagination.cbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.BookingDashBoardDto;
import com.imagination.cbs.repository.BookingRevisionRepository;
import com.imagination.cbs.service.DashBoardService;
import com.imagination.cbs.service.LoggedInUserService;
import com.imagination.cbs.util.ResultTrasnsformerHelper;

/**
 * @author Pappu Rout
 *
 */


@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService{
	
	@Autowired
	private BookingRevisionRepository bookingRevisionRepository;
	
	@Autowired
	private LoggedInUserService loggedInUserService;
	
	@Autowired
	private ResultTrasnsformerHelper resultTrasnsformerHelper;
	
	@Override
	public Page<BookingDashBoardDto> getDashBoardBookingsStatusDetails(String status, int pageNo, int pageSize) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		Long employeeId = loggedInUserService.getLoggedInUserDetails().getEmpId();
		
		List<BookingDashBoardDto> bookingDashboradDtos = new ArrayList<>();

		Pageable pageable = createPageable(pageNo, pageSize, "br.changed_date", "DESC");
		List<Tuple> bookingRevisions = null;

		if (status.equalsIgnoreCase("Submitted")) {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForSubmitted(loggedInUser, pageable);
		} else if(status.equalsIgnoreCase("waiting")){
			return retrieveBookingRevisionForWaitingForApproval(employeeId,pageable, bookingDashboradDtos);
		} else {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForDraftOrCancelled(loggedInUser, status, pageable);
		}

		List<BookingDashBoardDto> bookingDashBoardDtos = resultTrasnsformerHelper.transormListOfTupleToListOfInputObject(bookingRevisions, BookingDashBoardDto.class);

		return new PageImpl<>(bookingDashBoardDtos, pageable, bookingDashBoardDtos.size());
	}
	
	private Pageable createPageable(int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Sort sort = null;
		if (sortingOrder.equals("ASC")) {
			sort = Sort.by(Direction.ASC, sortingField);
		}
		if (sortingOrder.equals("DESC")) {
			sort = Sort.by(Direction.DESC, sortingField);
		}

		return PageRequest.of(pageNo, pageSize, sort);
	}

	
	private Page<BookingDashBoardDto> retrieveBookingRevisionForWaitingForApproval(Long employeeId,  Pageable pageable, List<BookingDashBoardDto> bookingDashboradDtos){
		
		List<Tuple> retrieveBookingRevisionForWaitingByJobName = bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByJobNumber(employeeId, pageable);
		
		List<BookingDashBoardDto> bookingDashboradDtosList = resultTrasnsformerHelper.transormListOfTupleToListOfInputObject(retrieveBookingRevisionForWaitingByJobName, BookingDashBoardDto.class);
		
		List<Tuple> retrieveBookingRevisionForWaitingByEmployeeId = bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByEmployeeId(employeeId, pageable);
		
		bookingDashboradDtosList.addAll(resultTrasnsformerHelper.transormListOfTupleToListOfInputObject(retrieveBookingRevisionForWaitingByEmployeeId, BookingDashBoardDto.class));
		
		return new PageImpl<>(bookingDashboradDtosList, pageable, bookingDashboradDtosList.size());
		
	}


}
