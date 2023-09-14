package com.scb.ivr.model.c400.res.custidentifycardnum;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationCardNumAttributes {

	@JsonProperty("org-num")
	public String orgNum;

	@JsonProperty("exception-response-code")
	public String exceptionResponseCode;

	@JsonProperty("card-limit")
	public String cardLimit;

	@JsonProperty("block-code")
	public String blockCode;

	@JsonProperty("block-status")
	public String blockStatus;

	@JsonProperty("customer-id")
	public String customerId;

	@JsonProperty("card-status")
	public String cardStatus;

	@JsonProperty("card-num")
	public String cardNum;

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getExceptionResponseCode() {
		return exceptionResponseCode;
	}

	public void setExceptionResponseCode(String exceptionResponseCode) {
		this.exceptionResponseCode = exceptionResponseCode;
	}

	public String getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(String cardLimit) {
		this.cardLimit = cardLimit;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
