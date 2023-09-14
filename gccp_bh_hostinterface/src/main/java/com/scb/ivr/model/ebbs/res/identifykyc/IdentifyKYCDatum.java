package com.scb.ivr.model.ebbs.res.identifykyc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifyKYCDatum {

	@JsonProperty("attributes")
	private IdentifyKYCAttributes attributes;

	public IdentifyKYCAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(IdentifyKYCAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
