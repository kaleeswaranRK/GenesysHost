package com.scb.ivr.model.ebbs.res.acctbalcasa;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASADatum {

	@JsonProperty("attributes")
	private AccountBalanceCASAAttributes attributes;

	public AccountBalanceCASAAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(AccountBalanceCASAAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
