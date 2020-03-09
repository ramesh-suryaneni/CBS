package com.imagination.cbs.dto;

import java.util.List;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.Team;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookingDto {
	@Setter
	@Getter
	private long bookingId;

	@Setter
	@Getter
	private String bookingDescription;

	@Setter
	@Getter
	private String changedBy;

	@Setter
	@Getter
	private String changedDate;

	@Setter
	@Getter
	private Team team;

	@Setter
	@Getter
	private ApprovalStatusDm approvalStatusDm;

	@Setter
	@Getter
	private List<BookingRevision> bookingRevisions;
}
