package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.uaas.res.validateccpin.ValidateCCPINResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class ValidateCCPIN_Res extends CommonOutput {

	private ValidateCCPINResponseData response;

	public ValidateCCPINResponseData getResponse() {
		return response;
	}

	public void setResponse(ValidateCCPINResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
