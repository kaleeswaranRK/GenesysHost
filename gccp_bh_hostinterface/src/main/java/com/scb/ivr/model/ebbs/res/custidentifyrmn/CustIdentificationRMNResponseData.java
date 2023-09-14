package com.scb.ivr.model.ebbs.res.custidentifyrmn;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRMNResponseData {

	@JsonProperty("data")
	private CustIdentificationRMNDatum data;

	@JsonProperty("errors")
	private CustIdentificationRMN_Errors[] errors;

	private String message = null;

	public CustIdentificationRMNDatum getData() {
		return data;
	}

	public void setData(CustIdentificationRMNDatum data) {
		this.data = data;
	}

	public CustIdentificationRMN_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustIdentificationRMN_Errors[] errors) {
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
