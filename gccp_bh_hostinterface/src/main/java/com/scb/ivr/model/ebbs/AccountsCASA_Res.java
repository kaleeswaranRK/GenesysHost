package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.acctbalcasa.AccountBalanceCASAResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class AccountsCASA_Res extends CommonOutput {

	private AccountBalanceCASAResponseData response;

	public AccountBalanceCASAResponseData getResponse() {
		return response;
	}

	public void setResponse(AccountBalanceCASAResponseData response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
