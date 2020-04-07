/**
 * 
 */
package com.imagination.cbs.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Data
public class ApproveRequest {
	
	@NotEmpty
	@Positive
	private String bookingId;
	
	@Pattern(regexp = "APPROVE|HRAPPROVE", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String action;

	@NotEmpty
	private String status;

}
