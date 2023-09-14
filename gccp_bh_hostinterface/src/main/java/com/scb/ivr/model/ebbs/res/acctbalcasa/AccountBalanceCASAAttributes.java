package com.scb.ivr.model.ebbs.res.acctbalcasa;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASAAttributes {

	@JsonProperty("response")
	private AccountBalanceCASAResponse response;

	public AccountBalanceCASAResponse getResponse() {
		return response;
	}

	public void setResponse(AccountBalanceCASAResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
