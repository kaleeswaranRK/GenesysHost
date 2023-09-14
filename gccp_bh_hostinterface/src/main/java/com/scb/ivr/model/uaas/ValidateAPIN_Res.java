package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.uaas.res.validateapin.ValidateAPINResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class ValidateAPIN_Res extends CommonOutput {

	private ValidateAPINResponseData response;

	public ValidateAPINResponseData getResponse() {
		return response;
	}

	public void setResponse(ValidateAPINResponseData response) {
		this.response = response;
	}
	
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
