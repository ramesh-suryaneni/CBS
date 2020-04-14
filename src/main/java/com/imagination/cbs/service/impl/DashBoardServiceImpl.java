package com.imagination.cbs.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.imagination.cbs.util.CBSDateUtils;

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
	
	@Override
	public Page<BookingDashBoardDto> getDashBoardBookingsStatusDetails(String status, int pageNo, int pageSize) {

		String loggedInUser = loggedInUserService.getLoggedInUserDetails().getDisplayName();
		Long employeeId = loggedInUserService.getLoggedInUserDetails().getEmpId();
		
		List<BookingDashBoardDto> bookingDashboradDtos = new ArrayList<>();

		//Pageable pageable = createPageable(pageNo, pageSize, "br.changed_date", "DESC");
		List<Tuple> bookingRevisions = null;

		if (status.equalsIgnoreCase("Submitted")) {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForSubmitted(loggedInUser, PageRequest.of(pageNo, pageSize));
		} else if(status.equalsIgnoreCase("waiting")){
			return retrieveBookingRevisionForWaitingForApproval(employeeId, PageRequest.of(pageNo, pageSize), bookingDashboradDtos);
		} else {
			bookingRevisions = bookingRevisionRepository.retrieveBookingRevisionForDraftOrCancelled(loggedInUser, status, PageRequest.of(pageNo, pageSize));
		}
        List<BookingDashBoardDto> bookingDashBoardDtos = toPagedBookingDashBoardDtoFromTuple(bookingRevisions);

		return new PageImpl<>(bookingDashBoardDtos, PageRequest.of(pageNo, pageSize), bookingDashBoardDtos.size());
	}
	
	/*private Pageable createPageable(int pageNo, int pageSize, String sortingField, String sortingOrder) {
		Sort sort = null;
		if (sortingOrder.equals("ASC")) {
			sort = Sort.by(Direction.ASC, sortingField);
		}
		if (sortingOrder.equals("DESC")) {
			sort = Sort.by(Direction.DESC, sortingField);
		}

		return PageRequest.of(pageNo, pageSize, sort);
	}*/
	
	private Page<BookingDashBoardDto> retrieveBookingRevisionForWaitingForApproval(Long employeeId,  Pageable pageable, List<BookingDashBoardDto> bookingDashboradDtos){
		
		List<Tuple> retrieveBookingRevisionForWaitingByJobName = bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByJobNumber(employeeId, pageable);
		
        List<BookingDashBoardDto> bookingDashboradDtosList = toPagedBookingDashBoardDtoFromTuple(retrieveBookingRevisionForWaitingByJobName);
		
		List<Tuple> retrieveBookingRevisionForWaitingByEmployeeId = bookingRevisionRepository.retrieveBookingRevisionForWaitingForApprovalByEmployeeId(employeeId, pageable);
		
        bookingDashboradDtosList.addAll(toPagedBookingDashBoardDtoFromTuple(retrieveBookingRevisionForWaitingByEmployeeId));
		
		return new PageImpl<>(bookingDashboradDtosList, pageable, bookingDashboradDtosList.size());
		
	}
	
private List<BookingDashBoardDto> toPagedBookingDashBoardDtoFromTuple(List<Tuple> bookingRevisions) {
        
        return bookingRevisions.stream().filter((bookingRevision)-> Objects.nonNull((bookingRevision.get("bookingId",BigInteger.class))))
            .map(bookingRevision -> {
            
            	BookingDashBoardDto bookingDashBoardDto = new BookingDashBoardDto();

            bookingDashBoardDto.setStatus(bookingRevision.get("status", String.class));
            bookingDashBoardDto.setJobName(bookingRevision.get("jobName", String.class));
            bookingDashBoardDto.setRoleName(bookingRevision.get("roleName", String.class));
            bookingDashBoardDto.setContractorName(bookingRevision.get("contractorName", String.class));
            bookingDashBoardDto.setContractedFromDate(CBSDateUtils.convertTimeStampToString(bookingRevision.get("contractedFromDate", Timestamp.class)));
            bookingDashBoardDto.setContractedToDate(CBSDateUtils.convertTimeStampToString(bookingRevision.get("contractedToDate", Timestamp.class)));
            bookingDashBoardDto.setChangedBy(bookingRevision.get("changedBy", String.class));
            bookingDashBoardDto.setChangedDate(CBSDateUtils.convertTimeStampToString(bookingRevision.get("changedDate", Timestamp.class)));
            bookingDashBoardDto.setBookingId(bookingRevision.get("bookingId", BigInteger.class));
            bookingDashBoardDto.setBookingRevisionId(bookingRevision.get("bookingRevisionId", BigInteger.class));
            
            return bookingDashBoardDto;
            
            }).collect(Collectors.toList());
    }
    
    
	
}

