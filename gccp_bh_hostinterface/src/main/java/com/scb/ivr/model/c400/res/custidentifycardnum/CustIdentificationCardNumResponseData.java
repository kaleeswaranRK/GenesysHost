package com.scb.ivr.model.c400.res.custidentifycardnum;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationCardNumResponseData {

	@JsonProperty("data")
	private List<CustIdentificationCardNumData> data;

	@JsonProperty("errors")
	private CustIdentificationCardNum_Errors[] errors;
	
	private String message;

	public List<CustIdentificationCardNumData> getData() {
		return data;
	}

	public void setData(List<CustIdentificationCardNumData> data) {
		this.data = data;
	}

	public CustIdentificationCardNum_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustIdentificationCardNum_Errors[] errors) {
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
