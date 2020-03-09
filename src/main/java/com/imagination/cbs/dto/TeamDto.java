package com.imagination.cbs.dto;

import java.util.List;

import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.Booking;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TeamDto {

	@Setter
	@Getter
	private long teamId;

	@Setter
	@Getter
	private String changedBy;

	@Setter
	@Getter
	private String teamName;

	@Setter
	@Getter
	private String changedDate;

	@Setter
	@Getter
	private List<Approver> approvers;

	@Setter
	@Getter
	private List<Booking> bookings;
}
