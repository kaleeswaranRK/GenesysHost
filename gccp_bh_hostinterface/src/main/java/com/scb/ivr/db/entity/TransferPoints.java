/**
 * 
 */
package com.scb.ivr.db.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author TA
 *
 */
public class TransferPoints {
	private String id;
	private String transferPoint;
	private String language;
	private String segment;
	private String service;
	private String queuePriority;
	private String businessHourFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransferPoint() {
		return transferPoint;
	}

	public void setTransferPoint(String transferPoint) {
		this.transferPoint = transferPoint;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getQueuePriority() {
		return queuePriority;
	}

	public void setQueuePriority(String queuePriority) {
		this.queuePriority = queuePriority;
	}

	public String getBusinessHourFlag() {
		return businessHourFlag;
	}

	public void setBusinessHourFlag(String businessHourFlag) {
		this.businessHourFlag = businessHourFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
