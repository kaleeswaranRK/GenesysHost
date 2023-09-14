package com.scb.ivr.model.c400.res.accbal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceDatum {

	@JsonProperty("attributes")
	private AccountBalanceAttributes attributes;

	@JsonProperty("attributes")
	public AccountBalanceAttributes getAttributes() {
		return attributes;
	}

	@JsonProperty("attributes")
	public void setAttributes(AccountBalanceAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
