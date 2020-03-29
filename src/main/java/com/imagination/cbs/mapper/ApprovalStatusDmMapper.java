package com.imagination.cbs.mapper;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.ApprovalStatusDm;
import com.imagination.cbs.dto.ApprovalStatusDmDto;

/**
 * @author pravin.budage
 *
 */
@Mapper(componentModel = "spring")
public interface ApprovalStatusDmMapper {

	public ApprovalStatusDmDto toApprovalStatusDmDtoFromApprovalStatusDmDomain(ApprovalStatusDm approvalStatusDmDto);
}
