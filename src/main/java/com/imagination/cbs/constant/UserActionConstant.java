/**
 * 
 */
package com.imagination.cbs.constant;

/**
 * @author Ramesh.Suryaneni
 *
 */
public enum UserActionConstant {
	
	APPROVE("APPROVE"),
	HRAPPROVE("HRAPPROVE"),
	DECLINE("DECLINE");
	
	UserActionConstant(String action) {
		this.setAction(action);
	}

	private String action;
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	

}
