package com.scb.ivr.model.ebbs.res.identifykyc;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifyKYCResponseData {

	@JsonProperty("data")
	private List<IdentifyKYCDatum> data = null;

	@JsonProperty("errors")
	private IdentifyKYC_Errors[] errors = null;

	private String message = null;

	public List<IdentifyKYCDatum> getData() {
		return data;
	}

	public void setData(List<IdentifyKYCDatum> data) {
		this.data = data;
	}

	public IdentifyKYC_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(IdentifyKYC_Errors[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
