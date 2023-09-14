package com.scb.ivr.model.ebbs.res.custidentifyrelcrp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRelCRPDatum {

	@JsonProperty("attributes")
	private CustIdentificationRelCRPAttributes attributes;

	public CustIdentificationRelCRPAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustIdentificationRelCRPAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
