package com.scb.ivr.model.uaas.res.validateapin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateAPINResponseData {

	private String statusCode;
	private String errorMessage;
	private PinAuthInfo pinAuthInfo;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public PinAuthInfo getPinAuthInfo() {
		return pinAuthInfo;
	}

	public void setPinAuthInfo(PinAuthInfo pinAuthInfo) {
		this.pinAuthInfo = pinAuthInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
