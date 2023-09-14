package com.scb.ivr.model.ebbs.res.acctbalcasa;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASAResponse {

	@JsonProperty("customerdetails")
	private AccountBalanceCASACustomerdetails customerdetails;

	public AccountBalanceCASACustomerdetails getCustomerdetails() {
		return customerdetails;
	}

	public void setCustomerdetails(AccountBalanceCASACustomerdetails customerdetails) {
		this.customerdetails = customerdetails;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
