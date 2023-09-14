package com.scb.ivr.model.rkalees;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.scb.ivr.model.rkalees.res.cardLost.CardLostResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class cardLost_Res extends CommonOutput{
	private CardLostResponseData response;

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


	public CardLostResponseData getResponse() {
		return response;
	}


	public void setResponse(CardLostResponseData response) {
		this.response = response;
	}
}
