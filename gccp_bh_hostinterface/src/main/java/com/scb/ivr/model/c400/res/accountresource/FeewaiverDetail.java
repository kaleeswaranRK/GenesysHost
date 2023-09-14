package com.scb.ivr.model.c400.res.accountresource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeewaiverDetail {

	@JsonProperty("statementdate")
	private String statementdate;
	
	@JsonProperty("finance-charge-waiver-count")
	private Integer financeChargeWaiverCount;
	
	@JsonProperty("late-charge-waiver-count")
	private Integer lateChargeWaiverCount;
	
	@JsonProperty("overlimit-fee-waiver-count")
	private Integer overlimitFeeWaiverCount;
	
	@JsonProperty("fee-month")
	private Integer feeMonth;
	
	@JsonProperty("statement-date")
	private String statementDate;
	
	public String getStatementdate() {
		return statementdate;
	}
	public void setStatementdate(String statementdate) {
		this.statementdate = statementdate;
	}

	public Integer getFinanceChargeWaiverCount() {
		return financeChargeWaiverCount;
	}
	public void setFinanceChargeWaiverCount(Integer financeChargeWaiverCount) {
		this.financeChargeWaiverCount = financeChargeWaiverCount;
	}

	public Integer getLateChargeWaiverCount() {
		return lateChargeWaiverCount;
	}
	public void setLateChargeWaiverCount(Integer lateChargeWaiverCount) {
		this.lateChargeWaiverCount = lateChargeWaiverCount;
	}

	public Integer getOverlimitFeeWaiverCount() {
		return overlimitFeeWaiverCount;
	}
	public void setOverlimitFeeWaiverCount(Integer overlimitFeeWaiverCount) {
		this.overlimitFeeWaiverCount = overlimitFeeWaiverCount;
	}

	public Integer getFeeMonth() {
		return feeMonth;
	}
	public void setFeeMonth(Integer feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getStatementDate() {
		return statementDate;
	}
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}

	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
