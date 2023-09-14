package com.scb.ivr.model.ebbs.res.custidentifyrelcrp;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRelCRPResponseData {

	@JsonProperty("data")
	private List<CustIdentificationRelCRPDatum> data = null;

	@JsonProperty("errors")
	private CustIdentificationRelCRP_Errors[] errors;

	private String message = null;

	public List<CustIdentificationRelCRPDatum> getData() {
		return data;
	}

	public void setData(List<CustIdentificationRelCRPDatum> data) {
		this.data = data;
	}

	public CustIdentificationRelCRP_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustIdentificationRelCRP_Errors[] errors) {
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
