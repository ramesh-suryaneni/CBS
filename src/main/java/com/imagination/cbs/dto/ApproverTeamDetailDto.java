package com.imagination.cbs.dto;

/**
 * @author pappu.rout
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproverTeamDetailDto {

	private String remark3;
	private String name;

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ApproverTeamDetailDto [remark3=" + remark3 + ", name=" + name + "]";
	}
}
