package com.scb.ivr.service.generalvo;

public class CommonOutput {
	
	private String errorcode;
	private String errormessage;
	private String messageId;
	private String startTime;
	private String endTime;
	
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "CommonOutput [errorcode=" + errorcode + ", errormessage=" + errormessage + ", messageId=" + messageId
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	

}
