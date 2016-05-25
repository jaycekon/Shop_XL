package com.Shop.Util;

import java.util.List;

/**
 * 物流实体
 */
public class ExpressBean {
	private String message;
	private String nu;
	private int ischeck;
	private String com;
	private int status;
	private String condition;
	private String state;
	private List<ExpressContext> data;

	public ExpressBean(){
	 
	}
	
	public ExpressBean(String message, String nu, int ischeck, String com,
			int status, String condition, String state,
			List<ExpressContext> data) {
		super();
		this.message = message;
		this.nu = nu;
		this.ischeck = ischeck;
		this.com = com;
		this.status = status;
		this.condition = condition;
		this.state = state;
		this.data = data;
	}

	public List<ExpressContext> getData() {
		return data;
	}

	public void setData(List<ExpressContext> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	public int getIscheck() {
		return ischeck;
	}
	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ExpressBean [message=" + message + ", nu=" + nu + ", ischeck="
				+ ischeck + ", com=" + com + ", status=" + status
				+ ", condition=" + condition + ", state=" + state + ", data="
				+ data + "]";
	}


	
}
