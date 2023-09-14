package com.scb.ivr.model.c400;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.c400.res.accbal.AccountBalanceResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CreditCardBalance_Res extends CommonOutput {

	public AccountBalanceResponseData response;

	public AccountBalanceResponseData getResponse() {
		return response;
	}

	public void setResponse(AccountBalanceResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
