package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.uaas.res.generateapin.GenerateAPINResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class ChangeAPIN_Res extends CommonOutput {

	private GenerateAPINResponseData response;

	public GenerateAPINResponseData getResponse() {
		return response;
	}

	public void setResponse(GenerateAPINResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
