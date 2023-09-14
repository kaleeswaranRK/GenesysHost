package com.scb.ivr.model.uaas.res.validateotp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateOTPResponseData {

	private String statusCode;
	private String errorMessage;
	private String sessionTicket;

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

	public String getSessionTicket() {
		return sessionTicket;
	}

	public void setSessionTicket(String sessionTicket) {
		this.sessionTicket = sessionTicket;
	}

}
