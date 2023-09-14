package com.scb.ivr.model.ebbs.res.custidentifyrmn;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRMNAttributes {

	@JsonProperty("response")
	private CustIdentificationRMNResponse response;

	public CustIdentificationRMNResponse getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationRMNResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
