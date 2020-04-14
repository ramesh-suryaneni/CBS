package com.imagination.cbs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;
import com.imagination.cbs.dto.WorkTasksDto;
import com.imagination.cbs.util.CBSDateUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

	public Booking toBookingDomainFromBookingDto(BookingRequest bookingDto);

	public BookingDto toBookingDtoFromBooking(Booking booking);

	@Mapping(source = "contractedFromDate", dateFormat = "dd/MM/yyyy", target = "contractedFromDate")
	@Mapping(source = "contractedToDate", dateFormat = "dd/MM/yyyy", target = "contractedToDate")
	@Mapping(source = "contractorSignedDate", dateFormat = "dd/MM/yyyy", target = "contractorSignedDate")
	@Mapping(source = "bookingWorkTasks", target = "bookingWorkTasks", qualifiedByName = "mapList")
	public BookingDto convertToDto(BookingRevision bookingRevision);

	@Named("mapList")
	default WorkTasksDto convertDate(BookingWorkTask workTask) {
		String date = CBSDateUtils.convertDateToString(workTask.getTaskDeliveryDate());
		WorkTasksDto taskDto = new WorkTasksDto();
		taskDto.setTaskId(String.valueOf(workTask.getTaskId()));
		taskDto.setTaskName(String.valueOf(workTask.getTaskName()));
		taskDto.setTaskDeliveryDate(date);
		taskDto.setTaskDateRate(String.valueOf(workTask.getTaskDateRate()));
		taskDto.setTaskTotalDays(String.valueOf(workTask.getTaskTotalDays()));
		taskDto.setTaskTotalAmount(String.valueOf(workTask.getTaskTotalAmount()));
		taskDto.setBookingRevisionId(String.valueOf(workTask.getBookingRevision().getBookingRevisionId()));
		taskDto.setChangedBy(workTask.getChangedBy());
		taskDto.setChangedDate(String.valueOf(workTask.getChangedDate()));
		return taskDto;
	}
}