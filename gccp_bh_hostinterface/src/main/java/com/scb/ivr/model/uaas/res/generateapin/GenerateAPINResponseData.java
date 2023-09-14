package com.scb.ivr.model.uaas.res.generateapin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateAPINResponseData {

	private String statusCode;
	private String errorMessage;
	private PinResetInfo pinResetInfo;

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

	public PinResetInfo getPinResetInfo() {
		return pinResetInfo;
	}

	public void setPinResetInfo(PinResetInfo pinResetInfo) {
		this.pinResetInfo = pinResetInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
