package com.imagination.cbs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

	public Booking toBookingDomainFromBookingDto(BookingRequest bookingDto);

	public BookingDto toBookingDtoFromBooking(Booking booking);

	@Mapping(source = "contractedFromDate", dateFormat = "dd/MM/yyyy", target = "contractedFromDate")
	@Mapping(source = "contractedToDate", dateFormat = "dd/MM/yyyy", target = "contractedToDate")
	@Mapping(source = "contractorSignedDate", dateFormat = "dd/MM/yyyy", target = "contractorSignedDate")
	public BookingDto convertToDto(BookingRevision bookingRevision);
}