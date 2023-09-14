package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.uaas.res.generateccpin.GenerateCCPINResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class GenerateCCPIN_Res extends CommonOutput {

	private GenerateCCPINResponseData response;

	public GenerateCCPINResponseData getResponse() {
		return response;
	}

	public void setResponse(GenerateCCPINResponseData response) {
		this.response = response;
	}

	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
