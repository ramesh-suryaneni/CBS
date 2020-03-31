package com.imagination.cbs.mapper;

import java.sql.Timestamp;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.BookingRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

	public Booking toBookingDomainFromBookingDto(BookingRequest bookingDto);

	public BookingDto toBookingDtoFromBooking(Booking booking);

	@Named("stringToTimeStamp")
	public static Timestamp stringToTimeStampConverter(String date) {
		return Timestamp.valueOf(date);
	}

	@Mapping(source = "contractedFromDate", dateFormat = "dd/MM/yyyy", target = "contractedFromDate")
	@Mapping(source = "contractedToDate", dateFormat = "dd/MM/yyyy", target = "contractedToDate")
	@Mapping(source = "contractorSignedDate", dateFormat = "dd/MM/yyyy", target = "contractorSignedDate")
	public BookingDto toBookingDtoFromBookingRevision(BookingRevision bookingRevisionDto);

	// @Mapping(source = "contractedFromDate", dateFormat = "dd/MM/yyyy", target
	// = "contractedFromDate", qualifiedByName = "stringToTimeStamp")
	// @Mapping(source = "contractedToDate", dateFormat = "dd/MM/yyyy", target =
	// "contractedToDate", qualifiedByName = "stringToTimeStamp")
	// @Mapping(source = "contractorSignedDate", dateFormat = "dd/MM/yyyy",
	// target = "contractorSignedDate", qualifiedByName = "stringToTimeStamp")
	// public BookingRevision toBookingRevisionFromBookingDto(BookingRequest
	// bookingDto);

	public BookingDto convertToDto(BookingRevision bookingRevision);

}