package com.scb.ivr.model.c400.res.accbal;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceResponseData implements Serializable {

	private static final long serialVersionUID = 1443900278618999949L;

	@JsonProperty("data")
	private List<AccountBalanceDatum> data = null;

	@JsonProperty("errors")
	private AccountBalance_Errors[] errors;
	
	private String message=null;

	public List<AccountBalanceDatum> getData() {
		return data;
	}

	public void setData(List<AccountBalanceDatum> data) {
		this.data = data;
	}

	public AccountBalance_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(AccountBalance_Errors[] errors) {
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
