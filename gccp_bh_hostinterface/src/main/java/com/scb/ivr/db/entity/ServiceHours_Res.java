package com.scb.ivr.db.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonOutput;

public class ServiceHours_Res extends CommonOutput {

	private String tableName;
	private boolean isPublicHoliday;
	private String serviceStartTime;
	private String serviceEndTime;
	private boolean isServiceHrs;
	private String genPrompt;
	private String splPrompt;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public boolean isServiceHrs() {
		return isServiceHrs;
	}

	public void setServiceHrs(boolean isServiceHrs) {
		this.isServiceHrs = isServiceHrs;
	}

	public String getGenPrompt() {
		return genPrompt;
	}

	public void setGenPrompt(String genPrompt) {
		this.genPrompt = genPrompt;
	}

	public String getSplPrompt() {
		return splPrompt;
	}

	public void setSplPrompt(String splPrompt) {
		this.splPrompt = splPrompt;
	}

	public boolean isPublicHoliday() {
		return isPublicHoliday;
	}

	public void setPublicHoliday(boolean isPublicHoliday) {
		this.isPublicHoliday = isPublicHoliday;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
