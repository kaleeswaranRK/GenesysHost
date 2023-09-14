package com.scb.ivr.model.ebbs.res.custidentifyacctnum;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationAcctNumResponseData {
	
	@JsonProperty("data")
	private List<CustIdentificationAcctNumDatum> data = null;
	
	@JsonProperty("errors")
	private CustIdentification_Errors[] errors;
	
	private String message=null;

	@JsonProperty("data")
	public List<CustIdentificationAcctNumDatum> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<CustIdentificationAcctNumDatum> data) {
		this.data = data;
	}

	public CustIdentification_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustIdentification_Errors[] errors) {
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
