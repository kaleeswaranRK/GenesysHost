package com.scb.ivr.model.c400.res.accbal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalanceDelinquencyHistory {

	@JsonProperty("delinquency-history")
	private String delinquencyHistory;
	
	@JsonProperty("balance-history")
	private String balanceHistory;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("delinquency-history")
	public String getDelinquencyHistory() {
		return delinquencyHistory;
	}

	@JsonProperty("delinquency-history")
	public void setDelinquencyHistory(String delinquencyHistory) {
		this.delinquencyHistory = delinquencyHistory;
	}

	@JsonProperty("balance-history")
	public String getBalanceHistory() {
		return balanceHistory;
	}

	@JsonProperty("balance-history")
	public void setBalanceHistory(String balanceHistory) {
		this.balanceHistory = balanceHistory;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
