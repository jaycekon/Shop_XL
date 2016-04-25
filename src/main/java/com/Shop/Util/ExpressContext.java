package com.Shop.Util;


public class ExpressContext {

	private String time;
	private String context;
	private String ftime;
	public ExpressContext(){
		
	}
	
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}

	
	public ExpressContext(String time, String context, String ftime) {
		super();
		this.time = time;
		this.context = context;
		this.ftime = ftime;
	}



	public String getFtime() {
		return ftime;
	}



	public void setFtime(String ftime) {
		this.ftime = ftime;
	}



	@Override
	public String toString() {
		return "ExpressContext [time=" + time + ", context=" + context
				+ ", ftime=" + ftime + "]";
	}




	

	
}
