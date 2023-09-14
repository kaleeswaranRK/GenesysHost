package com.scb.ivr.model.rkalees.res.cardLost;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scb.ivr.service.generalvo.CommonOutput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardLostResponseData extends CommonOutput{

	private String cardLostInfo;



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getCardLostInfo() {
		return cardLostInfo;
	}

	public void setCardLostInfo(String cardLostInfo) {
		this.cardLostInfo = cardLostInfo;
	}

	
}
