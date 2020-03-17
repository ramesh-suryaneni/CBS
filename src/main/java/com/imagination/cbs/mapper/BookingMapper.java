package com.imagination.cbs.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.imagination.cbs.domain.Booking;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Discipline;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.BookingDto;
import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {

	public List<DisciplineDto> toListDisciplineDTO(List<Discipline> listOfDiscipline);

	public List<ContractorRoleDto> toListContractorRoleDto(List<RoleDm> listOfRoles);

	@Mapping(source = "changedDate", target = "changedDate", qualifiedByName = "stringToTimeStamp")
	public Booking toBookingDomainFromBookingDto(BookingDto bookingDto);

	// @Mapping(source = "contractedFromDate", target = "contractedFromDate",
	// qualifiedByName = "timeStampToString")
	// @Mapping(source = "contractedToDate", target = "contractedToDate",
	// qualifiedByName = "timeStampToString")
	// @Mapping(source = "revisionNumber", target = "revisionNumber",
	// qualifiedByName = "longToString")
	public BookingDto toBookingDtoFromBooking(Booking bookingDto);

	@Named("stringToTimeStamp")
	public static Timestamp stringToTimeStampConverter(String date) {
		return Timestamp.valueOf(date);
	}

	@Named("timeStampToString")
	public static String timeStampToStringConverter(Timestamp timeStamp) {
		return timeStamp.toString();
	}

	@Named("longToString")
	public static String longToStringConverter(String domainValue) {
		return domainValue.toString();
	}

	@Mapping(source = "contractedFromDate", target = "contractedFromDate", qualifiedByName = "timeStampToString")
	@Mapping(source = "contractedToDate", target = "contractedToDate", qualifiedByName = "timeStampToString")
	@Mapping(source = "revisionNumber", target = "revisionNumber", qualifiedByName = "longToString")
	public BookingDto toBookingDtoFromBookingRevision(BookingRevision bookingRevisionDto);
}