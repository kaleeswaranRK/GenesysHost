package com.scb.ivr.model.ebbs.res.acctbalcasa;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASAResponseData {

	@JsonProperty("data")
	private List<AccountBalanceCASADatum> data = null;

	@JsonProperty("errors")
	private AccountBalanceCASA_Errors[] errors;

	private String message = null;

	public List<AccountBalanceCASADatum> getData() {
		return data;
	}

	public void setData(List<AccountBalanceCASADatum> data) {
		this.data = data;
	}

	public AccountBalanceCASA_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(AccountBalanceCASA_Errors[] errors) {
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
