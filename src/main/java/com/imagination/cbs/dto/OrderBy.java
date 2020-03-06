/**
 * 
 */
package com.imagination.cbs.dto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public enum OrderBy {
	
	ID("id"), 
	CREATED_ON("createdOn"), 
	CREATED_TS("createdTs"), 
	PRIORITY("priority"),
	ROLE_NAME("name"),
	SONG_GENRE("genre"),
	USERNAME("username"),
	REPORTED_ON("reportedOn"),
	UPDATED_ON("updatedOn"),
	WALL_NAME("name");
	
	private String orderByCode;

	private OrderBy(String orderBy) {
		this.orderByCode = orderBy;
	}

	public String getOrderByCode() {
		return this.orderByCode;
	}
}
