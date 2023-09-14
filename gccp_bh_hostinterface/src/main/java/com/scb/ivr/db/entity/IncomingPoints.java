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
public class IncomingPoints {
	private String id;
	private String vdn;
	private String callType;
	private String applicationType;
	private String hotlineCode;
	private String hotlineName;
	private String ivrName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVdn() {
		return vdn;
	}

	public void setVdn(String vdn) {
		this.vdn = vdn;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getHotlineCode() {
		return hotlineCode;
	}

	public void setHotlineCode(String hotlineCode) {
		this.hotlineCode = hotlineCode;
	}

	public String getHotlineName() {
		return hotlineName;
	}

	public void setHotlineName(String hotlineName) {
		this.hotlineName = hotlineName;
	}

	public String getIvrName() {
		return ivrName;
	}

	public void setIvrName(String ivrName) {
		this.ivrName = ivrName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
