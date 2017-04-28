package com.fdahpstudydesigner.bean;


public class DynamicBean {

	private String dateTime;
	
	private String time;
	
	public DynamicBean(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
