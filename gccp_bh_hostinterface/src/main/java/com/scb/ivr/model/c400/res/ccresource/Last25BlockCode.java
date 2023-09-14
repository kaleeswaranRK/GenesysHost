package com.scb.ivr.model.c400.res.ccresource;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Last25BlockCode {
	@JsonProperty("last-25-block-code-history") 
    public String last25BlockCodeHistory;
    @JsonProperty("last-25-block-code-date-history") 
    public Date last25BlockCodeDateHistory;
	public String getLast25BlockCodeHistory() {
		return last25BlockCodeHistory;
	}
	public void setLast25BlockCodeHistory(String last25BlockCodeHistory) {
		this.last25BlockCodeHistory = last25BlockCodeHistory;
	}
	public Date getLast25BlockCodeDateHistory() {
		return last25BlockCodeDateHistory;
	}
	public void setLast25BlockCodeDateHistory(Date last25BlockCodeDateHistory) {
		this.last25BlockCodeDateHistory = last25BlockCodeDateHistory;
	}
	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
    
}
