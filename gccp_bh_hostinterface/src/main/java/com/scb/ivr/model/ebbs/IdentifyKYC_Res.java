package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.identifykyc.IdentifyKYCResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class IdentifyKYC_Res extends CommonOutput {

	private IdentifyKYCResponseData response;

	public IdentifyKYCResponseData getResponse() {
		return response;
	}

	public void setResponse(IdentifyKYCResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
